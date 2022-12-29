package fr.prodrivers.minecraft.commons.chat;

import fr.prodrivers.minecraft.commons.configuration.Messages;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.command.CommandSender;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.UUID;

/**
 * SystemMessage helper for Prodrivers plugins.
 * <p>
 * Expose more advanced functions to send messages to players.
 * Handles prefix and colors directly.
 */
public class SystemMessage {
	/**
	 * Constructor for the Chat helper for Prodrivers plugins
	 *
	 * @param messageSender {@link MessageSender} instance to use as backend
	 */
	public SystemMessage(@NonNull MessageSender messageSender) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Set the default name to use in prefix when a custom prefix is not specified
	 *
	 * @param name Name to be used in prefix
	 */
	public void setName(@NonNull String name) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Load prefix from a message instance
	 *
	 * @param messages Message instance
	 */
	public void load(@NonNull Messages messages) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Success message receiver
	 *
	 * @param receiver Receiving entity
	 * @param message  Success message to send
	 */
	public void success(@NonNull CommandSender receiver, @NonNull String message) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Success message receiver, using a defined prefix
	 *
	 * @param receiver Receiving entity
	 * @param message  Message to send
	 * @param prefix   Prefix to use
	 */
	public void success(@NonNull CommandSender receiver, @NonNull String message, @NonNull String prefix) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Generic message receiver
	 *
	 * @param receiver Receiving entity
	 * @param message  Success message to send
	 */
	public void info(@NonNull CommandSender receiver, @NonNull String message) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Success message receiver, using a defined prefix
	 *
	 * @param receiver Receiving entity
	 * @param message  Message to send
	 * @param prefix   Prefix to use
	 */
	public void info(@NonNull CommandSender receiver, @NonNull String message, @NonNull String prefix) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Error message receiver
	 *
	 * @param receiver Receiving entity
	 * @param message  Error message to send
	 */
	public void error(@NonNull CommandSender receiver, @NonNull String message) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Error message receiver, using a defined prefix
	 *
	 * @param receiver Receiving entity
	 * @param message  Message to send
	 * @param prefix   Prefix to use
	 */
	public void error(@NonNull CommandSender receiver, @NonNull String message, @NonNull String prefix) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Success message receiver
	 *
	 * @param receiver Receiving entity
	 * @param message  Success message to send
	 */
	public void success(@NonNull CommandSender receiver, @NonNull BaseComponent[] message) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Success message receiver, using a defined prefix
	 *
	 * @param receiver Receiving entity
	 * @param message  Message to send
	 * @param prefix   Prefix to use
	 */
	public void success(@NonNull CommandSender receiver, @NonNull BaseComponent[] message, @NonNull String prefix) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Generic message receiver
	 *
	 * @param receiver Receiving entity
	 * @param message  Success message to send
	 */
	public void info(@NonNull CommandSender receiver, @NonNull BaseComponent[] message) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Generic message receiver, using a defined prefix
	 *
	 * @param receiver Receiving entity
	 * @param message  Message to send
	 * @param prefix   Prefix to use
	 */
	public void info(@NonNull CommandSender receiver, @NonNull BaseComponent[] message, @NonNull String prefix) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Error message receiver
	 *
	 * @param receiver Receiving entity
	 * @param message  Error message to send
	 */
	public void error(@NonNull CommandSender receiver, @NonNull BaseComponent[] message) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Error message receiver, using a defined prefix
	 *
	 * @param receiver Receiving entity
	 * @param message  Message to send
	 * @param prefix   Prefix to use
	 */
	public void error(@NonNull CommandSender receiver, @NonNull BaseComponent[] message, @NonNull String prefix) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Success message receiver, that potentially supports non-local players
	 *
	 * @param receiverPlayerUniqueId Receiving player
	 * @param message                Success message to send
	 */
	public void success(@NonNull UUID receiverPlayerUniqueId, @NonNull String message) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Success message receiver, that potentially supports non-local players, using a defined prefix
	 *
	 * @param receiverPlayerUniqueId Receiving player
	 * @param message                Success message to send
	 * @param prefix                 Prefix to use
	 */
	public void success(@NonNull UUID receiverPlayerUniqueId, @NonNull String message, @NonNull String prefix) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Generic message receiver, that potentially supports non-local players
	 *
	 * @param receiverPlayerUniqueId Receiving player
	 * @param message                Success message to send
	 */
	public void info(@NonNull UUID receiverPlayerUniqueId, @NonNull String message) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Generic message receiver, that potentially supports non-local players, using a defined prefix
	 *
	 * @param receiverPlayerUniqueId Receiving player
	 * @param message                Success message to send
	 * @param prefix                 Prefix to use
	 */
	public void info(@NonNull UUID receiverPlayerUniqueId, @NonNull String message, @NonNull String prefix) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Error message receiver, that potentially supports non-local players
	 *
	 * @param receiverPlayerUniqueId Receiving player
	 * @param message                Error message to send
	 */
	public void error(@NonNull UUID receiverPlayerUniqueId, @NonNull String message) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Error message receiver, that potentially supports non-local players, using a defined prefix
	 *
	 * @param receiverPlayerUniqueId Receiving player
	 * @param message                Success message to send
	 * @param prefix                 Prefix to use
	 */
	public void error(@NonNull UUID receiverPlayerUniqueId, @NonNull String message, @NonNull String prefix) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Success message receiver, that potentially supports non-local players
	 *
	 * @param receiverPlayerUniqueId Receiving player
	 * @param message                Success message to send
	 */
	public void success(@NonNull UUID receiverPlayerUniqueId, @NonNull BaseComponent[] message) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Success message receiver, that potentially supports non-local players, using a defined prefix
	 *
	 * @param receiverPlayerUniqueId Receiving player
	 * @param message                Message to send
	 * @param prefix                 Prefix to use
	 */
	public void success(@NonNull UUID receiverPlayerUniqueId, @NonNull BaseComponent[] message, @NonNull String prefix) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Generic message receiver, that potentially supports non-local players
	 *
	 * @param receiverPlayerUniqueId Receiving player
	 * @param message                Success message to send
	 */
	public void info(@NonNull UUID receiverPlayerUniqueId, @NonNull BaseComponent[] message) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Generic message receiver, that potentially supports non-local players, using a defined prefix
	 *
	 * @param receiverPlayerUniqueId Receiving player
	 * @param message                Message to send
	 * @param prefix                 Prefix to use
	 */
	public void info(@NonNull UUID receiverPlayerUniqueId, @NonNull BaseComponent[] message, @NonNull String prefix) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Error message receiver, that potentially supports non-local players
	 *
	 * @param receiverPlayerUniqueId Receiving player
	 * @param message                Error message to send
	 */
	public void error(@NonNull UUID receiverPlayerUniqueId, @NonNull BaseComponent[] message) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Error message receiver, that potentially supports non-local players, using a defined prefix
	 *
	 * @param receiverPlayerUniqueId Receiving player
	 * @param message                Message to send
	 * @param prefix                 Prefix to use
	 */
	public void error(@NonNull UUID receiverPlayerUniqueId, @NonNull BaseComponent[] message, @NonNull String prefix) {
		throw new UnsupportedOperationException();
	}
}
