package fr.prodrivers.bukkit.commons.sections;

import fr.prodrivers.bukkit.commons.exceptions.*;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.List;

/**
 * Prodrivers Commons Section Manager
 * <p>
 * Contains methods to manage and interact with Prodrivers Commons Sections.
 * Use this class to gather information, about sections, players, move players between sections and register new
 * sections.
 *
 * Before actually moving a player, a {@link fr.prodrivers.bukkit.commons.events.PlayerChangeSectionEvent} will be triggered and can be cancelled.
 */
public abstract class SectionManager {
	/**
	 * Constant that contains the root node's name.
	 */
	public final static String ROOT_NODE_NAME = "";

	/**
	 * Make a player enter his current section's parent section.
	 *
	 * @param player Player to be moved
	 * @throws NoCurrentSectionException       The player does not have a current section
	 * @throws NoParentSectionException        The player's current section does not have a parent section
	 * @throws IllegalSectionLeavingException  The current section forbids the player to go to the parent section
	 * @throws IllegalSectionEnteringException The player should not enter the parent section
	 */
	public void enter(Player player) throws NoCurrentSectionException, NoParentSectionException, IllegalSectionLeavingException, IllegalSectionEnteringException {
		throw new UnsupportedOperationException();
	}

	/**
	 * Make a player enter a section by section name.
	 * If the current section force a specific target section, this method will fail gracefully.
	 *
	 * @param player      Player to be moved
	 * @param sectionName Section name to go to
	 * @throws InvalidSectionException         Invalid section name provided
	 * @throws IllegalSectionLeavingException  A section along the path the player has to walk forbids the player to leave it
	 * @throws IllegalSectionEnteringException A section along the path the player has to walk forbids the player from entering it
	 */
	public void enter(Player player, String sectionName) throws InvalidSectionException, IllegalSectionLeavingException, IllegalSectionEnteringException {
		throw new UnsupportedOperationException();
	}

	/**
	 * Make a player leave a section and enter another, entering and leaving all intermediate ones. Internal use only.
	 *
	 * @param player     Player to be moved
	 * @param leftNode   Section left by player
	 * @param targetNode Section entered by player
	 * @throws InvalidSectionException         Invalid section name provided
	 * @throws IllegalSectionLeavingException  A section along the path the player has to walk forbids the player to leave it
	 * @throws IllegalSectionEnteringException A section along the path the player has to walk forbids the player from entering it
	 * @throws NoParentSectionException        A section along the path has no parent and is not the root node, denoting a fault in the tree
	 */
	protected void enter(Player player, Section leftNode, Section targetNode) throws IllegalSectionLeavingException, IllegalSectionEnteringException, NoParentSectionException {
		throw new UnsupportedOperationException();
	}

	/**
	 * Register a new section instance against the Prodrivers Commons infrastructure
	 *
	 * @param section Prodrivers Commons-compatible section to register
	 * @throws NullPointerException Provided section is null
	 */
	public void register(Section section) throws NullPointerException {
		throw new UnsupportedOperationException();
	}

	/**
	 * Register a new section instance against the Prodrivers Commons infrastructure
	 *
	 * @param section Prodrivers Commons-compatible section to register
	 * @param silent  if {@code true}, do not log a message on successful registration
	 * @throws NullPointerException Provided section is null
	 */
	public void register(Section section, boolean silent) throws NullPointerException {
		throw new UnsupportedOperationException();
	}

	/**
	 * Registers a player against the Prodrivers Commons infrastructure.
	 * <p>
	 * Required before any interaction with Prodrivers Commons and dependent plugins.
	 * This action is automatically performed by the default section manager on player login, if it is used.
	 *
	 * @param player Player to register
	 */
	public void register(Player player) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Unregisters a player from the Prodrivers Commons infrastructure.
	 * <p>
	 * Once a player is unregistered, no further interaction with Prodrivers Commons and dependent plugins should happen.
	 * This action is automatically performed by the default section manager on player logout, if it is used.
	 *
	 * @param player Player to unregister
	 */
	public void unregister(OfflinePlayer player) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Get a player's current section instance
	 *
	 * @param player Player to consider
	 * @return Player's section instance if possible or null
	 */
	public Section getCurrentSection(OfflinePlayer player) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Get a section instance by its name.
	 *
	 * @param name Name of the section instance to return
	 * @return Section instance if possible or null
	 */
	public Section getSection(String name) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Get the root section instance. Should never be null.
	 *
	 * @return Root section instance
	 */
	public Section getRootSection() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Get a section's current players.
	 *
	 * @param section Section to consider
	 * @return Section's players or null
	 */
	public Collection<Player> getPlayers(Section section) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Check if a player can go from a section by another, by calling in order every section's join and leave check that
	 * is on the path the player needs to take (except for the left node, where only the left check is performed, and
	 * the target node, where only the join check is performed).
	 *
	 * @param player     Player to be moved
	 * @param leftNode   Section left by player
	 * @param targetNode Section entered by player
	 * @param fromParty  Indicate that the process was started by the party owner, passed to sections as some checks
	 *                   may differ depending on the fact that a party player started it or the party owner started it
	 * @throws InvalidSectionException  End node is a transitive node, which is invalid
	 * @throws NoParentSectionException A section along the path has no parent and is not the root node, denoting a fault in the tree
	 */
	protected boolean canPlayerWalkAlongSectionPath(Player player, Section leftNode, Section targetNode, boolean fromParty) throws InvalidSectionException, NoParentSectionException {
		throw new UnsupportedOperationException();
	}

	/**
	 * Go from a section to another section higher in the tree. The last node is either the provided target node or
	 * the top-most node of the left node's branch.
	 * At the end, the player is registered in the final processed node.
	 *
	 * @param player       The player that should walk back nodes.
	 * @param start        The node that we start from.
	 * @param target       The node, higher in the tree, that we want the player to go to at the end of this function.
	 * @param visitedNodes List that should receive all visited nodes
	 * @param fromParty    Indicate that the process was started by the party owner
	 * @return @{code true} success of walking back. @{code false} if any section did not authorized the player leaving or entering it.
	 * @throws NoParentSectionException A section along the path has no parent and is not the root node, denoting a fault in the tree
	 */
	protected boolean walkBackward(Player player, Section start, Section target, List<Section> visitedNodes, boolean fromParty) throws NoParentSectionException {
		throw new UnsupportedOperationException();
	}

	/**
	 * Go from a section to another section lower in the tree. The last node is either the provided target node or
	 * the bottom-most node of the left node's branch.
	 * At the end, the player is registered in the final processed node.
	 *
	 * @param player       The player that should walk back nodes.
	 * @param start        The node that we start from.
	 * @param target       The node, lower in the tree, that we want the player to go to at the end of this function.
	 * @param visitedNodes List that should receive all visited nodes
	 * @param fromParty    Indicate that the process was started by the party owner
	 * @return @{code true} success of walking back. @{code false} if any section did not authorized the player leaving or entering it.
	 * @throws InvalidSectionException End node is a transitive node, which is invalid
	 */
	protected boolean walkForward(Player player, Section start, Section target, List<Section> visitedNodes, boolean fromParty) throws InvalidSectionException {
		throw new UnsupportedOperationException();
	}

	/**
	 * Find the last common node in the path between two nodes.
	 *
	 * @param left   First node to consider
	 * @param target Second node to consider
	 * @return Last common node on the paths of both nodes.
	 */
	protected Section findCommonNode(Section left, Section target) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Build the section tree.
	 * Fills, for each node, its parent and children. Creates intermediary nodes whenever needed.
	 * <p>
	 * This method is automatically called by Prodrivers Commons on server activation.
	 */
	public void buildSectionTree() {
		throw new UnsupportedOperationException();
	}
}
