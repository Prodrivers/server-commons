package fr.prodrivers.minecraft.commons.players;

import net.kyori.adventure.audience.Audience;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Optional;
import java.util.UUID;

/**
 * Prodrivers Players class
 * <p>
 * Used to abstract player-related functions from platform implementation. The player may be a locally-connected player
 * or an offline instance.
 * <p>
 * Provides access to its name, unique ID, a {@link net.kyori.adventure.audience.Audience} instance and
 * a generic {@link StoredPlayer} using the plugin's own database instance.
 * <p>
 * Optional fields are provided only when possible:
 * {@link PPlayer#audience} when the player is locally-connected,
 * {@link PPlayer#properties} when a corresponding record is found in database.
 */
public record PPlayer(
		@NonNull UUID uniqueId,
		@NonNull String name,
		@NonNull Optional<Audience> audience,
		@NonNull Optional<StoredPlayer> properties
) {}
