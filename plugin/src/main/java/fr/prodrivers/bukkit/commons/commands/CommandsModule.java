package fr.prodrivers.bukkit.commons.commands;

import com.google.inject.AbstractModule;

public class CommandsModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(MainPluginCommands.class).asEagerSingleton();
		bind(HubCommands.class).asEagerSingleton();
		bind(CommandsBlocker.class).asEagerSingleton();
		bind(PartyCommands.class).asEagerSingleton();
	}
}
