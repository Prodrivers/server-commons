package fr.prodrivers.bukkit.commons.commands;

import fr.prodrivers.bukkit.commons.exceptions.*;
import fr.prodrivers.bukkit.commons.plugin.Main;
import fr.prodrivers.bukkit.commons.sections.MainHub;
import fr.prodrivers.bukkit.commons.sections.Section;
import fr.prodrivers.bukkit.commons.sections.SectionManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

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
			SectionManager.enter(player);
		} catch(Exception e)  {
			Main.logger.log(Level.SEVERE, "Unexpected error during hub command.", e);
		}
	}

	private void handledEnter( Player player, String name ) {
		try {
			SectionManager.enter( player, name );
		} catch(Exception e)  {
			Main.logger.log(Level.SEVERE, "Unexpected error during hub command.", e);
		}
	}
}
