package fr.prodrivers.bukkit.commons.parties;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class PartyListener implements Listener {
	PartyListener( JavaPlugin plugin ) {
		plugin.getServer().getPluginManager().registerEvents( this, plugin );
	}

	@EventHandler
	public void onPlayerQuit( PlayerQuitEvent event ) {
		PartyManager.removePartyInvites( event.getPlayer().getUniqueId() );

		Party party = PartyManager.getParty( event.getPlayer().getUniqueId() );

		if( party != null ) {
			party.removePlayer( event.getPlayer().getUniqueId() );
		}
	}
}
