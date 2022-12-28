package fr.prodrivers.minecraft.commons.storage;

import fr.prodrivers.minecraft.commons.Log;
import fr.prodrivers.minecraft.commons.plugin.DependenciesClassLoaderProvider;
import io.ebean.Database;
import io.ebean.DatabaseFactory;
import io.ebean.config.DatabaseConfig;
import io.ebean.config.PlatformConfig;

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
		dbConfig.setDbUuid(PlatformConfig.DbUuid.AUTO_BINARY);
		dbConfig.loadFromProperties(ebeanProperties);
		// Force no default server
		dbConfig.setDefaultServer(false);

		return DatabaseFactory.createWithContextClassLoader(dbConfig, dependenciesClassLoaderProvider.get());
	}
}
