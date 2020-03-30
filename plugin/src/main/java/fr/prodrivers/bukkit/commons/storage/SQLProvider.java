package fr.prodrivers.bukkit.commons.storage;

import fr.prodrivers.bukkit.commons.plugin.Main;
import io.ebean.EbeanServer;
import io.ebean.EbeanServerFactory;
import io.ebean.config.ServerConfig;
import io.ebean.datasource.DataSourceConfig;
import java.util.concurrent.ThreadLocalRandom;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * SQL Database provider for Prodrivers plugins
 *
 * While #StorageProvider offers a solid way to efficiently store persistent data across servers, some plugins may need the power of a more traditional, relational database.
 * SQLProvider fills the need by offering access to a JDBC-compliant database instance.
 * While SQLProvider exists, it is strongly encouraged to use #StorageProvider if possible.
 *
 * SQLProvider is an optional part of the plugin, meaning that the result of its methods, on top of the underlying SQL database internal quirks and wrong queries, is not guaranteed to be correct.
 * One must check against null each results returned by SQLProvider.
 */
public class SQLProvider {
	private static Connection connection;
	private final static String EBEAN_SERVER_NAME_PREFIX = "ProdriversCommonsEbeanServer_";

	public static void init() {
		try {
			if( Main.getConfiguration().storage_sql_username != null && !Main.getConfiguration().storage_sql_username.isEmpty() && Main.getConfiguration().storage_sql_password != null && !Main.getConfiguration().storage_sql_password.isEmpty() ) {
				connection = DriverManager.getConnection( Main.getConfiguration().storage_sql_uri, Main.getConfiguration().storage_sql_username, Main.getConfiguration().storage_sql_password );
			} else {
				connection = DriverManager.getConnection( Main.getConfiguration().storage_sql_uri );
			}
		} catch( SQLException ex ) {
			connection = null;
			Main.logger.warning( "[ProdriversCommons] SQL provider is not available: " + ex.getLocalizedMessage() );
			Main.logger.warning( "[ProdriversCommons] Please check your SQL connection URI and credentials." );
		}
	}

	/**
	 * Gets the database connection.
	 * @return Connection or null
	 */
	public static Connection getConnection() {
		return connection;
	}

	public static EbeanServer getEbeanServer( List<Class<?>> classes ) {
		if( connection == null )
			return null;
		try {
			return initEbeanServer( classes );
		} catch( RuntimeException | SQLException ex ) {
			Main.logger.severe( "[ProdriversCommons] Error while creating EBean server: " + ex.getLocalizedMessage() );
			ex.printStackTrace();
		}
		return null;
	}

	private static EbeanServer initEbeanServer( List<Class<?>> classes ) throws SQLException {
		DataSourceConfig dbSrcCfg = new DataSourceConfig();
		dbSrcCfg.setDriver( DriverManager.getDriver(connection.getMetaData().getURL()).getClass().getName() );
		dbSrcCfg.setUsername( Main.getConfiguration().storage_sql_username );
		dbSrcCfg.setPassword( Main.getConfiguration().storage_sql_password );
		dbSrcCfg.setUrl( connection.getMetaData().getURL() );

		ServerConfig sc = new ServerConfig();
		sc.setName( generateName() );
		sc.setDataSourceConfig( dbSrcCfg );
		sc.setClasses( classes );
		sc.setRegister( false );

		return EbeanServerFactory.create( sc );
	}

	private static String generateName() {
		return EBEAN_SERVER_NAME_PREFIX + String.valueOf( ThreadLocalRandom.current().nextInt() );
	}

	public static void close() throws IOException {
		try {
			if( connection != null )
				connection.close();
		} catch( Exception ex ) {
			throw new IOException( ex );
		}
	}
}
