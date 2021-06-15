package fr.prodrivers.bukkit.commons.parties;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

public class PartyModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(PartyManager.class);
		install(new FactoryModuleBuilder()
				.implement(Party.class, Party.class)
				.build(PartyFactory.class));
	}
}
