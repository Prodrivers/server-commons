package fr.prodrivers.minecraft.commons.plugin.commands;

import co.aikar.commands.BukkitCommandManager;
import com.google.inject.AbstractModule;
import fr.prodrivers.minecraft.commons.commands.ACFCommandManagerProvider;

public class CommandsModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(BukkitCommandManager.class).toProvider(ACFCommandManagerProvider.class);
	}
}
