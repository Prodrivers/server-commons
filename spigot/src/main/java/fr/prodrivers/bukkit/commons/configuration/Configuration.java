package fr.prodrivers.bukkit.commons.configuration;

import fr.prodrivers.bukkit.commons.annotations.ExcludedFromConfiguration;
import fr.prodrivers.bukkit.commons.chat.Chat;
import fr.prodrivers.bukkit.commons.configuration.file.AbstractFileAttributeConfiguration;
import org.bukkit.plugin.Plugin;

import javax.inject.Inject;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("CanBeFinal")
public class Configuration extends AbstractFileAttributeConfiguration {
	@ExcludedFromConfiguration
	private final Plugin plugin;
	@ExcludedFromConfiguration
	private Chat chat;
	@ExcludedFromConfiguration
	private final Messages messages;

	public Level logLevel = Level.INFO;

	@Inject
	public Configuration(Logger logger, Plugin plugin, Messages messages) {
		super(logger);
		this.plugin = plugin;
		this.messages = messages;
		this.configuration = this.plugin.getConfig();
	}

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
