package fr.prodrivers.bukkit.commons.sections;

import fr.prodrivers.bukkit.commons.exceptions.*;
import org.bukkit.entity.Player;

/**
 * Prodrivers Commons Section Manager
 */
public class SectionManager {
	/**
	 * Make a player enter his current section's preferred next section
	 * @param player Player to be moved
	 * @throws NoCurrentSectionException The player does not have a current section
	 * @throws NoNextSectionException The player's current section does not have a next section
	 * @throws IllegalSectionLeavingException The current section forbids the player to go to the preferred next section
	 * @throws IllegalSectionEnteringException The player should not enter the preferred next section
	 */
	public static void enter( Player player ) throws NoCurrentSectionException, NoNextSectionException, IllegalSectionLeavingException, IllegalSectionEnteringException {
		throw new UnsupportedOperationException();
	}


	/**
	 * Make a player enter a section by section name
	 * If the current section force a specific target section, this method will fail gracefully.
	 * @param player Player to be moved
	 * @param sectionName Section name to go to
	 * @throws InvalidSectionException Invalid section name provided
	 * @throws IllegalSectionLeavingException The current section forbids the player to go to the desired section
	 * @throws IllegalSectionEnteringException The player should not enter the desired section
	 */
	public static void enter( Player player, String sectionName ) throws InvalidSectionException, IllegalSectionLeavingException, IllegalSectionEnteringException {
		throw new UnsupportedOperationException();
	}

	/**
	 * Make a player enter a section by section name
	 * @param player Player to be moved
	 * @param sectionName Section name to go to
	 * @param force Force the player to go to the target section, even if his current section prefers another. He will be successively transferred to intermediate preferred sections to avoid any problems.
	 * @throws InvalidSectionException Invalid section name provided
	 * @throws IllegalSectionLeavingException The current section forbids the player to go to the desired section
	 * @throws IllegalSectionEnteringException The player should not enter the desired section
	 */
	public static void enter( Player player, String sectionName, boolean force ) throws InvalidSectionException, IllegalSectionLeavingException, IllegalSectionEnteringException {
		throw new UnsupportedOperationException();
	}

	/**
	 * Register a new section instance against the Prodrivers Commons infrastructure
	 * @param section Prodrivers Commons-compatible section to register
	 * @throws NullPointerException Provided section is null
	 */
	public static void register( IProdriversSection section ) throws NullPointerException {
		throw new UnsupportedOperationException();
	}

	/**
	 * Get a player's current section instance
	 * @param player Player to consider
	 * @return Player's section instance if possible or null
	 */
	public static IProdriversSection getCurrentSection( Player player ) {
		throw new UnsupportedOperationException();
	}
}
