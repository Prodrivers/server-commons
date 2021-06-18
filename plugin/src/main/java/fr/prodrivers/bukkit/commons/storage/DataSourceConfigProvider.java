package fr.prodrivers.bukkit.commons.storage;

import fr.prodrivers.bukkit.commons.plugin.EConfiguration;
import io.ebean.datasource.DataSourceConfig;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.sql.DriverManager;
import java.sql.SQLException;

@Singleton
public class DataSourceConfigProvider implements Provider<DataSourceConfig> {
	private final EConfiguration configuration;

	@Inject
	public DataSourceConfigProvider(EConfiguration configuration) {
		this.configuration = configuration;
	}

	@Override
	public DataSourceConfig get() {
		DataSourceConfig dbSrcCfg = new DataSourceConfig();
		try {
			dbSrcCfg.setDriver(DriverManager.getDriver(this.configuration.storage_sql_uri).getClass().getName());
		} catch(SQLException e) {
			throw new RuntimeException("Cannot get driver for provided SQL storage.", e);
		}
		dbSrcCfg.setUsername(this.configuration.storage_sql_username);
		dbSrcCfg.setPassword(this.configuration.storage_sql_password);
		dbSrcCfg.setUrl(this.configuration.storage_sql_uri);
		dbSrcCfg.addProperty("useSSL", false);

		return dbSrcCfg;
	}
}
