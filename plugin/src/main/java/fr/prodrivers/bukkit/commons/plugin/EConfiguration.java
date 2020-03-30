package fr.prodrivers.bukkit.commons.plugin;

import fr.prodrivers.bukkit.commons.Chat;
import fr.prodrivers.bukkit.commons.configuration.Configuration;
import fr.prodrivers.bukkit.commons.configuration.Messages;
import fr.prodrivers.bukkit.commons.commands.CommandsManager;
import fr.prodrivers.bukkit.commons.sections.SectionManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

public class EConfiguration extends Configuration {
	public Location sections_mainHub = new Location( Bukkit.getWorld( "world" ), 0, 70, 0 );
	public String cache_uri = "redis://localhost:6379";
	public String cache_password = "";
	public int cache_maxTotalConnections = 8;
	public int cache_maxIdleConnections = 4;
	public int cache_timeOut = 10000;
	public String storage_uri = "mongodb://localhost:27017";
	public String storage_playerCollection = "players";
	public String storage_sql_uri = "jdbc:mysql://localhost:3306/db";
	public String storage_sql_username = "";
	public String storage_sql_password = "";

	public EConfiguration( JavaPlugin plugin, Class<? extends Messages> messagesClass, Chat chat ) {
		super( plugin, messagesClass, chat );
	}

	@Override
	public void reload() {
		super.reload();
		SectionManager.reload();
		CommandsManager.reload();
	}
}
