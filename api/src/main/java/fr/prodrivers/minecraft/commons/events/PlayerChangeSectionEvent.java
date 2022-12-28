package fr.prodrivers.minecraft.commons.events;

import fr.prodrivers.minecraft.commons.sections.Section;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Event triggered when a player moves from one section to another, before the moving is actually done.
 */
public class PlayerChangeSectionEvent extends Event implements Cancellable {
	/**
	 * Make class non-instantiable
	 */
	private PlayerChangeSectionEvent() {
	}

	/**
	 * Get the player that is moving.
	 *
	 * @return Player that is moving
	 */
	public Player getPlayer() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Get the left section.
	 *
	 * @return Left section
	 */
	public Section getLeftSection() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Get the target section.
	 *
	 * @return Target section
	 */
	public Section getTargetSection() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isCancelled() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setCancelled(boolean cancelled) {
		throw new UnsupportedOperationException();
	}

	@Override
	public @NonNull HandlerList getHandlers() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Return handlers of this event. Mandated by Bukkit.
	 *
	 * @return Handler list
	 */
	public static HandlerList getHandlerList() {
		throw new UnsupportedOperationException();
	}
}
