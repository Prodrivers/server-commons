package fr.prodrivers.bukkit.commons.storage;

import fr.prodrivers.bukkit.commons.di.guice.ProdriversCommonsGuiceModule;
import io.ebean.datasource.DataSourceConfig;

import javax.inject.Provider;
import javax.inject.Singleton;

/**
 * Database source configuration for Ebean ORM usage in Prodrivers plugins. Requires dependency injection.
 * <p>
 * Intended to be used with a dependency injector. It is already made available in
 * {@link ProdriversCommonsGuiceModule ProdriversCommons' public module}, no further action is
 * needed apart from adding the module to your own dependency injector instance.
 * </p>
 */
@Singleton
public class DataSourceConfigProvider implements Provider<DataSourceConfig> {
	/**
	 * Gets a new Ebean Database source configuration instance.
	 *
	 * @return Database source configuration or null
	 */
	@Override
	public DataSourceConfig get() {
		throw new UnsupportedOperationException();
	}
}
