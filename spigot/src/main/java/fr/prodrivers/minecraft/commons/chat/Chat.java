package fr.prodrivers.minecraft.commons.chat;

import fr.prodrivers.minecraft.commons.configuration.Messages;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.UUID;

@Singleton
public class Chat {
	private final MessageSender messageSender;

	private String rawPrefix = "[<name>]";
	private String prefix = "[<name>]";
	private String name;

	@Inject
	public Chat(@NonNull MessageSender messageSender) {
		this.messageSender = messageSender;
	}

	public void setName(@NonNull String name) {
		this.name = name;
		this.prefix = this.rawPrefix.replaceAll("<name>", this.name);
	}

	public void load(@NonNull Messages messages) {
		if(messages != null && messages.prefix != null) {
			this.rawPrefix = messages.prefix;
		}
		this.prefix = this.rawPrefix.replaceAll("<name>", this.name);
	}

	public void send(@NonNull CommandSender sender, @NonNull String message) {
		send(sender, message, this.prefix);
	}

	public void send(@NonNull CommandSender sender, @NonNull String message, @NonNull String prefix) {
		this.messageSender.send(sender, message, prefix);
	}

	public void success(@NonNull CommandSender sender, @NonNull String message) {
		success(sender, message, this.prefix);
	}

	public void success(@NonNull CommandSender sender, @NonNull String message, @NonNull String prefix) {
		send(sender, ChatColor.GREEN + message, prefix);
	}

	public void error(@NonNull CommandSender sender, @NonNull String message) {
		send(sender, message, this.prefix);
	}

	public void error(@NonNull CommandSender sender, @NonNull String message, @NonNull String prefix) {
		send(sender, ChatColor.RED + message, prefix);
	}

	public void send(@NonNull CommandSender sender, @NonNull BaseComponent[] message, @NonNull String prefix) {
		this.messageSender.send(sender, message, prefix);
	}

	public void send(@NonNull CommandSender sender, @NonNull BaseComponent[] message) {
		send(sender, message, this.prefix);
	}

	public void success(@NonNull CommandSender sender, @NonNull BaseComponent[] message) {
		success(sender, message, this.prefix);
	}

	public void success(@NonNull CommandSender sender, @NonNull BaseComponent[] message, @NonNull String prefix) {
		send(sender, (new ComponentBuilder("")).color(net.md_5.bungee.api.ChatColor.GREEN).append(message).create(), prefix);
	}

	public void error(@NonNull CommandSender sender, @NonNull BaseComponent[] message) {
		error(sender, message, this.prefix);
	}

	public void error(@NonNull CommandSender sender, @NonNull BaseComponent[] message, @NonNull String prefix) {
		send(sender, (new ComponentBuilder("")).color(net.md_5.bungee.api.ChatColor.RED).append(message).create(), prefix);
	}

	public void send(@NonNull UUID receiverPlayerUniqueId, @NonNull String message) {
		send(receiverPlayerUniqueId, message, this.prefix);
	}

	public void send(@NonNull UUID receiverPlayerUniqueId, @NonNull String message, @NonNull String prefix) {
		this.messageSender.send(receiverPlayerUniqueId, message, prefix);
	}

	public void success(@NonNull UUID receiverPlayerUniqueId, @NonNull String message) {
		send(receiverPlayerUniqueId, ChatColor.GREEN + message, this.prefix);
	}

	public void success(@NonNull UUID receiverPlayerUniqueId, @NonNull String message, @NonNull String prefix) {
		send(receiverPlayerUniqueId, ChatColor.GREEN + message, prefix);
	}

	public void error(@NonNull UUID receiverPlayerUniqueId, @NonNull String message) {
		send(receiverPlayerUniqueId, message, this.prefix);
	}

	public void error(@NonNull UUID receiverPlayerUniqueId, @NonNull String message, @NonNull String prefix) {
		send(receiverPlayerUniqueId, ChatColor.RED + message, prefix);
	}


	public void send(@NonNull UUID receiverPlayerUniqueId, @NonNull BaseComponent[] message) {
		send(receiverPlayerUniqueId, message, this.prefix);
	}

	public void send(@NonNull UUID receiverPlayerUniqueId, @NonNull BaseComponent[] message, @NonNull String prefix) {
		this.messageSender.send(receiverPlayerUniqueId, message, prefix);
	}

	public void success(@NonNull UUID receiverPlayerUniqueId, @NonNull BaseComponent[] message) {
		success(receiverPlayerUniqueId, message, this.prefix);
	}

	public void success(@NonNull UUID receiverPlayerUniqueId, @NonNull BaseComponent[] message, @NonNull String prefix) {
		send(receiverPlayerUniqueId, (new ComponentBuilder("")).color(net.md_5.bungee.api.ChatColor.GREEN).append(message).create(), prefix);
	}

	public void error(@NonNull UUID receiverPlayerUniqueId, @NonNull BaseComponent[] message) {
		error(receiverPlayerUniqueId, message, this.prefix);
	}

	public void error(@NonNull UUID receiverPlayerUniqueId, @NonNull BaseComponent[] message, @NonNull String prefix) {
		send(receiverPlayerUniqueId, (new ComponentBuilder("")).color(net.md_5.bungee.api.ChatColor.RED).append(message).create(), prefix);
	}

	public String getPrefix() {
		return prefix;
	}
}
