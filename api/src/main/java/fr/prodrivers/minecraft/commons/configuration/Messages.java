package fr.prodrivers.minecraft.commons.configuration;

import fr.prodrivers.minecraft.commons.chat.Chat;
import fr.prodrivers.minecraft.commons.annotations.ExcludedFromConfiguration;
import fr.prodrivers.minecraft.commons.configuration.file.FileAttributeConfiguration;
import org.bukkit.plugin.Plugin;

/**
 * Messages helper for Prodrivers plugins.
 * <p>
 * It represents messages using class fields, by saving and loading string fields from and inside the plugin's messages
 * configuration file, using an underlying {@link AbstractAttributeConfiguration}.
 * Initialization and reloads are handled directly by the main {@link Configuration} instance.
 * <p>
 * {@link ExcludedFromConfiguration} annotation allows specific fields not t
 * be used by the field processor.
 * If it is not used in pair with a {@link Configuration} derivative, {@link #init()} have to be called immediately
 * after constructing the object, either at the end of the constructor or outside of it.
 */
public class Messages extends FileAttributeConfiguration {
	/**
	 * Prefix to use in messages sent using {@link Chat Chat}
	 */
	public String prefix = "[<name>]";

	/**
	 * Messages helper constructor.
	 *
	 * @param plugin Plugin initializing the helper
	 */
	public Messages(Plugin plugin) {
		super(null);
		throw new UnsupportedOperationException();
	}
}
