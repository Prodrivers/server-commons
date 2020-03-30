package fr.prodrivers.bukkit.commons.sections;

import fr.prodrivers.bukkit.commons.exceptions.IllegalSectionLeavingException;
import fr.prodrivers.bukkit.commons.exceptions.NoCurrentSectionException;
import fr.prodrivers.bukkit.commons.plugin.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class SectionListener implements Listener {
	SectionListener( JavaPlugin plugin ) {
		plugin.getServer().getPluginManager().registerEvents( this, plugin );
	}

	@EventHandler
	public void onPlayerJoin( PlayerJoinEvent e ) {
		final Player player = e.getPlayer();

		SectionManager.enter( player, MainHub.name );
	}

	@EventHandler
	public void onPlayerQuit( PlayerQuitEvent e ) {
		final Player player = e.getPlayer();

		try {
			SectionManager.leave( player );
		} catch( NoCurrentSectionException | IllegalSectionLeavingException ex ) {
			Main.logger.log( Level.SEVERE, "Exception occurred when making the player leave as he disconnects. Player may be left in an undefined state.", ex );
		}
	}
}
