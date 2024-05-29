package fr.prodrivers.minecraft.server.commons.sections;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class SectionListener implements Listener {
	private final SectionManager sectionManager;

	SectionListener(JavaPlugin plugin, SectionManager sectionManager) {
		this.sectionManager = sectionManager;

		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerJoin(PlayerJoinEvent e) {
		final Player player = e.getPlayer();

		sectionManager.register(player);
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		final OfflinePlayer player = e.getPlayer();

		sectionManager.unregister(player);
	}
}
