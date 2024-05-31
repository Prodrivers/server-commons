package fr.prodrivers.minecraft.server.spigot.commons.plugin.commands;

import fr.prodrivers.minecraft.server.commons.Log;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import javax.inject.Inject;

import java.util.Arrays;
import java.util.HashSet;
import java.util.logging.Logger;

public class CommandsBlocker implements CommandExecutor {
	private final Logger logger;
	private final HashSet<String> blocked = new HashSet<>();

	@Inject
	CommandsBlocker(Logger logger, JavaPlugin plugin) {
		this.logger = logger;
		populate();
		for(String cmd : blocked) {
			PluginCommand command = plugin.getCommand(cmd);
			if(command != null)
				command.setExecutor(this);
			else
				this.logger.warning("Command " + cmd + " could not be blocked.");
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
		if(!sender.hasPermission("pcommons.commandblocker.bypass") && blocked.contains(label)) {
			this.logger.info("Sender '" + sender + "' has tried to use command '" + command + "' with arguments " + Arrays.toString(args));
			return true;
		}

		return false;
	}
}
