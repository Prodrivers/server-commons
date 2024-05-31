package fr.prodrivers.minecraft.server.commons.chat;

import fr.prodrivers.minecraft.server.commons.configuration.Messages;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.checkerframework.checker.nullness.qual.NonNull;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.UUID;

/**
 * Chat helper for Prodrivers plugins.
 * <p>
 * Expose more advanced functions to send messages to players.
 * Handles prefix and colors directly.
 */
@Deprecated
@Singleton
public class Chat {
	private final MessageSender messageSender;

	private String rawPrefix = "[<name>]";
	private String prefix = "[<name>]";
	private String name;

	/**
	 * Constructor for the Chat helper for Prodrivers plugins
	 *
	 * @param messageSender {@link MessageSender} instance to use as backend
	 */
	@Inject
	public Chat(@NonNull MessageSender messageSender) {
		this.messageSender = messageSender;
	}

	/**
	 * Set the default name to use in prefix when a custom prefix is not specified
	 *
	 * @param name Name to be used in prefix
	 */
	public void setName(@NonNull String name) {
		this.name = name;
		this.prefix = this.rawPrefix.replaceAll("<name>", this.name);
	}

	/**
	 * Load prefix from a message instance
	 *
	 * @param messages Message instance
	 */
	public void load(@NonNull Messages messages) {
		if(messages != null && messages.prefix != null) {
			this.rawPrefix = messages.prefix;
		}
		this.prefix = this.rawPrefix.replaceAll("<name>", this.name);
	}

	/**
	 * Generic message sender
	 *
	 * @param receiver Receiving entity
	 * @param message  Message to send
	 */
	public void send(@NonNull CommandSender receiver, @NonNull String message) {
		send(receiver, message, this.prefix);
	}

	/**
	 * Generic message sender, using a defined prefix
	 *
	 * @param receiver Receiving entity
	 * @param message  Message to send
	 * @param prefix   Prefix to use
	 */
	public void send(@NonNull CommandSender receiver, @NonNull String message, @NonNull String prefix) {
		this.messageSender.send(receiver, message, prefix);
	}

	/**
	 * Success message sender
	 *
	 * @param receiver Receiving entity
	 * @param message  Success message to send
	 */
	public void success(@NonNull CommandSender receiver, @NonNull String message) {
		success(receiver, message, this.prefix);
	}

	/**
	 * Success message sender, using a defined prefix
	 *
	 * @param receiver Receiving entity
	 * @param message  Message to send
	 * @param prefix   Prefix to use
	 */
	public void success(@NonNull CommandSender receiver, @NonNull String message, @NonNull String prefix) {
		send(receiver, ChatColor.GREEN + message, prefix);
	}

	/**
	 * Error message sender
	 *
	 * @param receiver Receiving entity
	 * @param message  Error message to send
	 */
	public void error(@NonNull CommandSender receiver, @NonNull String message) {
		send(receiver, message, this.prefix);
	}

	/**
	 * Error message sender, using a defined prefix
	 *
	 * @param receiver Receiving entity
	 * @param message  Message to send
	 * @param prefix   Prefix to use
	 */
	public void error(@NonNull CommandSender receiver, @NonNull String message, @NonNull String prefix) {
		send(receiver, ChatColor.RED + message, prefix);
	}

	/**
	 * Generic message sender
	 *
	 * @param receiver Receiving entity
	 * @param message  Message to send
	 */
	public void send(@NonNull CommandSender receiver, @NonNull BaseComponent[] message) {
		send(receiver, message, this.prefix);
	}

	/**
	 * Generic message sender, using a defined prefix
	 *
	 * @param receiver Receiving entity
	 * @param message  Message to send
	 * @param prefix   Prefix to use
	 */
	public void send(@NonNull CommandSender receiver, @NonNull BaseComponent[] message, @NonNull String prefix) {
		this.messageSender.send(receiver, message, prefix);
	}

	/**
	 * Success message sender
	 *
	 * @param receiver Receiving entity
	 * @param message  Success message to send
	 */
	public void success(@NonNull CommandSender receiver, @NonNull BaseComponent[] message) {
		success(receiver, message, this.prefix);
	}

	/**
	 * Success message sender, using a defined prefix
	 *
	 * @param receiver Receiving entity
	 * @param message  Message to send
	 * @param prefix   Prefix to use
	 */
	public void success(@NonNull CommandSender receiver, @NonNull BaseComponent[] message, @NonNull String prefix) {
		send(receiver, (new ComponentBuilder("")).color(net.md_5.bungee.api.ChatColor.GREEN).append(message).create(), prefix);
	}

	/**
	 * Error message sender
	 *
	 * @param receiver Receiving entity
	 * @param message  Error message to send
	 */
	public void error(@NonNull CommandSender receiver, @NonNull BaseComponent[] message) {
		error(receiver, message, this.prefix);
	}

	/**
	 * Error message sender, using a defined prefix
	 *
	 * @param receiver Receiving entity
	 * @param message  Message to send
	 * @param prefix   Prefix to use
	 */
	public void error(@NonNull CommandSender receiver, @NonNull BaseComponent[] message, @NonNull String prefix) {
		send(receiver, (new ComponentBuilder("")).color(net.md_5.bungee.api.ChatColor.RED).append(message).create(), prefix);
	}

	/**
	 * Generic message sender, that potentially supports non-local players
	 *
	 * @param receiverPlayerUniqueId Receiving player
	 * @param message                Message to send
	 */
	public void send(@NonNull UUID receiverPlayerUniqueId, @NonNull String message) {
		send(receiverPlayerUniqueId, message, this.prefix);
	}

	/**
	 * Generic message sender, that potentially supports non-local players, using a defined prefix
	 *
	 * @param receiverPlayerUniqueId Receiving player
	 * @param message                Message to send
	 * @param prefix                 Prefix to use
	 */
	public void send(@NonNull UUID receiverPlayerUniqueId, @NonNull String message, @NonNull String prefix) {
		this.messageSender.send(receiverPlayerUniqueId, message, prefix);
	}

	/**
	 * Success message sender, that potentially supports non-local players
	 *
	 * @param receiverPlayerUniqueId Receiving player
	 * @param message                Success message to send
	 */
	public void success(@NonNull UUID receiverPlayerUniqueId, @NonNull String message) {
		send(receiverPlayerUniqueId, ChatColor.GREEN + message, this.prefix);
	}

	/**
	 * Success message sender, that potentially supports non-local players, using a defined prefix
	 *
	 * @param receiverPlayerUniqueId Receiving player
	 * @param message                Success message to send
	 * @param prefix                 Prefix to use
	 */
	public void success(@NonNull UUID receiverPlayerUniqueId, @NonNull String message, @NonNull String prefix) {
		send(receiverPlayerUniqueId, ChatColor.GREEN + message, prefix);
	}

	/**
	 * Error message sender, that potentially supports non-local players
	 *
	 * @param receiverPlayerUniqueId Receiving player
	 * @param message                Error message to send
	 */
	public void error(@NonNull UUID receiverPlayerUniqueId, @NonNull String message) {
		send(receiverPlayerUniqueId, message, this.prefix);
	}

	/**
	 * Error message sender, that potentially supports non-local players, using a defined prefix
	 *
	 * @param receiverPlayerUniqueId Receiving player
	 * @param message                Success message to send
	 * @param prefix                 Prefix to use
	 */
	public void error(@NonNull UUID receiverPlayerUniqueId, @NonNull String message, @NonNull String prefix) {
		send(receiverPlayerUniqueId, ChatColor.RED + message, prefix);
	}

	/**
	 * Generic message sender, that potentially supports non-local players
	 *
	 * @param receiverPlayerUniqueId Receiving player
	 * @param message                Message to send
	 */
	public void send(@NonNull UUID receiverPlayerUniqueId, @NonNull BaseComponent[] message) {
		send(receiverPlayerUniqueId, message, this.prefix);
	}

	/**
	 * Generic message sender, that potentially supports non-local players, using a defined prefix
	 *
	 * @param receiverPlayerUniqueId Receiving player
	 * @param message                Message to send
	 * @param prefix                 Prefix to use
	 */
	public void send(@NonNull UUID receiverPlayerUniqueId, @NonNull BaseComponent[] message, @NonNull String prefix) {
		this.messageSender.send(receiverPlayerUniqueId, message, prefix);
	}

	/**
	 * Success message sender, that potentially supports non-local players
	 *
	 * @param receiverPlayerUniqueId Receiving player
	 * @param message                Success message to send
	 */
	public void success(@NonNull UUID receiverPlayerUniqueId, @NonNull BaseComponent[] message) {
		success(receiverPlayerUniqueId, message, this.prefix);
	}

	/**
	 * Success message sender, that potentially supports non-local players, using a defined prefix
	 *
	 * @param receiverPlayerUniqueId Receiving player
	 * @param message                Message to send
	 * @param prefix                 Prefix to use
	 */
	public void success(@NonNull UUID receiverPlayerUniqueId, @NonNull BaseComponent[] message, @NonNull String prefix) {
		send(receiverPlayerUniqueId, (new ComponentBuilder("")).color(net.md_5.bungee.api.ChatColor.GREEN).append(message).create(), prefix);
	}

	/**
	 * Error message sender, that potentially supports non-local players
	 *
	 * @param receiverPlayerUniqueId Receiving player
	 * @param message                Error message to send
	 */
	public void error(@NonNull UUID receiverPlayerUniqueId, @NonNull BaseComponent[] message) {
		error(receiverPlayerUniqueId, message, this.prefix);
	}

	/**
	 * Error message sender, that potentially supports non-local players, using a defined prefix
	 *
	 * @param receiverPlayerUniqueId Receiving player
	 * @param message                Message to send
	 * @param prefix                 Prefix to use
	 */
	public void error(@NonNull UUID receiverPlayerUniqueId, @NonNull BaseComponent[] message, @NonNull String prefix) {
		send(receiverPlayerUniqueId, (new ComponentBuilder("")).color(net.md_5.bungee.api.ChatColor.RED).append(message).create(), prefix);
	}

	public String getPrefix() {
		return prefix;
	}
}
