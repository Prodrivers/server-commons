package fr.prodrivers.minecraft.server.commons.storage;

import fr.prodrivers.minecraft.server.spigot.commons.plugin.EConfiguration;

import jakarta.inject.Inject;
import jakarta.inject.Provider;
import jakarta.inject.Singleton;
import java.util.Properties;

@Singleton
public class EbeanPropertiesProvider implements Provider<Properties> {
	private final EConfiguration configuration;

	@Inject
	public EbeanPropertiesProvider(EConfiguration configuration) {
		this.configuration = configuration;
	}

	@Override
	public Properties get() {
		return this.configuration.storage_ebean;
	}
}
