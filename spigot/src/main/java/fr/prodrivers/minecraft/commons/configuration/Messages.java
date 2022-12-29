package fr.prodrivers.minecraft.commons.configuration;

import fr.prodrivers.minecraft.commons.annotations.ExcludedFromConfiguration;
import fr.prodrivers.minecraft.commons.configuration.file.FileAttributeConfiguration;
import fr.prodrivers.minecraft.commons.configuration.file.actions.ComponentFileAttributeConfigurationAction;
import fr.prodrivers.minecraft.commons.configuration.file.actions.MessageFileAttributeConfigurationAction;
import fr.prodrivers.minecraft.commons.configuration.file.actions.MessageListFileAttributeConfigurationAction;
import fr.prodrivers.minecraft.commons.configuration.file.actions.MessageMapFileAttributeConfigurationAction;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.plugin.Plugin;

import javax.inject.Inject;
import java.io.File;

@SuppressWarnings("CanBeFinal")
public class Messages extends FileAttributeConfiguration {
	@ExcludedFromConfiguration
	protected final MiniMessage miniMessage = MiniMessage.miniMessage();

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
		registerAction(new ComponentFileAttributeConfigurationAction(this.configuration));
		super.init();
	}

	public MiniMessage miniMessage() {
		return miniMessage;
	}

	public Component deserialize(String message) {
		return miniMessage.deserialize(message);
	}
}
