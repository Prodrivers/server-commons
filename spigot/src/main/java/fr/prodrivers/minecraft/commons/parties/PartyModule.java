package fr.prodrivers.minecraft.commons.parties;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import fr.prodrivers.minecraft.spigot.commons.plugin.EConfiguration;

import javax.inject.Inject;

public class PartyModule extends AbstractModule {
	private final Class<? extends PartyManager> partyManagerClass;

	@Inject
	@SuppressWarnings("unchecked")
	public PartyModule(EConfiguration configuration) {
		try {
			this.partyManagerClass = (Class<? extends PartyManager>) Class.forName(configuration.providers_PartyManager);
		} catch(ClassNotFoundException | ClassCastException e) {
			throw new RuntimeException("Invalid party manager provider: " + configuration.providers_PartyManager, e);
		}
	}

	@Override
	protected void configure() {
		bind(PartyManager.class).to(partyManagerClass);
		if(DefaultPartyManager.class.equals(partyManagerClass)) {
			install(new FactoryModuleBuilder()
					.implement(Party.class, DefaultParty.class)
					.build(DefaultPartyFactory.class));
		} else {
			bind(PartyManager.class).to(partyManagerClass);
		}
	}
}
