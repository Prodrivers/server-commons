package fr.prodrivers.bukkit.commons.configuration;

import fr.prodrivers.bukkit.commons.configuration.file.FileAttributeConfiguration;
import fr.prodrivers.bukkit.commons.configuration.file.actions.MessageFileAttributeConfigurationAction;
import fr.prodrivers.bukkit.commons.configuration.file.actions.MessageListFileAttributeConfigurationAction;
import fr.prodrivers.bukkit.commons.configuration.file.actions.MessageMapFileAttributeConfigurationAction;
import org.bukkit.plugin.Plugin;

import javax.inject.Inject;
import java.io.File;

@SuppressWarnings("CanBeFinal")
public class Messages extends FileAttributeConfiguration {
	public String prefix = "[<name>]";

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
