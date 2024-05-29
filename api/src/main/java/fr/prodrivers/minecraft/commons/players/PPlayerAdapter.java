package fr.prodrivers.minecraft.commons.players;

import java.util.UUID;

/**
 * Prodrivers Players Adapter class
 * <p>
 * Used to retrieve PPlayer instances from platform players.
 */
public interface PPlayerAdapter {
	/**
	 * Creates a Prodrivers Player from a platform-provided Player instance. The implementation does the necessar
	 * conversions.
	 *
	 * @param serverPlayer Player instance from platform
	 * @return Instance of PPlayer
	 * @throws IllegalArgumentException serverPlayer instance is not compatible with used plugin's implementation
	 */
	PPlayer of(Object serverPlayer) throws IllegalArgumentException;

	/**
	 * Creates a Prodrivers Player from a UUID. If the player is not connected, this creates an offline instance.
	 *
	 * @param uniqueId Player's UUID
	 * @return Instance of PPlayer
	 */
	PPlayer of(UUID uniqueId);
}
