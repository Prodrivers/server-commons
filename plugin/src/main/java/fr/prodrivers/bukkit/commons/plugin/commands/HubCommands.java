package fr.prodrivers.bukkit.commons.plugin.commands;

import fr.prodrivers.bukkit.commons.chat.Chat;
import fr.prodrivers.bukkit.commons.Log;
import fr.prodrivers.bukkit.commons.plugin.EMessages;
import fr.prodrivers.bukkit.commons.sections.SectionManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import javax.inject.Inject;

public class HubCommands implements CommandExecutor {
	private static final String label = "hub";

	private final SectionManager sectionManager;
	private final Chat chat;
	private final EMessages messages;

	@Inject
	HubCommands(JavaPlugin plugin, SectionManager sectionManager, Chat chat, EMessages messages) {
		this.chat = chat;
		this.messages = messages;
		this.sectionManager = sectionManager;
		plugin.getCommand(label).setExecutor(this);
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(label.equalsIgnoreCase(HubCommands.label)) {
			if(sender instanceof Player) {
				if(args.length > 0) {
					handledEnter((Player) sender, args[0]);
				} else {
					handledEnter((Player) sender);
				}
			} else {
				this.chat.error(sender, this.messages.player_only_command);
			}

			return true;
		}

		return false;
	}

	private void handledEnter(Player player) {
		try {
			this.sectionManager.enter(player);
		} catch(Exception e) {
			Log.severe("Unexpected error during hub command.", e);
		}
	}

	private void handledEnter(Player player, String name) {
		try {
			this.sectionManager.enter(player, name);
		} catch(Exception e) {
			Log.severe("Unexpected error during hub command.", e);
		}
	}
}
