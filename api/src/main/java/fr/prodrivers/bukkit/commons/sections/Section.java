package fr.prodrivers.bukkit.commons.sections;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Define the base Prodrivers Commons Section implementation. <i>Sections make plugins play nice together.</i>
 * <p>
 * A section is a logical subdivision of a server. A section can be a hub, a mini-game hub, a mini-game instance, or any
 * other needed subdivision. Sections are organized in a tree, with a root (default) section that is its point of entry.
 * Each section has event handlers to react to a player entering or leaving it, doing needed preparation and cleanup
 * work.
 * <p>
 * Its main use is to allow different plugins to play together by calling preparation and/or cleanup code when player
 * is moving between different subdivisions of the server without handling it themselves/having special code to handle
 * all cases, such as moving between a hub and a mini-game, or a mini-game to another mini-game. Prodrivers Commons'
 * role is to ensure that all join and leave event handlers are correctly called when moving along the section tree.
 * For example, it allows a player to move from one plugin's mini-game to another plugin's mini-game, on the same server
 * or across servers, with proper quit and join code called for each of them, without those plugins having to handle
 * themselves.
 * <p>
 * It ultimately allows plugins that where not built to handles other plugins on a server or across servers in mind to
 * implement those capabilities without much modifications, or even without any modifications by having another plugin
 * handle the interface for it.
 * <p>
 * Every new player enters the section mechanism by entering the root section. Each player, at any point in time, is
 * guaranteed to be part of a single section, as long as it is part of the mechanism.
 * <p>
 * Sections implementing it shall register themselves using {@link SectionManager#register(Section) SectionManager.register}
 */
public abstract class Section {
	/**
	 * Initialize a new section
	 *
	 * @param fullName Section full name
	 */
	public Section(@NonNull String fullName) {
	}

	/**
	 * Get the section's name, without its parents sections
	 *
	 * @return Section's name
	 */
	public @NonNull String getName() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Get the section's full name, containing the name of its parents sections, as used by players in commands
	 *
	 * @return Section's full name
	 */
	public @NonNull String getFullName() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Get the section's full name, containing the name of its parent sections, split along the node separator
	 *
	 * @return Section's split full name
	 */
	public @NonNull List<String> getSplitFullName() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Get all the section's parents full names, including the section's fuull name
	 *
	 * @return Section's parents full name
	 */
	public @NonNull List<String> getParentsFullName() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Get all capabilities of a section.
	 *
	 * @return {@code true} Section capabilities
	 */
	public abstract @NonNull Set<SectionCapabilities> getCapabilities();

	/**
	 * Section pre join callback, called to check whether or not the player should enter the section.
	 * <p>
	 * This could be called at any point in the section's lifetime.
	 * The player is not guaranteed to actually enter the section.
	 * <p>
	 * The return value should be a guarantee that the player can enter the section.
	 *
	 * @param player    Player that should join the section
	 * @param fromParty Indicate that the process was started by the party owner
	 * @return {@code true} The user is authorized to enter the section
	 */
	public abstract boolean preJoin(@NonNull Player player, boolean fromParty);

	/**
	 * Section join callback. Should do the actions required when a player enters a section.
	 *
	 * @param player Player that joins the section
	 * @return {@code true} Continue the section enter process
	 */
	public abstract boolean join(@NonNull Player player);

	/**
	 * Section pre leave callback, called to check whether or not the player can leave the section.
	 * <p>
	 * This could be called at any point in the section's lifetime.
	 * The player is not guaranteed to actually leave the section.
	 * This can be called even if the player is not actually in the section (ex: called during the check pass of
	 * section tree traversal)
	 * <p>
	 * The return value should be a guarantee that the player can leave the section.
	 *
	 * @param player    Player that should leave the section
	 * @param fromParty Indicate that the process was started by the party owner
	 * @return {@code true} The user is authorized to leave the section
	 */
	public abstract boolean preLeave(@NonNull OfflinePlayer player, boolean fromParty);

	/**
	 * Section leave callback. Should do the actions required when a player leaves a section.
	 *
	 * @param player Player that leaves the section
	 * @return {@code true} Continue the section enter process
	 */
	public abstract boolean leave(@NonNull OfflinePlayer player);

	/**
	 * Get all players in a section.
	 *
	 * @return Section's players
	 */
	public @NonNull Collection<Player> getPlayers() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Get section's parent section.
	 *
	 * @return Section's parent section
	 */
	public @Nullable Section getParentSection() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Checks if a player is inside this section.
	 *
	 * @param player Player to consider
	 * @return {@code true} if the player is in this section
	 */
	public boolean contains(@NonNull OfflinePlayer player) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Adds a player to this section.
	 *
	 * @param player Player to add
	 * @return {@code true} if the player was added to this section
	 */
	protected boolean add(@NonNull OfflinePlayer player) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Removes a player from this section.
	 *
	 * @param player Player to remove
	 * @return {@code true} if the player was removed from this section
	 */
	protected boolean remove(@NonNull OfflinePlayer player) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Get section's child sections.
	 *
	 * @return Section's child sections
	 */
	public @NonNull Collection<Section> getChildSections() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Check if a section is a child to this section.
	 *
	 * @param child Section to consider
	 * @return {@code true} if the section is a child to this section
	 */
	public boolean contains(@NonNull Section child) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Adds a section as a child to this section.
	 *
	 * @param section Section to add as a child
	 */
	protected void addChildren(@NonNull Section section) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Removes a section as a child to this section.
	 *
	 * @param section Section to remove as a child
	 */
	protected void removeChildren(@NonNull Section section) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Adds a section as a parent to this section.
	 * A section can only have one parent a a time. Calling this will erase the previous parent.
	 *
	 * @param section Section to add as a parent
	 */
	protected void addParent(@NonNull Section section) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Removes the parent from this section.
	 * Ensure that you add another parent afterwards. Only the root section should have no parent.
	 */
	protected void removeParent() {
		throw new UnsupportedOperationException();
	}
}
