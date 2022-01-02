package fr.prodrivers.bukkit.commons.storage;

import fr.prodrivers.bukkit.commons.Log;
import fr.prodrivers.bukkit.commons.plugin.DependenciesClassLoaderProvider;
import io.ebean.Database;
import io.ebean.DatabaseFactory;
import io.ebean.config.DatabaseConfig;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.util.Properties;

@Singleton
public class EbeanProvider implements Provider<Database> {
	private final DatabaseConfig dbConfig;
	private final Properties ebeanProperties;
	private final DependenciesClassLoaderProvider dependenciesClassLoaderProvider;

	@Inject
	public EbeanProvider(@Named("ebean") Properties ebeanProperties, DatabaseConfig dbConfig, DependenciesClassLoaderProvider dependenciesClassLoaderProvider) {
		Log.info("DbConfig is: " + dbConfig.getClass().getCanonicalName());
		Log.info("DbConfig has classes: " + dbConfig.getClasses());
		this.dbConfig = dbConfig;
		Log.info("EbeanPropertiesProvider is: " + ebeanProperties.getClass().getCanonicalName());
		this.ebeanProperties = ebeanProperties;
		this.dependenciesClassLoaderProvider = dependenciesClassLoaderProvider;
	}

	@Override
	public Database get() {
		dbConfig.loadFromProperties(ebeanProperties);

		return DatabaseFactory.createWithContextClassLoader(dbConfig, dependenciesClassLoaderProvider.get());
	}
}
