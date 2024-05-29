package fr.prodrivers.minecraft.server.spigot.commons.plugin;

import com.google.inject.AbstractModule;
import fr.prodrivers.minecraft.server.commons.configuration.Configuration;
import fr.prodrivers.minecraft.server.commons.configuration.Messages;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginModule extends AbstractModule {
	private final ClassLoader classLoader;
	private final Main plugin;

	public PluginModule(Main plugin, ClassLoader classLoader) {
		this.plugin = plugin;
		this.classLoader = classLoader;
	}

	@Override
	protected void configure() {
		bind(Plugin.class).toInstance(this.plugin);
		bind(JavaPlugin.class).toInstance(this.plugin);
		bind(Main.class).toInstance(this.plugin);
		bind(Configuration.class).to(EConfiguration.class);
		bind(Messages.class).to(EMessages.class);
		bind(DependenciesClassLoaderProvider.class).toInstance(new DependenciesClassLoaderProvider(this.classLoader));
	}
}