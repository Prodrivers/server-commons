package fr.prodrivers.minecraft.server.commons.hubs;

import fr.prodrivers.minecraft.server.commons.sections.Section;
import fr.prodrivers.minecraft.server.commons.sections.SectionCapabilities;
import fr.prodrivers.minecraft.server.commons.sections.SectionManager;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashSet;
import java.util.Set;

/**
 * Base class for Prodrivers Commons-managed main hub.
 * <p>
 * Implement this class if you want to use Prodrivers Commons' managed main hub with your own logic.
 * Used implementation can be changed in configuration.
 */
@Deprecated
public abstract class MainHub extends Section {
	private final static Set<SectionCapabilities> capabilities = new HashSet<>();

	static {
		capabilities.add(SectionCapabilities.HUB);
		capabilities.add(SectionCapabilities.PARTY_AWARE);
	}

	MainHub() {
		super(SectionManager.ROOT_NODE_NAME);
	}

	public @NonNull Set<SectionCapabilities> getCapabilities() {
		return capabilities;
	}

	public boolean preJoin(@NonNull Player player, Section targetSection, boolean fromParty) {
		throw new UnsupportedOperationException();
	}

	public boolean join(@NonNull Player player) {
		throw new UnsupportedOperationException();
	}

	public boolean preLeave(@NonNull OfflinePlayer player, Section targetSection, boolean fromParty) {
		throw new UnsupportedOperationException();
	}

	public boolean leave(@NonNull OfflinePlayer player) {
		throw new UnsupportedOperationException();
	}
}
