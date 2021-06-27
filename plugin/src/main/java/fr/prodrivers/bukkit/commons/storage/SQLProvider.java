package fr.prodrivers.bukkit.commons.storage;

import fr.prodrivers.bukkit.commons.Log;
import fr.prodrivers.bukkit.commons.plugin.EConfiguration;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * SQL Database provider for Prodrivers plugins
 * <p>
 * While #StorageProvider offers a solid way to efficiently store persistent data across servers, some plugins may need the power of a more traditional, relational database.
 * SQLProvider fills the need by offering access to a JDBC-compliant database instance.
 * While SQLProvider exists, it is strongly encouraged to use #StorageProvider if possible.
 * <p>
 * SQLProvider is an optional part of the plugin, meaning that the result of its methods, on top of the underlying SQL database internal quirks and wrong queries, is not guaranteed to be correct.
 * One must check against null each results returned by SQLProvider.
 */
@Singleton
public class SQLProvider implements Provider<Connection>, Closeable {
	private Connection connection;

	@Inject
	public SQLProvider(EConfiguration configuration) {
		try {
			if(configuration.storage_sql_username != null && !configuration.storage_sql_username.isEmpty() && configuration.storage_sql_password != null && !configuration.storage_sql_password.isEmpty()) {
				connection = DriverManager.getConnection(configuration.storage_sql_uri, configuration.storage_sql_username, configuration.storage_sql_password);
			} else {
				connection = DriverManager.getConnection(configuration.storage_sql_uri);
			}
		} catch(SQLException ex) {
			connection = null;
			Log.warning("SQL provider is not available: " + ex.getLocalizedMessage());
			Log.warning("Please check your SQL connection URI and credentials.");
		}
	}

	/**
	 * Gets the database connection.
	 *
	 * @return Connection or null
	 */
	@Override
	public Connection get() {
		return connection;
	}

	public void close() throws IOException {
		try {
			if(connection != null) {
				connection.close();
			}
		} catch(Exception ex) {
			throw new IOException(ex);
		}
	}
}
