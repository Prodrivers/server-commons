package fr.prodrivers.minecraft.commons.hubs;

import fr.prodrivers.minecraft.commons.sections.Section;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.inject.Singleton;

/**
 * Default implementation of main hub.
 * <p>
 * Teleports a player to a location defined in configuration and plays a sound.
 * If the player can not be teleported (for example, due to an invalid location), the player is kicked.
 * <p>
 * Correctly handles players wanting to leave the hub by forbidding them and teleporting them.
 */
@Singleton
public class DefaultMainHub extends MainHub {
	@Override
	public boolean preJoin(@NonNull Player player, Section targetSection, boolean fromParty) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean join(@NonNull Player player) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean preLeave(@NonNull OfflinePlayer player, Section targetSection, boolean fromParty) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean leave(@NonNull OfflinePlayer player) {
		throw new UnsupportedOperationException();
	}
}
