package fr.prodrivers.bukkit.commons.storage;

import com.google.inject.AbstractModule;

public class StorageModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(SQLProvider.class).asEagerSingleton();
	}
}
