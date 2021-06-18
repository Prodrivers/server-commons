package fr.prodrivers.bukkit.commons.configuration;

import fr.prodrivers.bukkit.commons.Chat;
import fr.prodrivers.bukkit.commons.configuration.file.AbstractFileAttributeConfiguration;
import org.bukkit.plugin.Plugin;

/**
 * Configuration helper for Prodrivers plugins.
 * <p>
 * It represents configuration options using class fields, by saving and loading fields from and inside the plugin's
 * configuration file, using an underlying AbstractAttributeConfiguration.
 * It supports all data types supported by Bukkit's FileConfiguration.
 * <p>
 * ExcludedFromConfiguration annotation allows specific fields not to be used by the field processor.
 * As with every AbstractAttributeConfiguration derivative, init() have to be called immediately after constructing the
 * object, either at the end of the constructor or outside of it.
 */
public class Configuration extends AbstractFileAttributeConfiguration {
	/**
	 * Configuration helper constructor.
	 * Intended to be used with a dependency injector.
	 *
	 * @param plugin   Plugin initializing the helper
	 * @param messages Messages instance to manage, uses this to provide your own inheriting class that adds its own message fields
	 * @param chat     Chat instance to manage
	 */
	public Configuration(Plugin plugin, Messages messages, Chat chat) {
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
