package fr.prodrivers.bukkit.commons.sections;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class SectionListener implements Listener {
	SectionListener( JavaPlugin plugin ) {
		plugin.getServer().getPluginManager().registerEvents( this, plugin );
	}

	@EventHandler
	public void onPlayerJoin( PlayerJoinEvent e ) {
		final Player player = e.getPlayer();

		SectionManager.enter(player, SectionManager.ROOT_NODE_NAME);
	}

	@EventHandler
	public void onPlayerQuit( PlayerQuitEvent e ) {
		final OfflinePlayer player = e.getPlayer();

		SectionManager.unregister(player);
	}
}
