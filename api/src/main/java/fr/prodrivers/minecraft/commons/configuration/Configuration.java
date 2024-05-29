package fr.prodrivers.minecraft.commons.configuration;

import fr.prodrivers.minecraft.commons.chat.SystemMessage;
import fr.prodrivers.minecraft.commons.annotations.ExcludedFromConfiguration;
import fr.prodrivers.minecraft.commons.plugins.PPlugin;

/**
 * Configuration helper for Prodrivers plugins.
 * <p>
 * It represents configuration options using class fields, by saving and loading fields from and inside the plugin's
 * configuration file, using an underlying {@link AbstractAttributeConfiguration}.
 * Data types supports is best-effort and depends on your implementation.
 * <p>
 * {@link ExcludedFromConfiguration} annotation allows specific fields not to
 * be used by the field processor.
 * As with every {@link AbstractAttributeConfiguration} derivative, {@link #init()} have to be called immediately after
 * constructing the object, either at the end of the constructor or outside of it.
 */
public class Configuration extends AbstractAttributeConfiguration {
	/**
	 * Configuration helper constructor.
	 * Intended to be used with a dependency injector.
	 *
	 * @param plugin   Plugin initializing the helper
	 * @param messages Messages instance to manage, uses this to provide your own inheriting class that adds its own message fields
	 */
	public Configuration(PPlugin plugin, Messages messages) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Set SystemMessage instance to be managed.
	 *
	 * @param systemMessage SystemMessage instance to manage
	 */
	public void setSystemMessage(SystemMessage systemMessage) {
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
