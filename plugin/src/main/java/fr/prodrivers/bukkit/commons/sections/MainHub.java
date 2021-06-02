package fr.prodrivers.bukkit.commons.sections;

import fr.prodrivers.bukkit.commons.plugin.Main;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashSet;
import java.util.Set;

public class MainHub extends Section {
	public static MainHub instance;
	private final static Set<SectionCapabilities> capabilities = new HashSet<>();

	static {
		capabilities.add(SectionCapabilities.HUB);
		capabilities.add(SectionCapabilities.PARTY_AWARE);
	}

	private Location loc;

	MainHub() {
		super(SectionManager.ROOT_NODE_NAME);
		reload();
		instance = this;
	}

	public @NonNull Set<SectionCapabilities> getCapabilities() {
		return capabilities;
	}

	public boolean preJoin(@NonNull Player player, boolean fromParty) {
		return loc != null;
	}

	public boolean join(@NonNull Player player) {
		player.teleport(loc);
		player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10, 5);
		return true;
	}

	public boolean preLeave(@NonNull OfflinePlayer player, boolean fromParty) {
		return true;
	}

	public boolean leave(@NonNull OfflinePlayer player) {
		return true;
	}

	void reload() {
		loc = Main.getConfiguration().sections_mainHub;
	}
}
