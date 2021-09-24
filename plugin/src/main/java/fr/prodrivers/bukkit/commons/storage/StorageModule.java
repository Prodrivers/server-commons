package fr.prodrivers.bukkit.commons.storage;

import com.google.inject.AbstractModule;
import io.ebean.datasource.DataSourceConfig;

public class StorageModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(DataSourceConfig.class).toProvider(DataSourceConfigProvider.class).asEagerSingleton();
	}
}
