package fr.prodrivers.bukkit.commons.commands;

import fr.prodrivers.bukkit.commons.Log;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import javax.inject.Inject;
import java.util.HashSet;

public class CommandsBlocker implements CommandExecutor {
	private final HashSet<String> blocked = new HashSet<>();

	@Inject
	CommandsBlocker(JavaPlugin plugin) {
		populate();
		for(String cmd : blocked) {
			PluginCommand command = plugin.getCommand(cmd);
			if(command != null)
				command.setExecutor(this);
			else
				Log.warning("Command " + cmd + " could not be blocked.");
		}
	}

	private void populate() {
		blocked.clear();
		blocked.add("plugin");
		blocked.add("pl");
		blocked.add("help");
		blocked.add("bukkit");
		blocked.add("?");
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		return !sender.hasPermission("pcommons.commandblocker.bypass") && blocked.contains(label);
	}
}
