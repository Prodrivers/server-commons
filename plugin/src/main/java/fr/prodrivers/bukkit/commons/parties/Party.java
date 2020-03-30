package fr.prodrivers.bukkit.commons.parties;

import fr.prodrivers.bukkit.commons.Chat;
import fr.prodrivers.bukkit.commons.plugin.Main;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Party {
	private UUID owner;
	private ArrayList<UUID> players = new ArrayList<>();

	Party( final UUID ownerUniqueId ) {
		this.owner = ownerUniqueId;
		this.players.add( this.owner );
	}

	public UUID getOwnerUniqueId() {
		return this.owner;
	}

	public ArrayList<UUID> getPlayers() {
		return this.players;
	}

	public boolean addPlayer( final UUID playerUniqueId ) {
		if( playerUniqueId != null ) {
			final Player player = Bukkit.getPlayer( playerUniqueId );
			if( player != null ) {
				if( !PartyManager.isInParty( playerUniqueId ) ) {
					this.players.add( playerUniqueId );
					PartyManager.addParty( playerUniqueId, this );
					Main.getChat().success( player, Main.getMessages().party_you_joined_party.replaceAll( "%PLAYER%", Bukkit.getPlayer( this.getOwnerUniqueId() ).getName() ), Main.getMessages().party_prefix );
					this.tellAll( Main.getMessages().party_player_joined_party.replaceAll( "%PLAYER%", player.getName() ), Arrays.asList( playerUniqueId ) );
					return true;
				}
			}
		}
		return false;
	}

	public void electOwner( UUID newOwner ) {
		System.out.println( "[ProdriversCommons] Electing owner " + newOwner );
		// Set new owner
		owner = newOwner;
		// Tell the party who is the new leader
		final Player p = Bukkit.getPlayer( owner );
		if( p != null ) {
			if( this.players.contains( newOwner ) ) {
				Main.getChat().send( p, Main.getMessages().party_you_were_elected_as_new_leader, Main.getMessages().party_prefix );
				this.tellAll( Main.getMessages().party_player_elected_as_new_leader.replaceAll( "%PLAYER%", p.getName() ), Arrays.asList( newOwner ) );
			}
		}
	}

	public boolean removePlayer( final UUID playerUniqueId ) {
		if( playerUniqueId != null ) {
			if( isPartyOwner( playerUniqueId ) ) {
				System.out.println( "[ProdriversCommons] Party owner leaving" );
				// If there is still more than one player in the party
				if( size() > 1 ) {
					System.out.println( "[ProdriversCommons] Still players in party" );
					// Remove the leaving player
					this.players.remove( playerUniqueId );
					PartyManager.removeParty( playerUniqueId );
					// Elect a player to be the new leader
					electOwner( this.players.get( 0 ) );
				} else {
					System.out.println( "[ProdriversCommons] Disbanding party" );
					disband();
				}
				return true;
			}

			if( this.players.contains( playerUniqueId ) ) {
				this.players.remove( playerUniqueId );
				PartyManager.removeParty( playerUniqueId );
				final Player p___ = Bukkit.getPlayer( playerUniqueId );
				if( p___ != null ) {
					Main.getChat().success( p___, Main.getMessages().party_you_left_party.replaceAll( "%PLAYER%", Bukkit.getPlayer( this.getOwnerUniqueId() ).getName() ), Main.getMessages().party_prefix );
					this.tellAll( Main.getMessages().party_player_left_party.replaceAll( "%PLAYER%", p___.getName() ), Arrays.asList( playerUniqueId ) );
				}
				if( size() <= 1 ) {
					disband();
				}
				return true;
			}
		}
		return false;
	}

	public boolean containsPlayer( final UUID playerUniqueId ) {
		return playerUniqueId != null && this.players.contains( playerUniqueId );
	}

	public boolean isPartyOwner( final UUID playerUniqueId ) {
		return playerUniqueId != null && this.owner.equals( playerUniqueId );
	}

	public int size() {
		return this.players.size();
	}

	public void disband() {
		this.tellAll( Main.getMessages().party_disbanded );
		for( UUID p : this.players )
			PartyManager.removeParty( p );
		//this.players.clear();
	}

	public void chat( Player player, String message ) {
		if( message.trim().length() > 0 ) {
			String format = Main.getMessages().party_chat_format;
			message = format.replaceAll( "%PLAYER%", player.getDisplayName() ).replaceAll( "%MESSAGE%", message );
			for( final UUID p_ : this.getPlayers() ) {
				final Player p__ = Bukkit.getPlayer( p_ );
				if( p__ != null ) {
					p__.sendMessage( message );
				}
			}
		}
	}

	public void broadcast( Chat chat, final String msg ) {
		for( final UUID p_ : this.getPlayers() ) {
			final Player p__ = Bukkit.getPlayer( p_ );
			if( p__ != null ) {
				chat.send( p__, msg );
			}
		}
	}

	public void broadcast( Chat chat, final String msg, final List<UUID> excluded ) {
		for( final UUID p_ : this.getPlayers() ) {
			if( !excluded.contains( p_ ) ) {
				final Player p__ = Bukkit.getPlayer( p_ );
				if( p__ != null ) {
					chat.send( p__, msg );
				}
			}
		}
	}

	private void tellAll( final String msg ) {
		for( final UUID p_ : this.getPlayers() ) {
			final Player p__ = Bukkit.getPlayer( p_ );
			if( p__ != null ) {
				Main.getChat().send( p__, msg, Main.getMessages().party_prefix );
			}
		}
	}

	private void tellAll( final String msg, final List<UUID> excluded ) {
		for( final UUID p_ : this.getPlayers() ) {
			if( !excluded.contains( p_ ) ) {
				final Player p__ = Bukkit.getPlayer( p_ );
				if( p__ != null ) {
					Main.getChat().send( p__, msg, Main.getMessages().party_prefix );
				}
			}
		}
	}
}
