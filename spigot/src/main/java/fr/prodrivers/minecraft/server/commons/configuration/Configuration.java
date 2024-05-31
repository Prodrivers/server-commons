package fr.prodrivers.minecraft.server.commons.configuration;

import fr.prodrivers.minecraft.server.commons.annotations.ExcludedFromConfiguration;
import fr.prodrivers.minecraft.server.commons.chat.Chat;
import fr.prodrivers.minecraft.server.commons.configuration.file.AbstractFileAttributeConfiguration;
import org.bukkit.plugin.Plugin;

import javax.inject.Inject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Configuration helper for Prodrivers plugins.
 * <p>
 * It represents configuration options using class fields, by saving and loading fields from and inside the plugin's
 * configuration file, using an underlying {@link AbstractAttributeConfiguration}.
 * It supports all data types supported by Bukkit's {@link org.bukkit.configuration.file.FileConfiguration}.
 * <p>
 * {@link ExcludedFromConfiguration} annotation allows specific fields not to
 * be used by the field processor.
 * As with every {@link AbstractAttributeConfiguration} derivative, {@link #init()} have to be called immediately after
 * constructing the object, either at the end of the constructor or outside of it.
 */
@SuppressWarnings("CanBeFinal")
@Deprecated
public class Configuration extends AbstractFileAttributeConfiguration {
	@ExcludedFromConfiguration
	private final Plugin plugin;
	@ExcludedFromConfiguration
	private Chat chat;
	@ExcludedFromConfiguration
	private final Messages messages;

	public Level logLevel = Level.INFO;

	/**
	 * Configuration helper constructor.
	 * Intended to be used with a dependency injector.
	 *
	 * @param logger   Logger to use
	 * @param plugin   Plugin initializing the helper
	 * @param messages Messages instance to manage, uses this to provide your own inheriting class that adds its own message fields
	 */
	@Inject
	public Configuration(Logger logger, Plugin plugin, Messages messages) {
		super(logger);
		this.plugin = plugin;
		this.messages = messages;
		this.configuration = this.plugin.getConfig();
	}

	/**
	 * Set chat instance to be managed.
	 *
	 * @param chat Chat instance to manage
	 */
	public void setChat(Chat chat) {
		this.chat = chat;
		if(this.messages != null && this.chat != null) {
			this.chat.load(this.messages);
		}
	}

	@Override
	protected void init() {
		super.init();
		if(this.messages != null) {
			this.messages.init();
			if(this.chat != null) {
				this.chat.load(this.messages);
			}
		}
	}

	@Override
	public void save() {
		super.save();

		this.plugin.saveConfig();
		if(this.messages != null) {
			this.messages.save();
		}
	}

	@Override
	protected void saveDefaults() {
		super.saveDefaults();

		this.plugin.saveConfig();
	}

	@Override
	public void reload() {
		this.plugin.reloadConfig();
		super.reload();
		if(this.messages != null) {
			this.messages.reload();
			if(chat != null) {
				this.chat.load(this.messages);
			}
		}
	}
}
