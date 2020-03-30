package fr.prodrivers.bukkit.commons.sections;

import fr.prodrivers.bukkit.commons.exceptions.*;
import fr.prodrivers.bukkit.commons.parties.Party;
import fr.prodrivers.bukkit.commons.parties.PartyManager;
import fr.prodrivers.bukkit.commons.plugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;
import java.util.logging.Level;

/**
 * Prodrivers Commons Section Manager
 */
public class SectionManager {
	private static HashMap<String, IProdriversSection> sections = new HashMap<>();
	private static HashMap<UUID, IProdriversSection> playersCurrentSection = new HashMap<>();
	private static HashSet<UUID> inEnter = new HashSet<>();

	private static MainHub mainHub;

	public static void init( JavaPlugin plugin ) {
		new SectionListener( plugin );

		mainHub = new MainHub();
		register( mainHub );
	}

	public static void reload() {
		mainHub.reload();
	}

	public static void enter( Player player ) throws InvalidSectionException, IllegalSectionLeavingException, IllegalSectionEnteringException, NotPartyOwnerException {
		// Get the current player section
		IProdriversSection currentSection = playersCurrentSection.get( player.getUniqueId() );

		// If it is null
		if( currentSection == null ) {
			// Stop everything and inform
			throw new NoCurrentSectionException( "Player should indicate a section to join" );
		}

		// Get the current section preferred back section
		String backSectionName = currentSection.getPreferredNextSection();

		if( backSectionName == null ) {
			// Stop everything and inform
			throw new NoNextSectionException( "Current section doesn't have a preferred section" );
		}

		IProdriversSection backSection = sections.get( currentSection.getPreferredNextSection() );

		// If it is null
		if( backSection == null ) {
			// Stop everything and inform
			throw new NoNextSectionException( "Current section doesn't have a preferred section (invalid back section)" );
		}

		// Send the player to the preferred section
		// We set force to false as it makes no sense to force the player to go to the target section as the target section is already the preferred back section
		enterEffective( player, currentSection, backSection, null, false );
	}

	public static void enter( Player player, String sectionName ) throws InvalidSectionException, IllegalSectionLeavingException, IllegalSectionEnteringException, NotPartyOwnerException {
		enter( player, sectionName, null, false );
	}

	public static void enter( Player player, String sectionName, String subSection ) throws InvalidSectionException, IllegalSectionLeavingException, IllegalSectionEnteringException, NotPartyOwnerException {
		enter( player, sectionName, subSection, false );
	}

	public static void enter( Player player, String sectionName, String subSection, boolean force ) throws InvalidSectionException, IllegalSectionLeavingException, IllegalSectionEnteringException, NotPartyOwnerException {
		// Get the target section
		IProdriversSection targetSection = sections.get( sectionName );

		// If the section doesn't exist
		if( targetSection == null ) {
			// Stop everything and inform
			throw new InvalidSectionException( "Invalid section name" );
		}

		IProdriversSection leavedSection = playersCurrentSection.get( player.getUniqueId() );

		enterEffective( player, leavedSection, targetSection, subSection, force );
	}

	private static void enterEffective( Player player, IProdriversSection leavedSection, IProdriversSection targetSection, String subSection, boolean force ) throws IllegalSectionLeavingException, IllegalSectionEnteringException {
		// If the player is already in an enter process
		if( inEnter.contains( player.getUniqueId() ) )
			// Stop
			return;

		// Register that the player is in an enter process
		inEnter.add( player.getUniqueId() );

		// Check party
		if( targetSection.shouldMoveParty() ) {
			Party party = PartyManager.getParty( player.getUniqueId() );

			// If player is in party
			if( party != null ) {
				// Check party owner
				if( party.getOwnerUniqueId() != player.getUniqueId() ) {
					// Player is not party owner
					if( !targetSection.isHub() ) {
						// Remove him from entering players as we either retry or stop everything
						inEnter.remove( player.getUniqueId() );

						// Target section is not a hub, forbid moving
						throw new NotPartyOwnerException( "Player tried to join a section while not being party owner." );
					}
					// Target section is a hub, proceed normally
				} else {
					// Player is party owner
					// Move all party players, except the owner, to the target section
					for( UUID partyPlayerUUID : party.getPlayers() ) {
						// Get party player if not current player
						if( player.getUniqueId() != partyPlayerUUID ) {
							Player partyPlayer = Bukkit.getPlayer( partyPlayerUUID );

							if( partyPlayer != null ) {
								// If the player is already in an enter process
								if( inEnter.contains( partyPlayer.getUniqueId() ) )
									// Stop
									return;

								// Register that the player is in an enter process
								inEnter.add( partyPlayer.getUniqueId() );

								// Move player to section
								SectionManager.enterEffective( partyPlayer, leavedSection, targetSection, subSection, force, true );
							} else {
								Main.logger.severe( "[ProdriversCommons] Player " + partyPlayerUUID + " is in party but not on the server." );
							}
						}
					}
				}
			}
		}

		SectionManager.enterEffective( player, leavedSection, targetSection, subSection, force, false );
	}

	private static void enterEffective( Player player, IProdriversSection leavedSection, IProdriversSection targetSection, String subSection, boolean force, boolean enterWithParty ) throws IllegalSectionLeavingException, IllegalSectionEnteringException {
		leave( player, leavedSection, targetSection, force );

		// Inform the target section that the player wants to join
		// If the section wants to stop
		String leavedSectionName = ( leavedSection != null ? leavedSection.getName() : null );
		try {
			if( !targetSection.join( player, subSection, leavedSectionName ) ) {
				// Indicate the player has finished his enter process
				inEnter.remove( player.getUniqueId() );

				// If this is the main hub
				if( targetSection.getName().equals( MainHub.name ) ) {
					// Kick the player as there is probably no safe place to put him
					player.kickPlayer( Main.getMessages().kicked_because_no_safe_place );
				} else {
					// Stop everything and inform, put the player to the main lobby
					SectionManager.enter( player, MainHub.name );
					throw new IllegalSectionEnteringException( "Player should not go to the selected section" );
				}
			}

			// Register the target section as current section for the player
			playersCurrentSection.put( player.getUniqueId(), targetSection );

			// Indicate the player has finished his enter process
			inEnter.remove( player.getUniqueId() );

			// Calls the post-join callback
			try {
				targetSection.postJoin( player, subSection, leavedSectionName );
			} catch( Exception e ) {
				inEnter.remove( player.getUniqueId() );
				Main.logger.log( Level.SEVERE, "[ProdriversCommons] Section " + targetSection.getName() + " (" + targetSection.getClass() + ") raised an exception during its post-enter call: " + e.getLocalizedMessage(), e );
			}
		} catch( Exception e ) {
			inEnter.remove( player.getUniqueId() );
			Main.logger.log( Level.SEVERE, "[ProdriversCommons] Section " + targetSection.getName() + " (" + targetSection.getClass() + ") raised an exception during its enter call: " + e.getLocalizedMessage(), e );
		}
	}

	public static void leave( Player player ) throws NoCurrentSectionException, IllegalSectionLeavingException {
		// Get the current player section
		IProdriversSection currentSection = playersCurrentSection.get( player.getUniqueId() );

		// If it is null
		if( currentSection == null ) {
			// Stop everything and inform
			throw new NoCurrentSectionException( "Player should indicate a section to join" );
		}

		// Make the player leave
		// We set force to false as it makes no sense to force the player to go to the target section as there is no target section
		leave( player, currentSection, null, false );
	}

	public static void leave( Player player, IProdriversSection leavedSection, IProdriversSection targetSection, boolean force ) throws IllegalSectionLeavingException{
		// If the player is already in a section
		if( leavedSection != null ) {
			// If the leaved section indicate that we should force the next section
			if( leavedSection.forceNextSection() ) {
				// Get the preferred next section
				String backSectionName = leavedSection.getPreferredNextSection();

				// If it is not null
				if( backSectionName != null ) {
					IProdriversSection backSection = sections.get( leavedSection.getPreferredNextSection() );

					// If it is not null and the target section if different from the forced section
					if( backSection != null && targetSection != null && !backSection.getName().equals( targetSection.getName() ) ) {
						// Remove him from entering players as we either retry or stop everything
						inEnter.remove( player.getUniqueId() );

						// If we should force the player to exit
						if( force ) {
							// Make him exit to the forced back section
							enter( player, backSection.getName() );

							// Retry make him enter the desired section
							enter( player, targetSection.getName() );
						} else {
							// Stop everything as the player should not go there and inform
							throw new IllegalSectionLeavingException( "Leaved section forbid the player to enter desired section" );
						}
					}
				}
			}

			// Inform the corresponding section
			// If the section wants to stop
			try {
				if( !leavedSection.leave( player, ( targetSection != null ? targetSection.getName() : null ) ) ) {
					// We stop everything
					inEnter.remove( player.getUniqueId() );
					return;
				}

				// Remove the corresponding section as this player's current section
				playersCurrentSection.remove( player.getUniqueId() );

				// Calls the post-leave callback
				try {
					leavedSection.postLeave( player, ( targetSection != null ? targetSection.getName() : null ) );
				} catch( Exception e ) {
					Main.logger.log( Level.SEVERE, "[ProdriversCommons] Section " + leavedSection.getName() + " (" + leavedSection.getClass() + ") raised an exception during its post-leave call: " + e.getLocalizedMessage(), e );
				}
			} catch( Exception e ) {
				Main.logger.log( Level.SEVERE, "[ProdriversCommons] Section " + leavedSection.getName() + " (" + leavedSection.getClass() + ") raised an exception during its leave call: " + e.getLocalizedMessage(), e );
			}
		}
	}

	public static void register( IProdriversSection section ) throws NullPointerException {
		if( section == null )
			throw new NullPointerException();

		if( !sections.containsKey( section.getName() ) ) {
			sections.put( section.getName(), section );
			Main.logger.info( "[ProdriversCommons] SectionManager registered section " + section.getName() + " (" + section.getClass() + ")." );
		} else {
			Main.logger.warning( "[ProdriversCommons] Section " + section.getName() + " (" + section.getClass() + ") tried to be registered for a second time." );
		}
	}

	public static IProdriversSection getCurrentSection( Player player ) {
		return playersCurrentSection.get( player.getUniqueId() );
	}

	public static IProdriversSection getCurrentSection( OfflinePlayer player ) {
		return playersCurrentSection.get( player.getUniqueId() );
	}

	public static IProdriversSection getSection( String name ) {
		return sections.get( name );
	}
}
