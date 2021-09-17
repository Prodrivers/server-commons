package fr.prodrivers.bukkit.commons.hubs;

import fr.prodrivers.bukkit.commons.plugin.EConfiguration;
import fr.prodrivers.bukkit.commons.sections.Section;
import fr.prodrivers.bukkit.commons.sections.SectionCapabilities;
import fr.prodrivers.bukkit.commons.sections.SectionManager;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashSet;
import java.util.Set;

@Singleton
public class DefaultMainHub extends MainHub {
	private final EConfiguration configuration;

	@Inject
	DefaultMainHub(EConfiguration configuration) {
		super();
		this.configuration = configuration;
	}

	public boolean preJoin(@NonNull Player player, Section targetSection, boolean fromParty) {
		return this.configuration.sections_mainHub != null;
	}

	public boolean join(@NonNull Player player) {
		player.teleport(this.configuration.sections_mainHub);
		player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10, 5);
		return true;
	}

	public boolean preLeave(@NonNull OfflinePlayer player, Section targetSection, boolean fromParty) {
		return true;
	}

	public boolean leave(@NonNull OfflinePlayer player) {
		return true;
	}
}
