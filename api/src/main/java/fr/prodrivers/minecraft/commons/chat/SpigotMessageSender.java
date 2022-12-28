package fr.prodrivers.minecraft.commons.chat;

import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.command.CommandSender;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.UUID;

/**
 * Default implementation of message sender helper that uses Bukkit/Spigot functions.
 * <p>
 * This implementation is intended for single servers.
 */
public class SpigotMessageSender implements MessageSender {
	public void send(@NonNull CommandSender sender, @NonNull String message, @NonNull String prefix) {
		throw new UnsupportedOperationException();
	}

	public void send(@NonNull CommandSender sender, @NonNull BaseComponent[] components, @NonNull String prefix) {
		throw new UnsupportedOperationException();
	}

	public void send(@NonNull UUID receiverPlayerUniqueId, @NonNull String message, @NonNull String prefix) {
		throw new UnsupportedOperationException();
	}

	public void send(@NonNull UUID receiverPlayerUniqueId, @NonNull BaseComponent[] components, @NonNull String prefix) {
		throw new UnsupportedOperationException();
	}
}
