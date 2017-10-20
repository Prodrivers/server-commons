package fr.prodrivers.bukkit.commons.parties;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Prodrivers Commons Party instance
 */
public class Party {
	/**
	 * Generate a new party.
	 *
	 * @param ownerUniqueId Party owner's Unique ID
	 */
	Party( final UUID ownerUniqueId ) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Returns owner's unique id.
	 *
	 * @return Owner's unique id
	 */
	public UUID getOwnerUniqueId() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Returns party members.
	 *
	 * @return List of party members
	 */
	public ArrayList<UUID> getPlayers() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Adds a player.
	 *
	 * @param playerUniqueId Player to add's Unique ID
	 * @return {@code true} if the player was added to the party
	 */
	public boolean addPlayer( final UUID playerUniqueId ) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Removes a player.
	 *
	 * @param playerUniqueId Player to remove's Unique Id
	 * @return {@code true} if the player was removed from the party
	 */
	public boolean removePlayer( final UUID playerUniqueId ) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Checks if a player is present in party list.
	 *
	 * @param playerUniqueId Player's Unique ID
	 * @return {@code true} if the player is within the party list.
	 */
	public boolean containsPlayer( final UUID playerUniqueId ) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Checks if a player is the party owner
	 *
	 * @param playerUniqueId Player's Unique ID
	 * @return {@code true} if the player is the party owner
	 */
	public boolean isPartyOwner( final UUID playerUniqueId ) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Returns the party's number of players.
	 *
	 * @return Party's number of players
	 */
	public int size() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Disband/Delete the party.
	 */
	public void disband() {
		throw new UnsupportedOperationException();
	}
}

