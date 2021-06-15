package fr.prodrivers.bukkit.commons.plugin;

import com.google.inject.AbstractModule;
import fr.prodrivers.bukkit.commons.Chat;
import fr.prodrivers.bukkit.commons.configuration.Configuration;
import fr.prodrivers.bukkit.commons.configuration.Messages;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class PluginModule extends AbstractModule {
	private final JavaPlugin plugin;

	public PluginModule(JavaPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	protected void configure() {
		bind(Plugin.class).toInstance(this.plugin);
		bind(JavaPlugin.class).toInstance(this.plugin);
		bind(Chat.class).toInstance(new Chat(this.plugin.getDescription().getName()));
		bind(Configuration.class).to(EConfiguration.class);
		bind(Messages.class).to(EMessages.class);
	}
}