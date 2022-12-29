package fr.prodrivers.minecraft.commons.configuration;

import fr.prodrivers.minecraft.commons.annotations.ExcludedFromConfiguration;
import fr.prodrivers.minecraft.commons.chat.SystemMessage;
import fr.prodrivers.minecraft.commons.configuration.file.AbstractFileAttributeConfiguration;
import org.bukkit.plugin.Plugin;

import javax.inject.Inject;
import java.util.logging.Level;

@SuppressWarnings("CanBeFinal")
public class Configuration extends AbstractFileAttributeConfiguration {
	@ExcludedFromConfiguration
	private final Plugin plugin;
	@ExcludedFromConfiguration
	private SystemMessage systemMessage;
	@ExcludedFromConfiguration
	private final Messages messages;

	public Level logLevel = Level.INFO;

	@Inject
	public Configuration(Plugin plugin, Messages messages) {
		super();
		this.plugin = plugin;
		this.messages = messages;
		this.configuration = this.plugin.getConfig();
	}

	public void setSystemMessage(SystemMessage systemMessage) {
		this.systemMessage = systemMessage;
		if(this.messages != null && this.systemMessage != null) {
			this.systemMessage.load(this.messages);
		}
	}

	@Override
	protected void init() {
		super.init();
		if(this.messages != null) {
			this.messages.init();
			if(this.systemMessage != null) {
				this.systemMessage.load(this.messages);
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
			if(systemMessage != null) {
				this.systemMessage.load(this.messages);
			}
		}
	}
}
