package fr.prodrivers.minecraft.commons.commands;

import co.aikar.commands.BukkitMessageFormatter;
import fr.prodrivers.minecraft.commons.chat.SystemMessage;
import org.bukkit.ChatColor;

public class ProdriversMessageFormatter extends BukkitMessageFormatter {
	private final SystemMessage chat;

	public ProdriversMessageFormatter(SystemMessage chat, ChatColor... colors) {
		super(colors);
		this.chat = chat;
	}

	@Override
	public String format(String message) {
		return this.chat.getPrefixLegacy() + " " + super.format(message).stripLeading();
	}
}
