package fr.prodrivers.bukkit.commons.sections;

import fr.prodrivers.bukkit.commons.exceptions.*;
import org.bukkit.entity.Player;

import java.util.Collection;

/**
 * Prodrivers Commons Section Manager
 * <br/>
 * Contains methods to manage and interact with Prodrivers Commons Sections.
 * Use this class to gather information, about sections, players, move players between sections and register new
 * sections.
 */
public class SectionManager {
	/**
	 * Make a player enter his current section's parent section.
	 * @param player Player to be moved
	 * @throws NoCurrentSectionException The player does not have a current section
	 * @throws NoParentSectionException The player's current section does not have a parent section
	 * @throws IllegalSectionLeavingException The current section forbids the player to go to the parent section
	 * @throws IllegalSectionEnteringException The player should not enter the parent section
	 */
	public static void enter(Player player) throws NoCurrentSectionException, NoParentSectionException, IllegalSectionLeavingException, IllegalSectionEnteringException {
		throw new UnsupportedOperationException();
	}

	/**
	 * Make a player enter a section by section name.
	 * If the current section force a specific target section, this method will fail gracefully.
	 * @param player Player to be moved
	 * @param sectionName Section name to go to
	 * @throws InvalidSectionException Invalid section name provided
	 * @throws IllegalSectionLeavingException A section along the path the player has to walk forbids the player to leave it
	 * @throws IllegalSectionEnteringException A section along the path the player has to walk forbids the player from entering it
	 */
	public static void enter(Player player, String sectionName) throws InvalidSectionException, IllegalSectionLeavingException, IllegalSectionEnteringException {
		throw new UnsupportedOperationException();
	}

	/**
	 * Register a new section instance against the Prodrivers Commons infrastructure
	 * @param section Prodrivers Commons-compatible section to register
	 * @throws NullPointerException Provided section is null
	 */
	public static void register(Section section) throws NullPointerException {
		throw new UnsupportedOperationException();
	}

	/**
	 * Register a new section instance against the Prodrivers Commons infrastructure
	 * @param section Prodrivers Commons-compatible section to register
	 * @param silent if {@code true}, do not log a message on successful registration
	 * @throws NullPointerException Provided section is null
	 */
	public static void register(Section section, boolean silent) throws NullPointerException {
		throw new UnsupportedOperationException();
	}

	/**
	 * Get a player's current section instance
	 * @param player Player to consider
	 * @return Player's section instance if possible or null
	 */
	public static Section getCurrentSection(Player player) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Get a section's current players. This is provided by the plugin and should not be implemented.
	 *
	 * @return Section's players or null
	 */
	public static Collection<Player> getPlayers(Section section) {
		throw new UnsupportedOperationException();
	}
}
