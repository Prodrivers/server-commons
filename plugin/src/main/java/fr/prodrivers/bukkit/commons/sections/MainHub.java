package fr.prodrivers.bukkit.commons.sections;

import fr.prodrivers.bukkit.commons.plugin.EConfiguration;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashSet;
import java.util.Set;

@Singleton
public class MainHub extends Section {
	private final static Set<SectionCapabilities> capabilities = new HashSet<>();

	private final EConfiguration configuration;

	static {
		capabilities.add(SectionCapabilities.HUB);
		capabilities.add(SectionCapabilities.PARTY_AWARE);
	}

	@Inject
	MainHub(EConfiguration configuration) {
		super(SectionManager.ROOT_NODE_NAME);
		this.configuration = configuration;
	}

	public @NonNull Set<SectionCapabilities> getCapabilities() {
		return capabilities;
	}

	public boolean preJoin(@NonNull Player player, boolean fromParty) {
		return this.configuration.sections_mainHub != null;
	}

	public boolean join(@NonNull Player player) {
		player.teleport(this.configuration.sections_mainHub);
		player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10, 5);
		return true;
	}

	public boolean preLeave(@NonNull OfflinePlayer player, boolean fromParty) {
		return true;
	}

	public boolean leave(@NonNull OfflinePlayer player) {
		return true;
	}
}
