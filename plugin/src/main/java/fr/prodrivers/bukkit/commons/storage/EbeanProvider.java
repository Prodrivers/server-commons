package fr.prodrivers.bukkit.commons.storage;

import fr.prodrivers.bukkit.commons.Log;
import fr.prodrivers.bukkit.commons.plugin.DependenciesClassLoaderProvider;
import io.ebean.Database;
import io.ebean.DatabaseFactory;
import io.ebean.config.DatabaseConfig;
import io.ebean.datasource.DataSourceConfig;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.util.concurrent.ThreadLocalRandom;

@Singleton
public class EbeanProvider implements Provider<Database> {
	private final static String EBEAN_SERVER_NAME_PREFIX = "ProdriversCommonsEbeanServer_";

	private final DatabaseConfig dbConfig;
	private final DataSourceConfig dataSourceConfig;
	private final DependenciesClassLoaderProvider dependenciesClassLoaderProvider;

	@Inject
	public EbeanProvider(DataSourceConfig dataSourceConfig, DatabaseConfig dbConfig, DependenciesClassLoaderProvider dependenciesClassLoaderProvider) {
		Log.info("DbConfig is: " + dbConfig.getClass().getCanonicalName());
		Log.info("DbConfig has classes: " + dbConfig.getClasses());
		this.dbConfig = dbConfig;
		Log.info("DataSourceConfig is: " + dataSourceConfig.getClass().getCanonicalName());
		this.dataSourceConfig = dataSourceConfig;
		this.dependenciesClassLoaderProvider = dependenciesClassLoaderProvider;
	}

	@Override
	public Database get() {
		dbConfig.setName(generateName());
		dbConfig.setDataSourceConfig(dataSourceConfig);
		dbConfig.setRegister(false);

		return DatabaseFactory.createWithContextClassLoader(dbConfig, dependenciesClassLoaderProvider.get());
	}

	private static String generateName() {
		return EBEAN_SERVER_NAME_PREFIX + ThreadLocalRandom.current().nextInt();
	}
}
