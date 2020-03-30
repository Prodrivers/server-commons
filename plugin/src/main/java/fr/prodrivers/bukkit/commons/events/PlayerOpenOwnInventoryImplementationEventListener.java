package fr.prodrivers.bukkit.commons.events;

import org.bukkit.Achievement;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

public final class PlayerOpenOwnInventoryImplementationEventListener implements Listener {
	public static void init( Plugin plugin ) {
		//plugin.getServer().getPluginManager().registerEvents( new PlayerOpenOwnInventoryImplementationEventListener(), plugin );
	}

	/*@EventHandler
	public void onJoin( PlayerJoinEvent event ) {
		event.getPlayer().removeAchievement( Achievement.OPEN_INVENTORY );
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onInventoryOpenEvent( PlayerAchievementAwardedEvent event ) {
		if( event.getAchievement().equals( Achievement.OPEN_INVENTORY ) ) {
			event.setCancelled( true );
			Bukkit.getServer().getPluginManager().callEvent( new PlayerOpenOwnInventoryEvent( event.getPlayer() ) );
		}
	}*/
}
