package fr.prodrivers.bukkit.commons.hubs;

import com.google.inject.AbstractModule;
import fr.prodrivers.bukkit.commons.plugin.EConfiguration;

import javax.inject.Inject;

public class MainHubModule extends AbstractModule {
	private final Class<? extends MainHub> mainHubClass;

	@Inject
	@SuppressWarnings("unchecked")
	public MainHubModule(EConfiguration configuration) {
		try {
			this.mainHubClass = (Class<? extends MainHub>) Class.forName(configuration.providers_MainHub);
		} catch(ClassNotFoundException|ClassCastException e) {
			throw new RuntimeException("Invalid main hub provider: " + configuration.providers_MainHub, e);
		}
	}

	@Override
	protected void configure() {
		bind(MainHub.class).to(mainHubClass);
	}
}
