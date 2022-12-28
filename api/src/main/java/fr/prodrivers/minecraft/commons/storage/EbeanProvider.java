package fr.prodrivers.minecraft.commons.storage;

import fr.prodrivers.minecraft.commons.di.guice.ProdriversCommonsGuiceModule;
import io.ebean.Database;
import io.ebean.Model;
import io.ebean.Transaction;

import javax.inject.Provider;
import javax.inject.Singleton;
import java.util.List;

/**
 * Ebean ORM provider for Prodrivers plugins. Requires dependency injection.
 * <p>
 * Ebean is an ORM that offers JPA-compliant facilities to interact with relational databases.
 * Previously included directly in Spigot, Prodrivers Commons now implements it following its removal from Spigot.
 * <p>
 * EbeanProvider is intended to be used with a dependency injector. It uses two providers to build a new database
 * instance, for DataSourceConfig and DatabaseConfig. A provider for DataSourceConfig is already made available in
 * {@link ProdriversCommonsGuiceModule Prodrivers Commons' public module}, while plugins should
 * offer their own provider for DatabaseConfig. Your DatabaseConfig provider should, at least, register all classes
 * (entities, ...) that you intend to use with Ebean, using for example the
 * {@link io.ebean.config.DatabaseConfig#setClasses(List) setClasses} method.
 * <p>
 * Important: <b>do not use</b> {@link Model#save()} or {@link Model#save(Transaction)} as it will use the first
 * registered database instead of the one you fetched the bean from. This will cause issues when having multiple plugins
 * using Ebean.
 * <p>
 * EbeanProvider is an optional part of the plugin, meaning that the result of its methods, on top of the underlying SQL
 * database internal quirks and wrong queries, is not guaranteed to be correct.  One must check for errors when
 * retrieving the database.
 */
@Singleton
public class EbeanProvider implements Provider<Database> {
	/**
	 * Make class non-instantiable
	 */
	private EbeanProvider() {
	}

	/**
	 * Gets a new Ebean Database instance.
	 *
	 * @return Database or null
	 */
	@Override
	public Database get() {
		throw new UnsupportedOperationException();
	}
}
