package fr.prodrivers.minecraft.commons.players;

import net.kyori.adventure.audience.Audience;

import java.util.Optional;
import java.util.UUID;

/**
 * Prodrivers Players class
 * <p>
 * Used to abstract player-related functions from server implementation. The player may be a locally-connected player or
 * an offline instance.
 * <p>
 * Provides access to an {@link net.kyori.adventure.audience.Audience} instance and a generic {@link StoredPlayer}
 * using the plugin's own database instance.
 * <p>
 * Optional fields are provided only when possible:
 * {@link PPlayer#audience} when the player is locally-connected,
 * {@link PPlayer#properties} when a corresponding record is found in database.
 */
public record PPlayer(
		UUID uniqueId,
		Optional<Audience> audience,
		Optional<StoredPlayer> properties
) {
	/**
	 * Creates a Prodrivers Player from a server-provided Player instance. The implementation does the necessary conversions.
	 *
	 * @param serverPlayer Player instance from server implementation
	 * @return Instance of PPlayer
	 * @throws IllegalArgumentException serverPlayer instance is not compatible with used plugin's implementation
	 */
	public static PPlayer of(Object serverPlayer) throws IllegalArgumentException {
		throw new UnsupportedOperationException();
	}

	/**
	 * Creates a Prodrivers Player from a UUID. If the player is not connected, this creates an offline instance.
	 *
	 * @param uniqueId Player's UUID
	 * @return Instance of PPlayer
	 */
	public static PPlayer of(UUID uniqueId) {
		throw new UnsupportedOperationException();
	}
}
