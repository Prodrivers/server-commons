package fr.prodrivers.bukkit.commons.chat;

import fr.prodrivers.bukkit.commons.configuration.Messages;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

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
	public Chat(MessageSender messageSender) {
		this.messageSender = messageSender;
	}

	public void setName(String name) {
		this.name = name;
		this.prefix = this.rawPrefix.replaceAll("<name>", this.name);
	}

	public void load(Messages messages) {
		if(messages != null && messages.prefix != null) {
			this.rawPrefix = messages.prefix;
		}
		this.prefix = this.rawPrefix.replaceAll("<name>", this.name);
	}

	public void send(CommandSender sender, String message) {
		send(sender, message, this.prefix);
	}

	public void send(CommandSender sender, String message, String prefix) {
		this.messageSender.send(sender, message, prefix);
	}

	public void success(CommandSender sender, String message) {
		success(sender, message, this.prefix);
	}

	public void success(CommandSender sender, String message, String prefix) {
		send(sender, ChatColor.GREEN + message, prefix);
	}

	public void error(CommandSender sender, String message) {
		send(sender, message, this.prefix);
	}

	public void error(CommandSender sender, String message, String prefix) {
		send(sender, ChatColor.RED + message, prefix);
	}

	public void send(CommandSender sender, BaseComponent[] components, String prefix) {
		this.messageSender.send(sender, components, prefix);
	}

	public void send(CommandSender sender, BaseComponent[] components) {
		send(sender, components, this.prefix);
	}

	public void success(CommandSender sender, BaseComponent[] components) {
		success(sender, components, this.prefix);
	}

	public void success(CommandSender sender, BaseComponent[] components, String prefix) {
		send(sender, (new ComponentBuilder("")).color(net.md_5.bungee.api.ChatColor.GREEN).append(components).create(), prefix);
	}

	public void error(CommandSender sender, BaseComponent[] components) {
		error(sender, components, this.prefix);
	}

	public void error(CommandSender sender, BaseComponent[] components, String prefix) {
		send(sender, (new ComponentBuilder("")).color(net.md_5.bungee.api.ChatColor.RED).append(components).create(), prefix);
	}

	public void send(UUID receiverPlayerUniqueId, String message) {
		send(receiverPlayerUniqueId, message, this.prefix);
	}

	public void send(UUID receiverPlayerUniqueId, String message, String prefix) {
		this.messageSender.send(receiverPlayerUniqueId, message, prefix);
	}

	public void success(UUID receiverPlayerUniqueId, String message) {
		send(receiverPlayerUniqueId, ChatColor.GREEN + message, this.prefix);
	}

	public void success(UUID receiverPlayerUniqueId, String message, String prefix) {
		send(receiverPlayerUniqueId, ChatColor.GREEN + message, prefix);
	}

	public void error(UUID receiverPlayerUniqueId, String message) {
		send(receiverPlayerUniqueId, message, this.prefix);
	}

	public void error(UUID receiverPlayerUniqueId, String message, String prefix) {
		send(receiverPlayerUniqueId, ChatColor.RED + message, prefix);
	}


	public void send(UUID receiverPlayerUniqueId, BaseComponent[] message) {
		send(receiverPlayerUniqueId, message, this.prefix);
	}

	public void send(UUID receiverPlayerUniqueId, BaseComponent[] message, String prefix) {
		this.messageSender.send(receiverPlayerUniqueId, message, prefix);
	}

	public void success(UUID receiverPlayerUniqueId, BaseComponent[] message) {
		success(receiverPlayerUniqueId, message, this.prefix);
	}

	public void success(UUID receiverPlayerUniqueId, BaseComponent[] message, String prefix) {
		send(receiverPlayerUniqueId, (new ComponentBuilder("")).color(net.md_5.bungee.api.ChatColor.GREEN).append(message).create(), prefix);
	}

	public void error(UUID receiverPlayerUniqueId, BaseComponent[] message) {
		error(receiverPlayerUniqueId, message, this.prefix);
	}

	public void error(UUID receiverPlayerUniqueId, BaseComponent[] message, String prefix) {
		send(receiverPlayerUniqueId, (new ComponentBuilder("")).color(net.md_5.bungee.api.ChatColor.RED).append(message).create(), prefix);
	}

	public String getPrefix() {
		return prefix;
	}
}
