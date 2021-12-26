package fr.prodrivers.bukkit.commons.parties;

import fr.prodrivers.bukkit.commons.exceptions.PartyCannotInviteYourselfException;
import fr.prodrivers.bukkit.commons.exceptions.PlayerNotConnectedException;
import fr.prodrivers.bukkit.commons.exceptions.PlayerNotInvitedToParty;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.UUID;

/**
 * Prodrivers Commons Party Manager.
 * <p>
 * Allows interacting with Prodrivers Commons global parties.
 * Invites, kick, disband and others are directly handled by the plugin.
 *
 * Used implementation can be changed in configuration.
 */
public interface PartyManager {
	/**
	 * Checks if given player is in a party.
	 *
	 * @param playerUniqueId Player unique ID
	 * @return {@code true} if the player is in a party
	 */
	boolean isInParty(@NonNull final UUID playerUniqueId);

	/**
	 * Get player's party.
	 *
	 * @param playerUniqueId Player Unique ID
	 * @return Player's party
	 */
	Party getParty(@NonNull final UUID playerUniqueId);

	/**
	 * Returns all known parties.
	 *
	 * @return Parties
	 */
	Iterable<Party> getParties();

	/**
	 * Creates a new party.
	 *
	 * @param ownerUniqueId Party owner
	 * @return New party instance
	 */
	Party createParty(@NonNull final UUID ownerUniqueId);

	/**
	 * Assigns an existing party to a player.
	 *
	 * @param party          Party to add player to
	 * @param playerUniqueId Player's UniqueId
	 * @return {@code true} if player was added to party
	 */
	boolean addToParty(@NonNull final Party party, @NonNull final UUID playerUniqueId);

	/**
	 * Removes existing party from the manager.
	 *
	 * @param playerUniqueId Player's UniqueId
	 * @return {@code true} if player was removed from party
	 */
	boolean removeFromParty(@NonNull final UUID playerUniqueId);

	/**
	 * Disband and remove a party.
	 *
	 * @param party Party to disband
	 */
	void disband(@NonNull final Party party);

	/**
	 * Invites a player to a party
	 *
	 * @param inviterPlayerUniqueId Inviter player
	 * @param invitedPlayerUniqueId Invited player
	 * @return {@code true} if invite was sent with success
	 * @throws PlayerNotConnectedException        Inviter or invited player ID is invalid or not connected
	 * @throws PartyCannotInviteYourselfException Player trie sto invite itself
	 */
	boolean invite(@NonNull final UUID inviterPlayerUniqueId, @NonNull final UUID invitedPlayerUniqueId) throws PlayerNotConnectedException, PartyCannotInviteYourselfException;

	/**
	 * Invites a player to a party
	 *
	 * @param inviterPlayerUniqueId Inviter player
	 * @param invitedPlayerUniqueId Invited player
	 * @throws PlayerNotConnectedException Inviter or invited player ID is invalid or not connected
	 * @throws PlayerNotInvitedToParty     Player is not invited to party
	 */
	void accept(@NonNull final UUID inviterPlayerUniqueId, @NonNull final UUID invitedPlayerUniqueId) throws PlayerNotConnectedException, PlayerNotInvitedToParty;

	/**
	 * Assigns a new owner to a party.
	 *
	 * @param party            Party to assign owner to
	 * @param newOwnerUniqueId New owner's Unique ID
	 * @return {@code true} if new owner was assigned
	 */
	boolean assignOwner(@NonNull final Party party, @NonNull final UUID newOwnerUniqueId);

	/**
	 * Invites a player to a party
	 *
	 * @param invitedPlayerUniqueId Invited player
	 * @param party                 Associated party
	 */
	void addPartyInvite(@NonNull final UUID invitedPlayerUniqueId, @NonNull final Party party);

	/**
	 * Checks if the player has pending party invitations.
	 *
	 * @param invitedPlayerUniqueId Invited player's Unique ID
	 * @return {@code true} if the player has pending party invites.
	 */
	boolean hasPartyInvites(@NonNull final UUID invitedPlayerUniqueId);

	/**
	 * Get parties on which the player has pending party invitations.
	 *
	 * @param invitedPlayerUniqueId Invited player's Unique ID
	 * @return Parties that sent an invitation to the specified player
	 */
	Iterable<Party> getPartyInvites(@NonNull final UUID invitedPlayerUniqueId);

	/**
	 * Removes all pending party invitations for a player.
	 *
	 * @param invitedPlayerUniqueId Invited player's Unique ID
	 */
	void removePartyInvites(@NonNull final UUID invitedPlayerUniqueId);
}
