package fr.prodrivers.bukkit.commons.parties;

import java.util.UUID;

/**
 * Prodrivers Commons Party Manager.
 * <p>
 * Allows to interect with Prodrivers Commons global parties.
 * Invites, kick, disband and others are directly handled by the plugin.
 */
public class PartyManager {
	/**
	 * Checks if given player is in a party.
	 *
	 * @param playerUniqueId Player unique ID
	 * @return {@code true} if the player is in a party
	 */
	public static boolean isInParty(UUID playerUniqueId) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Get player's party.
	 *
	 * @param playerUniqueId Player Unqiue ID
	 * @return Player's party
	 */
	public static Party getParty(UUID playerUniqueId) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Returns all known parties.
	 *
	 * @return Parties
	 */
	public static Iterable<Party> getParties() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Creates a new party.
	 *
	 * @param ownerUniqueId Party owner
	 * @return New party instance
	 */
	public static Party createParty(UUID ownerUniqueId) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Assigns an existing party to a player.
	 *
	 * @param playerUniqueId Player's UniqueId
	 */
	static void addParty(UUID playerUniqueId, Party party) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Removes existing party from the manager.
	 *
	 * @param playerUniqueId Player's UniqueId
	 */
	static void removeParty(UUID playerUniqueId) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Invites a player to a party
	 *
	 * @param invitedPlayerUniqueId Invited player
	 * @param party                 Associated party
	 */
	public static void addPartyInvite(UUID invitedPlayerUniqueId, Party party) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Checks if the player has pending party invitations.
	 *
	 * @param invitedPlayerUniqueId Invited player's Unique ID
	 * @return {@code true} if the player has pending party invites.
	 */
	public static boolean hasPartyInvites(UUID invitedPlayerUniqueId) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Get parties on which the player has pending party invitations.
	 *
	 * @param invitedPlayerUniqueId Invited player's Unique ID
	 * @return Parties that sent an invitation to the specified player
	 */
	public static Iterable<Party> getPartyInvites(UUID invitedPlayerUniqueId) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Removes all pending party invitations for a player.
	 *
	 * @param invitedPlayerUniqueId Invited player's Unique ID
	 */
	public static void removePartyInvites(UUID invitedPlayerUniqueId) {
		throw new UnsupportedOperationException();
	}
}
