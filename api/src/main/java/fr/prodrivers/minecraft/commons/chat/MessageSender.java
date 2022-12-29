package fr.prodrivers.minecraft.commons.chat;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
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
 * <p>
 * Default implementations are:
 * - fr.prodrivers.minecraft.spigot.commons.chat.SpigotMessageSender: for Spigot platform, uses local Spigot server functions
 */
public interface MessageSender {
	/**
	 * Generic message receiver, using a defined prefix
	 *
	 * @param receiver Receiving audience
	 * @param message  Message to send
	 * @param prefix   Prefix to use
	 */
	default void send(@NonNull Audience receiver, @NonNull Component message, @NonNull Component prefix) {
		receiver.sendMessage(prefix.append(message));
	}

	/**
	 * Generic message receiver, that potentially supports non-local players, using a defined prefix
	 *
	 * @param receiverPlayerUniqueId Receiving player
	 * @param message                Message to send
	 * @param prefix                 Prefix to use
	 */
	void send(@NonNull UUID receiverPlayerUniqueId, @NonNull Component message, @NonNull Component prefix);
}
