package fr.prodrivers.bukkit.commons.di.guice;

import com.google.inject.AbstractModule;
import fr.prodrivers.bukkit.commons.parties.PartyManager;
import fr.prodrivers.bukkit.commons.sections.SectionManager;
import fr.prodrivers.bukkit.commons.storage.DataSourceConfigProvider;
import fr.prodrivers.bukkit.commons.storage.EbeanProvider;
import fr.prodrivers.bukkit.commons.storage.SQLProvider;
import io.ebean.Database;
import io.ebean.datasource.DataSourceConfig;

import javax.inject.Inject;
import java.sql.Connection;

public class ProdriversCommonsGuiceModule extends AbstractModule {
	@Inject
	private PartyManager partyManager;
	@Inject
	private SectionManager sectionManager;

	@Inject
	private SQLProvider sqlProvider;
	@Inject
	private DataSourceConfigProvider dataSourceConfigProvider;

	@Override
	protected void configure() {
		bind(PartyManager.class).toInstance(partyManager);
		bind(SectionManager.class).toInstance(sectionManager);
		bind(Connection.class).toProvider(sqlProvider);
		bind(Database.class).toProvider(EbeanProvider.class);
		bind(DataSourceConfig.class).toProvider(dataSourceConfigProvider);
	}
}
