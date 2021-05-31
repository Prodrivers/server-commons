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
 * <br/>
 * A section is a logical subdivision of a server. A section can be a hub, a mini-game hub, a mini-game instance, or any
 * other needed subdivision. Sections are organized in a tree, with a root (default) section that is its point of entry.
 * Each section has event handlers to react to a player entering or leaving it, doing needed preparation and cleanup
 * work.
 * <br/>
 * Its main use is to allow different plugins to play together by calling preparation and/or cleanup code when player
 * is moving between different subdivisions of the server without handling it themselves/having special code to handle
 * all cases, such as moving between a hub and a mini-game, or a mini-game to another mini-game. Prodrivers Commons'
 * role is to ensure that all join and leave event handlers are correctly called when moving along the section tree.
 * For example, it allows a player to move from one plugin's mini-game to another plugin's mini-game, on the same server
 * or across servers, with proper quit and join code called for each of them, without those plugins having to handle
 * themselves.
 * <br/>
 * It ultimately allows plugins that where not built to handles other plugins on a server or across servers in mind to
 * implement those capabilities without much modifications, or even without any modifications by having another plugin
 * handle the interface for it.
 * <br/>
 * Every new player enters the section mechanism by entering the root section. Each player, at any point in time, is
 * guaranteed to be part of a single section, as long as it is part of the mechanism.
 * <br/>
 * Sections implementing it shall register themselves using SectionManager.register
 */
public abstract class Section {
	/**
	 * Initialize a new section
	 *
	 * @param fullName Section full name
	 */
	public Section(@NonNull String fullName) {}

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
	 *
	 * This could be called at any point in the section's lifetime.
	 * The player is not guaranteed to actually enter the section.
	 *
	 * The return value should be a guarantee that the player can enter the section.
	 *
	 * @param player        Player that should join the section
	 * @return {@code true} The user is authorized to enter the section
	 */
	public abstract boolean preJoin(@NonNull Player player);

	/**
	 * Section join callback. Should do the actions required when a player enters a section.
	 *
	 * @param player        Player that joins the section
	 * @return {@code true} Continue the section enter process
	 */
	public abstract boolean join(@NonNull Player player);

	/**
	 * Section pre leave callback, called to check whether or not the player can leave the section.
	 *
	 * This could be called at any point in the section's lifetime.
	 * The player is not guaranteed to actually leave the section.
	 * This can be called even if the player is not actually in the section (ex: called during the check pass of
	 * section tree traversal)
	 *
	 * The return value should be a guarantee that the player can leave the section.
	 *
	 * @param player        Player that should leave the section
	 * @return {@code true} The user is authorized to leave the section
	 */
	public abstract boolean preLeave(@NonNull OfflinePlayer player);

	/**
	 * Section leave callback. Should do the actions required when a player leaves a section.
	 *
	 * @param player         Player that leaves the section
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

	public boolean contains(@NonNull OfflinePlayer player) {
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

	public boolean contains(@NonNull Section child) {
		throw new UnsupportedOperationException();
	}

	protected boolean add(@NonNull OfflinePlayer player) {
		throw new UnsupportedOperationException();
	}

	protected boolean remove(@NonNull OfflinePlayer player) {
		throw new UnsupportedOperationException();
	}

	protected void addChildren(@NonNull Section section) {
		throw new UnsupportedOperationException();
	}

	protected void removeChildren(@NonNull Section section) {
		throw new UnsupportedOperationException();
	}

	protected void addParent(@NonNull Section section) {
		throw new UnsupportedOperationException();
	}

	protected void removeParent() {
		throw new UnsupportedOperationException();
	}
}
