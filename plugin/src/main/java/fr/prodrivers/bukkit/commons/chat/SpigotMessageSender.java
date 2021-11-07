package fr.prodrivers.bukkit.commons.chat;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class SpigotMessageSender implements MessageSender {
	public void send(CommandSender sender, String message, String prefix) {
		sender.sendMessage(prefix + ChatColor.RESET + " " + message);
	}

	public void send(CommandSender sender, BaseComponent[] components, String prefix) {
		ComponentBuilder builder = new ComponentBuilder("");
		builder.append(TextComponent.fromLegacyText(prefix + ChatColor.RESET + " "));
		builder.append(components);
		sender.spigot().sendMessage(builder.create());
	}

	public void send(UUID receiverPlayerUniqueId, String message, String prefix) {
		Player player = Bukkit.getPlayer(receiverPlayerUniqueId);
		if(player != null) {
			send(player, message, prefix);
		}
	}

	public void send(UUID receiverPlayerUniqueId, BaseComponent[] components, String prefix) {
		Player player = Bukkit.getPlayer(receiverPlayerUniqueId);
		if(player != null) {
			send(player, components, prefix);
		}
	}
}
