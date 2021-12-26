package fr.prodrivers.bukkit.commons.parties;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Prodrivers Commons Party instance
 *
 * Used implementation can be changed in configuration.
 */
public interface Party {
	/**
	 * Returns owner's unique id.
	 *
	 * @return Owner's unique id
	 */
	UUID getOwnerUniqueId();

	/**
	 * Returns party members.
	 *
	 * @return List of party members
	 */
	ArrayList<UUID> getPlayers();

	/**
	 * Register a player to the party.
	 *
	 * @param playerUniqueId Player to add Unique ID
	 */
	void registerPlayer(final UUID playerUniqueId);

	/**
	 * Unregister a player from the party.
	 *
	 * @param playerUniqueId Player to remove's Unique ID
	 */
	void unregisterPlayer(final UUID playerUniqueId);

	/**
	 * Checks if a player is present in party list.
	 *
	 * @param playerUniqueId Player's Unique ID
	 * @return {@code true} if the player is within the party list.
	 */
	boolean containsPlayer(final UUID playerUniqueId);

	/**
	 * Checks if a player is the party owner
	 *
	 * @param playerUniqueId Player's Unique ID
	 * @return {@code true} if the player is the party owner
	 */
	boolean isPartyOwner(final UUID playerUniqueId);

	/**
	 * Set a player as the new party owner
	 *
	 * @param newOwner New owner
	 * @return {@code true} if new owner has been assigned
	 */
	boolean assignOwner(final UUID newOwner);

	/**
	 * Returns the party's number of players.
	 *
	 * @return Party's number of players
	 */
	int size();

	/**
	 * Clear a party of all its players.
	 */
	void clear();

	/**
	 * Send a message as a player.
	 *
	 * @param player  Sending player
	 * @param message Message to send
	 */
	void chatAsPlayer(final Player player, String message);

	/**
	 * Send a message to a player.
	 *
	 * @param receiverUniqueId Sending player
	 * @param message          Message to send
	 * @param substitutions    Values to pass to string format function
	 */
	void send(final UUID receiverUniqueId, final PartyMessage message, final Object... substitutions);

	/**
	 * Broadcast a message to the party.
	 *
	 * @param message       Message to send
	 * @param substitutions Values to pass to string format function
	 */
	void broadcast(final PartyMessage message, final Object... substitutions);

	/**
	 * Broadcast a message to the party, except some players.
	 *
	 * @param message       Message to send
	 * @param excluded      Players that should not receive the message
	 * @param substitutions Values to pass to string format function
	 */
	void broadcast(final PartyMessage message, final List<UUID> excluded, final Object... substitutions);

	/**
	 * Broadcast a message to the party.
	 *
	 * @param message       Message to send
	 */
	void broadcast(final String message);

	/**
	 * Broadcast a message to the party, except some players.
	 *
	 * @param message       Message to send
	 * @param excluded      Players that should not receive the message
	 */
	void broadcast(final String message, final List<UUID> excluded);
}

