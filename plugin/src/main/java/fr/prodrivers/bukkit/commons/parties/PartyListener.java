package fr.prodrivers.bukkit.commons.parties;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

public class PartyListener implements Listener {
	private final PartyManager partyManager;

	PartyListener(Plugin plugin, PartyManager partyManager) {
		this.partyManager = partyManager;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		this.partyManager.removePartyInvites(event.getPlayer().getUniqueId());

		Party party = this.partyManager.getParty(event.getPlayer().getUniqueId());

		if(party != null) {
			party.removePlayer(event.getPlayer().getUniqueId());
		}
	}
}
