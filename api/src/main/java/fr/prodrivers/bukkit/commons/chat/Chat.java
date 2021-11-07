package fr.prodrivers.bukkit.commons.chat;

import fr.prodrivers.bukkit.commons.configuration.Messages;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.command.CommandSender;

import java.util.UUID;

/**
 * Chat helper for Prodrivers plugins.
 * <p>
 * Expose simple send functions to send messages to players.
 * Handles prefix and colors directly.
 */
public class Chat {
	/**
	 * Constructor for the Chat helper for Prodrivers plugins
	 */
	public Chat(MessageSender messageSender) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Set the default name to use in prefix when a custom prefix is not specified
	 *
	 * @param name Name to be used in prefix
	 */
	public void setName(String name) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Load prefix from a message instance
	 *
	 * @param messages Message instance
	 */
	public void load(Messages messages) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Generic message receiver
	 *
	 * @param receiver Receiving entity
	 * @param message  Message to send
	 */
	public void send(CommandSender receiver, String message) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Generic message receiver, using a defined prefix
	 *
	 * @param receiver Receiving entity
	 * @param message  Message to send
	 * @param prefix   Prefix to use
	 */
	public void send(CommandSender receiver, String message, String prefix) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Success message receiver
	 *
	 * @param receiver Receiving entity
	 * @param message  Success message to send
	 */
	public void success(CommandSender receiver, String message) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Success message receiver, using a defined prefix
	 *
	 * @param receiver Receiving entity
	 * @param message  Message to send
	 * @param prefix   Prefix to use
	 */
	public void success(CommandSender receiver, String message, String prefix) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Error message receiver
	 *
	 * @param receiver Receiving entity
	 * @param message  Error message to send
	 */
	public void error(CommandSender receiver, String message) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Error message receiver, using a defined prefix
	 *
	 * @param receiver Receiving entity
	 * @param message  Message to send
	 * @param prefix   Prefix to use
	 */
	public void error(CommandSender receiver, String message, String prefix) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Generic message receiver
	 *
	 * @param receiver Receiving entity
	 * @param message  Message to send
	 */
	public void send(CommandSender receiver, BaseComponent[] message) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Generic message receiver, using a defined prefix
	 *
	 * @param receiver Receiving entity
	 * @param message  Message to send
	 * @param prefix   Prefix to use
	 */
	public void send(CommandSender receiver, BaseComponent[] message, String prefix) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Success message receiver
	 *
	 * @param receiver Receiving entity
	 * @param message  Success message to send
	 */
	public void success(CommandSender receiver, BaseComponent[] message) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Success message receiver, using a defined prefix
	 *
	 * @param receiver Receiving entity
	 * @param message  Message to send
	 * @param prefix   Prefix to use
	 */
	public void success(CommandSender receiver, BaseComponent[] message, String prefix) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Error message receiver
	 *
	 * @param receiver Receiving entity
	 * @param message  Error message to send
	 */
	public void error(CommandSender receiver, BaseComponent[] message) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Error message receiver, using a defined prefix
	 *
	 * @param receiver Receiving entity
	 * @param message  Message to send
	 * @param prefix   Prefix to use
	 */
	public void error(CommandSender receiver, BaseComponent[] message, String prefix) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Generic message receiver, that potentially supports non-local players
	 *
	 * @param receiverPlayerUniqueId Receiving player
	 * @param message                Message to send
	 */
	public void send(UUID receiverPlayerUniqueId, String message) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Generic message receiver, that potentially supports non-local players, using a defined prefix
	 *
	 * @param receiverPlayerUniqueId Receiving player
	 * @param message                Message to send
	 * @param prefix                 Prefix to use
	 */
	public void send(UUID receiverPlayerUniqueId, String message, String prefix) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Success message receiver, that potentially supports non-local players
	 *
	 * @param receiverPlayerUniqueId Receiving player
	 * @param message                Success message to send
	 */
	public void success(UUID receiverPlayerUniqueId, String message) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Success message receiver, that potentially supports non-local players, using a defined prefix
	 *
	 * @param receiverPlayerUniqueId Receiving player
	 * @param message                Success message to send
	 * @param prefix                 Prefix to use
	 */
	public void success(UUID receiverPlayerUniqueId, String message, String prefix) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Error message receiver, that potentially supports non-local players
	 *
	 * @param receiverPlayerUniqueId Receiving player
	 * @param message                Error message to send
	 */
	public void error(UUID receiverPlayerUniqueId, String message) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Error message receiver, that potentially supports non-local players, using a defined prefix
	 *
	 * @param receiverPlayerUniqueId Receiving player
	 * @param message                Success message to send
	 * @param prefix                 Prefix to use
	 */
	public void error(UUID receiverPlayerUniqueId, String message, String prefix) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Generic message receiver, that potentially supports non-local players
	 *
	 * @param receiverPlayerUniqueId Receiving player
	 * @param message                Message to send
	 */
	public void send(UUID receiverPlayerUniqueId, BaseComponent[] message) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Generic message receiver, that potentially supports non-local players, using a defined prefix
	 *
	 * @param receiverPlayerUniqueId Receiving player
	 * @param message                Message to send
	 * @param prefix                 Prefix to use
	 */
	public void send(UUID receiverPlayerUniqueId, BaseComponent[] message, String prefix) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Success message receiver, that potentially supports non-local players
	 *
	 * @param receiverPlayerUniqueId Receiving player
	 * @param message                Success message to send
	 */
	public void success(UUID receiverPlayerUniqueId, BaseComponent[] message) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Success message receiver, that potentially supports non-local players, using a defined prefix
	 *
	 * @param receiverPlayerUniqueId Receiving player
	 * @param message                Message to send
	 * @param prefix                 Prefix to use
	 */
	public void success(UUID receiverPlayerUniqueId, BaseComponent[] message, String prefix) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Error message receiver, that potentially supports non-local players
	 *
	 * @param receiverPlayerUniqueId Receiving player
	 * @param message                Error message to send
	 */
	public void error(UUID receiverPlayerUniqueId, BaseComponent[] message) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Error message receiver, that potentially supports non-local players, using a defined prefix
	 *
	 * @param receiverPlayerUniqueId Receiving player
	 * @param message                Message to send
	 * @param prefix                 Prefix to use
	 */
	public void error(UUID receiverPlayerUniqueId, BaseComponent[] message, String prefix) {
		throw new UnsupportedOperationException();
	}
}
