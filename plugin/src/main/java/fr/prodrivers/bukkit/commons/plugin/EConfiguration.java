package fr.prodrivers.bukkit.commons.plugin;

import fr.prodrivers.bukkit.commons.Chat;
import fr.prodrivers.bukkit.commons.configuration.Configuration;
import fr.prodrivers.bukkit.commons.configuration.Messages;
import fr.prodrivers.bukkit.commons.hubs.DefaultMainHub;
import fr.prodrivers.bukkit.commons.parties.DefaultPartyManager;
import fr.prodrivers.bukkit.commons.sections.DefaultSectionManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.logging.Level;

@SuppressWarnings("CanBeFinal")
@Singleton
public class EConfiguration extends Configuration {
	public Level logLevel = Level.INFO;
	public Location sections_mainHub = new Location(Bukkit.getWorld("world"), 0, 70, 0);
	public String storage_sql_uri = "jdbc:mysql://localhost:3306/db";
	public String storage_sql_username = "";
	public String storage_sql_password = "";
	public boolean storage_sql_useSSL = true;
	public Integer storage_sql_minimumConnections = 2;
	public Integer storage_sql_maximumConnections = 200;
	public Integer storage_sql_maxInactiveTimeSecs = 300;
	public long sectionTree_buildDelayTicks = 40L;
	public String providers_SectionManager = DefaultSectionManager.class.getCanonicalName();
	public String providers_PartyManager = DefaultPartyManager.class.getCanonicalName();
	public String providers_MainHub = DefaultMainHub.class.getCanonicalName();

	@Inject
	public EConfiguration(Plugin plugin, Chat chat, Messages messages) {
		super(plugin, chat, messages);
		init();
	}
}
