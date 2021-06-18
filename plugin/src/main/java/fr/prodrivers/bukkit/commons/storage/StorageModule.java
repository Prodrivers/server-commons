package fr.prodrivers.bukkit.commons.storage;

import com.google.inject.AbstractModule;
import io.ebean.datasource.DataSourceConfig;

import java.sql.Connection;

public class StorageModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(Connection.class).toProvider(SQLProvider.class).asEagerSingleton();
		bind(DataSourceConfig.class).toProvider(DataSourceConfigProvider.class).asEagerSingleton();
	}
}
