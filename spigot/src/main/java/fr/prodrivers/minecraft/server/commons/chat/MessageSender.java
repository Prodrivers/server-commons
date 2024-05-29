package fr.prodrivers.minecraft.server.commons.chat;

import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.command.CommandSender;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.UUID;

/**
 * Message sender helper for Prodrivers plugins.
 * <p>
 * Expose simple functions to send messages to players.
 * Handle colors directly. A prefix must be given as parameter.
 * Used as backend for Prodrivers Commons' chat facility.
 * <p>
 * Used implementation can be changed in configuration.
 */
public interface MessageSender {
	/**
	 * Generic message sender
	 *
	 * @param receiver Receiving entity
	 * @param message  Message to send
	 * @param prefix   Prefix to use
	 */
	void send(@NonNull CommandSender receiver, @NonNull String message, @NonNull String prefix);

	/**
	 * Generic message receiver, using a defined prefix
	 *
	 * @param receiver Receiving entity
	 * @param message  Message to send
	 * @param prefix   Prefix to use
	 */
	void send(@NonNull CommandSender receiver, @NonNull BaseComponent[] message, @NonNull String prefix);

	/**
	 * Generic message receiver, that potentially supports non-local players, using a defined prefix
	 *
	 * @param receiverPlayerUniqueId Receiving player
	 * @param message                Message to send
	 * @param prefix                 Prefix to use
	 */
	void send(@NonNull UUID receiverPlayerUniqueId, @NonNull String message, @NonNull String prefix);

	/**
	 * Generic message receiver, that potentially supports non-local players, using a defined prefix
	 *
	 * @param receiverPlayerUniqueId Receiving player
	 * @param message                Message to send
	 * @param prefix                 Prefix to use
	 */
	void send(@NonNull UUID receiverPlayerUniqueId, @NonNull BaseComponent[] message, @NonNull String prefix);
}
