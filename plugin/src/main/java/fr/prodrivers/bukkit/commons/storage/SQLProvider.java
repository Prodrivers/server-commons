package fr.prodrivers.bukkit.commons.storage;

import fr.prodrivers.bukkit.commons.Log;
import fr.prodrivers.bukkit.commons.plugin.EConfiguration;
import io.ebean.EbeanServer;
import io.ebean.EbeanServerFactory;
import io.ebean.config.ServerConfig;
import io.ebean.datasource.DataSourceConfig;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

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
public class SQLProvider {
	private static Connection connection;
	private final static String EBEAN_SERVER_NAME_PREFIX = "ProdriversCommonsEbeanServer_";
	
	private final EConfiguration configuration;

	@Inject
	public SQLProvider(EConfiguration configuration) {
		this.configuration = configuration;
		try {
			if(this.configuration.storage_sql_username != null && !this.configuration.storage_sql_username.isEmpty() && this.configuration.storage_sql_password != null && !this.configuration.storage_sql_password.isEmpty()) {
				connection = DriverManager.getConnection(this.configuration.storage_sql_uri, this.configuration.storage_sql_username, this.configuration.storage_sql_password);
			} else {
				connection = DriverManager.getConnection(this.configuration.storage_sql_uri);
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
	public Connection getConnection() {
		return connection;
	}

	public EbeanServer getEbeanServer(List<Class<?>> classes) {
		if(connection == null)
			return null;
		try {
			return initEbeanServer(classes);
		} catch(RuntimeException | SQLException e) {
			Log.severe("Error while creating EBean server: " + e.getLocalizedMessage(), e);
		}
		return null;
	}

	private EbeanServer initEbeanServer(List<Class<?>> classes) throws SQLException {
		DataSourceConfig dbSrcCfg = new DataSourceConfig();
		dbSrcCfg.setDriver(DriverManager.getDriver(connection.getMetaData().getURL()).getClass().getName());
		dbSrcCfg.setUsername(this.configuration.storage_sql_username);
		dbSrcCfg.setPassword(this.configuration.storage_sql_password);
		dbSrcCfg.setUrl(connection.getMetaData().getURL());
		dbSrcCfg.addProperty("useSSL", false);

		ServerConfig sc = new ServerConfig();
		sc.setName(generateName());
		sc.setDataSourceConfig(dbSrcCfg);
		sc.setClasses(classes);
		sc.setRegister(false);

		return EbeanServerFactory.create(sc);
	}

	private static String generateName() {
		return EBEAN_SERVER_NAME_PREFIX + ThreadLocalRandom.current().nextInt();
	}

	public static void close() throws IOException {
		try {
			if(connection != null)
				connection.close();
		} catch(Exception ex) {
			throw new IOException(ex);
		}
	}
}
