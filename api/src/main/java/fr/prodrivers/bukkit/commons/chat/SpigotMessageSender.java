package fr.prodrivers.bukkit.commons.chat;

import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.command.CommandSender;

import java.util.UUID;

/**
 * Default implementation of message sender helper that uses Bukkit/Spigot functions.
 *
 * This implementation is intended for single servers.
 */
public class SpigotMessageSender implements MessageSender {
	public void send(CommandSender sender, String message, String prefix) {
		throw new UnsupportedOperationException();
	}

	public void send(CommandSender sender, BaseComponent[] components, String prefix) {
		throw new UnsupportedOperationException();
	}

	public void send(UUID receiverPlayerUniqueId, String message, String prefix) {
		throw new UnsupportedOperationException();
	}

	public void send(UUID receiverPlayerUniqueId, BaseComponent[] components, String prefix) {
		throw new UnsupportedOperationException();
	}
}
