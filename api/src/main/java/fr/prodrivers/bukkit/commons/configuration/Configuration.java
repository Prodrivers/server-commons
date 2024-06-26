package fr.prodrivers.bukkit.commons.configuration;

import fr.prodrivers.bukkit.commons.chat.Chat;
import fr.prodrivers.bukkit.commons.configuration.file.AbstractFileAttributeConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.logging.Logger;

/**
 * Configuration helper for Prodrivers plugins.
 * <p>
 * It represents configuration options using class fields, by saving and loading fields from and inside the plugin's
 * configuration file, using an underlying {@link AbstractAttributeConfiguration}.
 * It supports all data types supported by Bukkit's {@link org.bukkit.configuration.file.FileConfiguration}.
 * <p>
 * {@link fr.prodrivers.bukkit.commons.annotations.ExcludedFromConfiguration} annotation allows specific fields not to
 * be used by the field processor.
 * As with every {@link AbstractAttributeConfiguration} derivative, {@link #init()} have to be called immediately after
 * constructing the object, either at the end of the constructor or outside of it.
 */
public class Configuration extends AbstractFileAttributeConfiguration {
	/**
	 * Configuration helper constructor.
	 * Intended to be used with a dependency injector.
	 *
	 * @param logger   Logger to use
	 * @param plugin   Plugin initializing the helper
	 * @param messages Messages instance to manage, uses this to provide your own inheriting class that adds its own message fields
	 */
	public Configuration(Logger logger, Plugin plugin, Messages messages) {
		super(logger);
		throw new UnsupportedOperationException();
	}

	/**
	 * Set chat instance to be managed.
	 *
	 * @param chat Chat instance to manage
	 */
	public void setChat(Chat chat) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Get the initialized Messages instance.
	 *
	 * @return Messages instance
	 */
	public Messages getMessages() {
		throw new UnsupportedOperationException();
	}
}
