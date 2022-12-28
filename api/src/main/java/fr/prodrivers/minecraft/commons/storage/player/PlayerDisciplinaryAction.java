package fr.prodrivers.minecraft.commons.storage.player;

import io.ebean.annotation.Cache;
import io.ebean.annotation.NotNull;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

/**
 * Player Disciplinary Action
 * <p>
 * Record that represents a disciplinary action taken against a player, with an optional message and expiration.
 */
@Entity
@Table(name = "players_disciplinary_actions")
@Cache
public record PlayerDisciplinaryAction(
		@Id @NotNull UUID disciplinedPlayer,
		@NotNull ActionType type,
		String message,
		Date expiration,
		UUID decider
) {
	/**
	 * Describes the possible disciplinary actions
	 */
	public enum ActionType {
		/**
		 * Player is globally banned from servers
		 */
		BAN,
		/**
		 * Player is globally muted on servers
		 */
		MUTE
	}

	/**
	 * Get if the disciplinary action is still applicable at current time
	 *
	 * @return {@code true} if disciplinary action is to be upheld
	 */
	public boolean isApplicable() {
		return expiration == null || expiration.after(Date.from(Instant.now()));
	}
}
