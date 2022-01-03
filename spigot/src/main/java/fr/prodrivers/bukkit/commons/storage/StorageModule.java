package fr.prodrivers.bukkit.commons.storage;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

import java.util.Properties;

public class StorageModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(Properties.class).annotatedWith(Names.named("ebean")).toProvider(EbeanPropertiesProvider.class).asEagerSingleton();
	}
}
