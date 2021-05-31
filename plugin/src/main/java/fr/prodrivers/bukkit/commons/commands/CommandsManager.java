package fr.prodrivers.bukkit.commons.commands;

import org.bukkit.plugin.java.JavaPlugin;

public class CommandsManager {
	public static void init(JavaPlugin plugin) {
		new MainPluginCommands(plugin);
		new HubCommands(plugin);
		new CommandsBlocker(plugin);
		new PartyCommands(plugin);
	}

	public static void reload() {
	}
}
