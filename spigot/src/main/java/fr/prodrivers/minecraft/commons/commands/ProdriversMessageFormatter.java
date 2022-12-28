package fr.prodrivers.minecraft.commons.commands;

import co.aikar.commands.BukkitMessageFormatter;
import fr.prodrivers.minecraft.commons.chat.Chat;
import org.bukkit.ChatColor;

public class ProdriversMessageFormatter extends BukkitMessageFormatter {
	private final Chat chat;

	public ProdriversMessageFormatter(Chat chat, ChatColor... colors) {
		super(colors);
		this.chat = chat;
	}

	@Override
	public String format(String message) {
		return this.chat.getPrefix() + " " + super.format(message).stripLeading();
	}
}
