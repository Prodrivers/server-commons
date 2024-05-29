package fr.prodrivers.minecraft.commons.players;

import io.ebean.Database;
import net.kyori.adventure.audience.Audience;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import javax.inject.Singleton;
import java.util.Optional;
import java.util.UUID;

@Singleton
public class PaperPPlayerAdapter implements PPlayerAdapter {
	Database database;

	public PaperPPlayerAdapter(Database database) {
		this.database = database;
	}

	@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
	private String getName(OfflinePlayer offlinePlayer, Optional<StoredPlayer> storedPlayer) {
		if(offlinePlayer.getName() != null) {
			return offlinePlayer.getName();
		}
		if(storedPlayer.isPresent()) {
			return storedPlayer.get().getName();
		}
		return offlinePlayer.getUniqueId().toString();
	}

	@Override
	public PPlayer of(Object serverPlayer) throws IllegalArgumentException {
		if(serverPlayer instanceof OfflinePlayer bukkitPlayer) {
			Optional<StoredPlayer> storedPlayer = database.find(StoredPlayer.class).where().eq("uniqueId", bukkitPlayer.getUniqueId()).findOneOrEmpty();

			return new PPlayer(
					bukkitPlayer.getUniqueId(),
					getName(bukkitPlayer, storedPlayer),
					bukkitPlayer.isOnline() ? Optional.of((Audience) bukkitPlayer) : Optional.empty(),
					storedPlayer
			);
		}
		throw new IllegalArgumentException("Platform player is not an OfflinePlayer. Uses the UUID constructor if you intend to use a non-local player.");
	}

	@Override
	public PPlayer of(UUID uniqueId) {
		return of(Bukkit.getOfflinePlayer(uniqueId));
	}
}
