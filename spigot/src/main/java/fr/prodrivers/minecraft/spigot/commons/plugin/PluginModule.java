package fr.prodrivers.minecraft.spigot.commons.plugin;

import com.google.inject.AbstractModule;
import fr.prodrivers.minecraft.commons.configuration.Configuration;
import fr.prodrivers.minecraft.commons.configuration.Messages;
import fr.prodrivers.minecraft.commons.players.PPlayerAdapter;
import fr.prodrivers.minecraft.commons.players.PPlayerAdapterProvider;
import net.kyori.adventure.text.minimessage.MiniMessage;
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
		bind(MiniMessage.class).toInstance(MiniMessage.miniMessage());
		bind(PPlayerAdapter.class).toProvider(PPlayerAdapterProvider.class);
	}
}