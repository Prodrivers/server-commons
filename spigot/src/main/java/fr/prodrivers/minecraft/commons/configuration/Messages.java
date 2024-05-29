package fr.prodrivers.minecraft.commons.configuration;

import fr.prodrivers.minecraft.commons.annotations.ExcludedFromConfiguration;
import fr.prodrivers.minecraft.commons.chat.Chat;
import fr.prodrivers.minecraft.commons.configuration.file.FileAttributeConfiguration;
import fr.prodrivers.minecraft.commons.configuration.file.actions.MessageFileAttributeConfigurationAction;
import fr.prodrivers.minecraft.commons.configuration.file.actions.MessageListFileAttributeConfigurationAction;
import fr.prodrivers.minecraft.commons.configuration.file.actions.MessageMapFileAttributeConfigurationAction;
import org.bukkit.plugin.Plugin;

import javax.inject.Inject;
import java.io.File;

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
@SuppressWarnings("CanBeFinal")
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
	@Inject
	public Messages(Plugin plugin) {
		super(new File(plugin.getDataFolder(), "messages.yml"));
	}


	@Override
	public void init() {
		registerAction(new MessageFileAttributeConfigurationAction(this.configuration));
		registerAction(new MessageListFileAttributeConfigurationAction(this.configuration));
		registerAction(new MessageMapFileAttributeConfigurationAction(this.configuration));
		super.init();
	}
}
