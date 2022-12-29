package fr.prodrivers.minecraft.spigot.commons.chat;

import fr.prodrivers.minecraft.commons.chat.MessageSender;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.UUID;

public class PaperMessageSender implements MessageSender {
	public void send(@NonNull UUID receiverPlayerUniqueId, @NonNull Component message, @NonNull Component prefix) {
		Audience player = Bukkit.getPlayer(receiverPlayerUniqueId);
		if(player != null) {
			send(player, message, prefix);
		}
	}
}
