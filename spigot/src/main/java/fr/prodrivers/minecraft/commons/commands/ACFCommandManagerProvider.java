package fr.prodrivers.minecraft.commons.commands;

import co.aikar.commands.*;
import co.aikar.locales.MessageKeyProvider;
import com.google.common.collect.ImmutableMap;
import fr.prodrivers.minecraft.commons.Log;
import fr.prodrivers.minecraft.commons.chat.SystemMessage;
import fr.prodrivers.minecraft.commons.configuration.Messages;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.lang.reflect.Field;
import java.util.Locale;
import java.util.Map;

@Singleton
public class ACFCommandManagerProvider implements Provider<BukkitCommandManager> {
	private final BukkitCommandManager commandManager;

	private static Map<MessageKeyProvider, String> getMessageMap() {
		return new ImmutableMap.Builder<MessageKeyProvider, String>()
				.put(MessageKeys.ERROR_GENERIC_LOGGED, "error_occurred")
				.put(MessageKeys.COULD_NOT_FIND_PLAYER, "invalid_player")
				.put(MessageKeys.NOT_ALLOWED_ON_CONSOLE, "not_a_player")
				.put(MessageKeys.MUST_BE_A_NUMBER, "invalid_number")
				.put(MessageKeys.PERMISSION_DENIED, "permission_denied")
				.put(MinecraftMessageKeys.NO_PLAYER_FOUND, "invalid_player")
				.put(MinecraftMessageKeys.NO_PLAYER_FOUND_OFFLINE, "invalid_player")
				.put(MinecraftMessageKeys.NO_PLAYER_FOUND_SERVER, "invalid_player")
				.build();
	}

	@Inject
	@SuppressWarnings("deprecation")
	public ACFCommandManagerProvider(Plugin plugin, Messages messages, SystemMessage chat) {
		// Create manager
		this.commandManager = new PaperCommandManager(plugin);
		// Enable help API
		this.commandManager.enableUnstableAPI("help");
		// Load messages from message instance
		Locale defaultLocale = commandManager.getLocales().getDefaultLocale();
		this.commandManager.getLocales().loadLanguage(messages.getStorage(), defaultLocale);
		// Set formatter with prefix
		this.commandManager.setDefaultFormatter(new ProdriversMessageFormatter(chat));
		this.commandManager.setFormat(MessageType.ERROR, new ProdriversMessageFormatter(chat, ChatColor.RED, ChatColor.YELLOW, ChatColor.RED));
		this.commandManager.setFormat(MessageType.SYNTAX, new ProdriversMessageFormatter(chat, ChatColor.YELLOW, ChatColor.GREEN, ChatColor.WHITE));
		this.commandManager.setFormat(MessageType.INFO, new ProdriversMessageFormatter(chat, ChatColor.BLUE, ChatColor.DARK_GREEN, ChatColor.GREEN));
		this.commandManager.setFormat(MessageType.HELP, new ProdriversMessageFormatter(chat, ChatColor.AQUA, ChatColor.GREEN, ChatColor.YELLOW));
		// Add default messages
		for(Map.Entry<MessageKeyProvider, String> entry : getMessageMap().entrySet()) {
			try {
				Field messageField = messages.getClass().getDeclaredField(entry.getValue());
				this.commandManager.getLocales().addMessage(defaultLocale, entry.getKey(), (String) messageField.get(messages));
			} catch(NoSuchFieldException | IllegalAccessException | ClassCastException e) {
				Log.info(messages + " has no field named " + entry.getValue() + ", ignoring.");
			}
		}
	}

	@Override
	public BukkitCommandManager get() {
		return this.commandManager;
	}
}