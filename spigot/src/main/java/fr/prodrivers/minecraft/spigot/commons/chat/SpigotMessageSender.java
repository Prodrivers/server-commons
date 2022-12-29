package fr.prodrivers.minecraft.spigot.commons.chat;

import fr.prodrivers.minecraft.commons.chat.MessageSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.UUID;

public class SpigotMessageSender implements MessageSender {
	public void send(@NonNull CommandSender sender, @NonNull String message, @NonNull String prefix) {
		sender.sendMessage(prefix + ChatColor.RESET + " " + message);
	}

	public void send(@NonNull CommandSender sender, @NonNull BaseComponent[] components, @NonNull String prefix) {
		ComponentBuilder builder = new ComponentBuilder("");
		builder.append(TextComponent.fromLegacyText(prefix + ChatColor.RESET + " "));
		builder.append(components);
		sender.spigot().sendMessage(builder.create());
	}

	public void send(@NonNull UUID receiverPlayerUniqueId, @NonNull String message, @NonNull String prefix) {
		Player player = Bukkit.getPlayer(receiverPlayerUniqueId);
		if(player != null) {
			send(player, message, prefix);
		}
	}

	public void send(@NonNull UUID receiverPlayerUniqueId, @NonNull BaseComponent[] components, @NonNull String prefix) {
		Player player = Bukkit.getPlayer(receiverPlayerUniqueId);
		if(player != null) {
			send(player, components, prefix);
		}
	}
}
