package fr.prodrivers.bukkit.commons.sections;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Collections;
import java.util.Set;

/**
 * Transitive Section
 * <p>
 * A transitive section is a section automatically created by Prodrivers Commons to fill holes in the section tree.
 * It is final and non-instantiable, as it is used internally only.
 * It does nothing on its own, designed to allow players to pass through to non-transitive sections, but has special
 * handling code to ensure that:
 * - players can not walk down the tree to a transitive section, i.e. a transitive section can not be the target node
 * when the player has to walk up and down the tree.
 * - players walking up the tree to a transitive section ends up walking to its parent until it encounters a
 * non-transitive section, i.e. a transitive section, when it is the target node and is attained only by walking up the
 * tree, acts a proxy for the non-transitive parent section.
 */
public final class TransitiveSection extends Section {
	public TransitiveSection(String fullName) {
		super(fullName);
	}

	@Override
	public @NonNull Set<SectionCapabilities> getCapabilities() {
		return Collections.singleton(SectionCapabilities.TRANSITIVE);
	}

	@Override
	public boolean preJoin(@NonNull Player player, Section targetSection, boolean fromParty) {
		return true;
	}

	@Override
	public boolean join(@NonNull Player player) {
		return true;
	}

	@Override
	public boolean preLeave(@NonNull OfflinePlayer player, Section targetSection, boolean fromParty) {
		return true;
	}

	@Override
	public boolean leave(@NonNull OfflinePlayer player) {
		return true;
	}
}
