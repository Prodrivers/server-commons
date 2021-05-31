package fr.prodrivers.bukkit.commons.commands;

import fr.prodrivers.bukkit.commons.plugin.Main;
import fr.prodrivers.bukkit.commons.sections.Section;
import fr.prodrivers.bukkit.commons.sections.SectionManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class MainPluginCommands implements CommandExecutor {
	private static String label = "pcommons";

	MainPluginCommands( JavaPlugin plugin ) {
		plugin.getCommand( label ).setExecutor( this );
	}

	public boolean onCommand( CommandSender sender, Command command, String label, String[] args ) {
		if( label.equalsIgnoreCase( MainPluginCommands.label ) ) {
			if( args.length > 0 ) {
				switch( args[ 0 ] ) {
					case "reload":
						if( sender.hasPermission( "pcommons.reload" ) )
							Main.getConfiguration().reload();
						break;
					case "sections":
						sectionsCommand( sender, args );
						break;
				}
			}

			return true;
		}

		return false;
	}

	private void sectionsCommand( CommandSender sender, String[] args ) {
		if( args.length > 1 ) {
			switch( args[ 1 ] ) {
				case "query":
					if( sender.hasPermission( "pcommons.section.query" ) ) {
						if( args.length > 2 ) {
							OfflinePlayer target = Bukkit.getPlayer( args[ 2 ] );
							if( target == null ) {
								target = Bukkit.getOfflinePlayer( args[ 2 ] );
							}
							Section section = SectionManager.getCurrentSection( target );

							if( section != null )
								Main.getChat().send( sender, target.getName() + " -> " + section.getFullName() + " (" + section.getClass().getCanonicalName() + ")" );
							else
								Main.getChat().send( sender, target.getName() + " -> No registered section" );
						}
					}
					break;
			}
		}
	}
}
