package fr.prodrivers.bukkit.commons.commands;

import fr.prodrivers.bukkit.commons.exceptions.*;
import fr.prodrivers.bukkit.commons.plugin.EMessages;
import fr.prodrivers.bukkit.commons.plugin.Main;
import fr.prodrivers.bukkit.commons.sections.IProdriversSection;
import fr.prodrivers.bukkit.commons.sections.MainHub;
import fr.prodrivers.bukkit.commons.sections.SectionManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class HubCommands implements CommandExecutor {
	private static String label = "hub";

	HubCommands( JavaPlugin plugin ) {
		plugin.getCommand( label ).setExecutor( this );
	}

	public boolean onCommand( CommandSender sender, Command command, String label, String[] args ) {
		if( label.equalsIgnoreCase( HubCommands.label ) ) {
			if( sender instanceof Player ) {
				if( args.length > 0 ) {
					handledEnter( (Player) sender, args[ 0 ] );
				} else {
					handledEnter( (Player) sender );
				}
			} else {
				Main.getChat().error( sender, Main.getMessages().player_only_command );
			}

			return true;
		}

		return false;
	}

	private void handledEnter( Player player ) {
		try {
			SectionManager.enter( player );
		} catch( NoCurrentSectionException | NoNextSectionException ex ) {
			handledEnter( player, MainHub.name );
		} catch( IllegalSectionLeavingException ex ) {
			Main.getChat().error( player, Main.getMessages().section_leaving_denied );
		} catch( IllegalSectionEnteringException ex ) {
			IProdriversSection section = ( SectionManager.getCurrentSection( player ) != null && SectionManager.getCurrentSection( player ).getPreferredNextSection() != null ? SectionManager.getSection( SectionManager.getCurrentSection( player ).getPreferredNextSection() ) : null );
			if( section != null ) {
				if( section.getName().equals( MainHub.name ) ) {
					( player ).kickPlayer( Main.getMessages().kicked_because_of_section_enter_denied );
				} else {
					Main.getChat().error( player, Main.getMessages().kicked_to_mainhub_because_of_section_enter_denied );
					handledEnter( player, MainHub.name );
				}
			}
		}
	}

	private void handledEnter( Player player, String name ) {
		try {
			SectionManager.enter( player, name );
		} catch( InvalidSectionException ex ) {
			Main.getChat().error( player, Main.getMessages().invalid_hub_name );
		} catch( IllegalSectionLeavingException ex ) {
			Main.getChat().error( player, Main.getMessages().section_leaving_denied );
		} catch( IllegalSectionEnteringException ex ) {
			IProdriversSection section = SectionManager.getSection( name );
			if( section != null ) {
				if( section.getName().equals( MainHub.name ) ) {
					( player ).kickPlayer( Main.getMessages().kicked_because_of_section_enter_denied );
				} else {
					Main.getChat().error( player, Main.getMessages().kicked_to_mainhub_because_of_section_enter_denied );
					handledEnter( player, MainHub.name );
				}
			}
		}
	}
}
