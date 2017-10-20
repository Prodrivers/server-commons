package fr.prodrivers.bukkit.commons.configuration;

import fr.prodrivers.bukkit.commons.configuration.file.FileAttributeConfiguration;
import org.bukkit.plugin.Plugin;

/**
 * Messages helper for Prodrivers plugins.
 * 
 * It represents messages using class fields, by saving and loading string fields from and inside the plugin's messages configuration file, using an underlying AbstractAttributeConfiguration.
 * Initialization and reloads are handled directly by the main Configuration instance.
 *
 * ExcludedFromConfiguration annotation allows specific fields not to be used by the field processor.
 * As with every AbstractAttributeConfiguration derivative, init() have to be called immediately after constructing the object.
 */
public class Messages extends FileAttributeConfiguration {
	public String prefix = "[<name>]";

	/**
	 * Messages helper constructor.
	 * @param plugin Plugin initializing the helper
	 */
	public Messages( Plugin plugin ) {
		super( null );
		throw new UnsupportedOperationException();
	}
}
