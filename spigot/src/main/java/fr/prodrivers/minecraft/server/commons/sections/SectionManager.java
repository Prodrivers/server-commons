package fr.prodrivers.minecraft.server.commons.sections;

import fr.prodrivers.minecraft.server.commons.events.PlayerChangeSectionEvent;
import fr.prodrivers.minecraft.server.commons.exceptions.*;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

/**
 * Prodrivers Commons Section Manager
 * <p>
 * Contains methods to manage and interact with Prodrivers Commons Sections.
 * Use this class to gather information, about sections, players, move players between sections and register new
 * sections.
 * <p>
 * Before actually moving a player, a {@link PlayerChangeSectionEvent} will be triggered and can be cancelled.
 * <p>
 * Used implementation can be changed in configuration.
 */
public interface SectionManager {
	/**
	 * Constant that contains the root node's name.
	 */
	String ROOT_NODE_NAME = "";

	/**
	 * Make a player enter his current section's parent section.
	 *
	 * @param player Player to be moved
	 * @return {@code true} if player was correctly moved to parent section
	 * @throws NoCurrentSectionException       The player does not have a current section
	 * @throws NoParentSectionException        The player's current section does not have a parent section
	 * @throws IllegalSectionLeavingException  The current section forbids the player to go to the parent section
	 * @throws IllegalSectionEnteringException The player should not enter the parent section
	 */
	boolean enter(Player player) throws NoCurrentSectionException, NoParentSectionException, IllegalSectionLeavingException, IllegalSectionEnteringException;

	/**
	 * Make a player enter a section by section name.
	 * If the current section force a specific target section, this method will fail gracefully.
	 *
	 * @param player      Player to be moved
	 * @param sectionName Section name to go to
	 * @return {@code true} if player was correctly moved to specified section
	 * @throws InvalidSectionException         Invalid section name provided
	 * @throws IllegalSectionLeavingException  A section along the path the player has to walk forbids the player to leave it
	 * @throws IllegalSectionEnteringException A section along the path the player has to walk forbids the player from entering it
	 */
	boolean enter(Player player, String sectionName) throws InvalidSectionException, IllegalSectionLeavingException, IllegalSectionEnteringException;

	/**
	 * Register a new section instance against the Prodrivers Commons infrastructure
	 *
	 * @param section Prodrivers Commons-compatible section to register
	 * @throws NullPointerException Provided section is null
	 */
	void register(Section section) throws NullPointerException;

	/**
	 * Register a new section instance against the Prodrivers Commons infrastructure
	 *
	 * @param section Prodrivers Commons-compatible section to register
	 * @param silent  if {@code true}, do not log a message on successful registration
	 * @throws NullPointerException Provided section is null
	 */
	void register(Section section, boolean silent) throws NullPointerException;

	/**
	 * Registers a player against the Prodrivers Commons infrastructure.
	 * <p>
	 * Required before any interaction with Prodrivers Commons and dependent plugins.
	 * This action is automatically performed by the default section manager on player login, if it is used.
	 *
	 * @param player Player to register
	 * @return {@code true} if player was correctly registered
	 */
	boolean register(Player player);

	/**
	 * Unregisters a player from the Prodrivers Commons infrastructure.
	 * <p>
	 * Once a player is unregistered, no further interaction with Prodrivers Commons and dependent plugins should happen.
	 * This action is automatically performed by the default section manager on player logout, if it is used.
	 *
	 * @param player Player to unregister
	 */
	void unregister(OfflinePlayer player);

	/**
	 * Get a player's current section instance
	 *
	 * @param player Player to consider
	 * @return Player's section instance if possible or null
	 */
	Section getCurrentSection(OfflinePlayer player);

	/**
	 * Get all registered sections.
	 *
	 * @return Iterator going through to all sections
	 */
	Iterable<Section> getSections();

	/**
	 * Get a section instance by its name.
	 *
	 * @param name Name of the section instance to return
	 * @return Section instance if possible or null
	 */
	Section getSection(String name);

	/**
	 * Get the root section instance. Should never be null.
	 *
	 * @return Root section instance
	 */
	Section getRootSection();

	/**
	 * Build the section tree.
	 * Fills, for each node, its parent and children. Creates intermediary nodes whenever needed.
	 * <p>
	 * This method is automatically called by Prodrivers Commons on server activation.
	 */
	void buildSectionTree();

	/**
	 * Opens a selection UI for a specific section.
	 *
	 * @param sectionName Section's full name to open UI for
	 * @param player      Player that opened the selection UI
	 * @throws InvalidSectionException No corresponding section exists to provided name
	 * @throws InvalidUIException      Section reports to have custom UI, but does not implement it
	 */
	void ui(String sectionName, Player player) throws InvalidSectionException, InvalidUIException;
}
