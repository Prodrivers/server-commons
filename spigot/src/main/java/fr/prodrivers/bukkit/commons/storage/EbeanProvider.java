package fr.prodrivers.bukkit.commons.storage;

import fr.prodrivers.bukkit.commons.plugin.DependenciesClassLoaderProvider;
import io.ebean.Database;
import io.ebean.DatabaseFactory;
import io.ebean.config.DatabaseConfig;
import io.ebean.config.PlatformConfig;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.util.Properties;
import java.util.logging.Logger;

@Singleton
public class EbeanProvider implements Provider<Database> {
	private final Logger logger;
	private final DatabaseConfig dbConfig;
	private final Properties ebeanProperties;
	private final DependenciesClassLoaderProvider dependenciesClassLoaderProvider;

	@Inject
	public EbeanProvider(Logger logger, @Named("ebean") Properties ebeanProperties, DatabaseConfig dbConfig, DependenciesClassLoaderProvider dependenciesClassLoaderProvider) {
		this.logger = logger;

		this.logger.finest("DbConfig instance class: " + dbConfig.getClass().getCanonicalName());
		this.logger.finest("DbConfig has classes: " + dbConfig.getClasses());
		this.logger.finest("EbeanPropertiesProvider instance class: " + ebeanProperties.getClass().getCanonicalName());

		this.dbConfig = dbConfig;
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
