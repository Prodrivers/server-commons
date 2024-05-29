package fr.prodrivers.minecraft.server.commons.hubs;

import com.google.inject.AbstractModule;
import fr.prodrivers.minecraft.server.spigot.commons.plugin.EConfiguration;

import javax.inject.Inject;

public class MainHubModule extends AbstractModule {
	private final Class<? extends MainHub> mainHubClass;

	@Inject
	@SuppressWarnings("unchecked")
	public MainHubModule(EConfiguration configuration) {
		try {
			if(!"null".equals(configuration.providers_MainHub)) {
				this.mainHubClass = (Class<? extends MainHub>) Class.forName(configuration.providers_MainHub);
			} else {
				this.mainHubClass = null;
			}
		} catch(ClassNotFoundException | ClassCastException e) {
			throw new RuntimeException("Invalid main hub provider: " + configuration.providers_MainHub, e);
		}
	}

	@Override
	protected void configure() {
		if(mainHubClass != null) {
			bind(MainHub.class).to(mainHubClass);
		} else {
			bind(MainHub.class).toInstance(null);
		}
	}
}
