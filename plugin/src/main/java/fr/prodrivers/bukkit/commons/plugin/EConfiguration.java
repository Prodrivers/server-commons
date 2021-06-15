package fr.prodrivers.bukkit.commons.plugin;

import fr.prodrivers.bukkit.commons.Chat;
import fr.prodrivers.bukkit.commons.configuration.Configuration;
import fr.prodrivers.bukkit.commons.configuration.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

import javax.inject.Inject;
import javax.inject.Singleton;

@SuppressWarnings("CanBeFinal")
@Singleton
public class EConfiguration extends Configuration {
	public Location sections_mainHub = new Location(Bukkit.getWorld("world"), 0, 70, 0);
	public String storage_sql_uri = "jdbc:mysql://localhost:3306/db";
	public String storage_sql_username = "";
	public String storage_sql_password = "";
	public long sectionTree_buildDelayTicks = 40L;

	@Inject
	public EConfiguration(Plugin plugin, Chat chat, Messages messages) {
		super(plugin, chat, messages);
		init();
	}
}
