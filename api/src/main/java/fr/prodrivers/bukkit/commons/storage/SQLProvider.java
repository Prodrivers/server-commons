package fr.prodrivers.bukkit.commons.storage;

import fr.prodrivers.bukkit.commons.di.guice.ProdriversCommonsGuiceModule;

import javax.inject.Provider;
import java.sql.Connection;

/**
 * SQL Database provider for Prodrivers plugins. Requires dependency injection.
 * <p>
 * SQLProvider offers access to a JDBC-compliant database instance.
 * While SQLProvider exists, it is strongly encouraged to use {@link EbeanProvider Ebean} if possible, as
 * {@link EbeanProvider Ebean} pools connection efficiently.
 * </p>
 * SQLProvider is intended to be used by a dependency injector, as it uses ProdriversCommons' internal configuration to
 * spin up connections. SQLProvider is already made available in
 * {@link ProdriversCommonsGuiceModule ProdriversCommons' public module}: you just have to
 * inject {@link java.sql.Connection java.sql.Connection} classes wherever you need it.
 * <p>
 * SQLProvider is an optional part of the plugin, meaning that the result of its methods, on top of the underlying SQL
 * database internal quirks and wrong queries, is not guaranteed to be correct.c One must check against null each
 * results returned by SQLProvider.
 * </p>
 */
public class SQLProvider implements Provider<Connection> {
	/**
	 * Gets the database connection.
	 *
	 * @return Connection or null
	 */
	public Connection get() {
		throw new UnsupportedOperationException();
	}
}
