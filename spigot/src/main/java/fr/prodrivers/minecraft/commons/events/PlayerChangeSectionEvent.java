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
	private static final HandlerList handlers = new HandlerList();

	private boolean cancelled;

	private final Player player;
	private final Section leftSection;
	private final Section targetSection;

	/**
	 * Constructs a new event.
	 *
	 * @param player        Player that is moving
	 * @param leftSection   Left section
	 * @param targetSection Target section
	 */
	public PlayerChangeSectionEvent(Player player, Section leftSection, Section targetSection) {
		this.player = player;
		this.leftSection = leftSection;
		this.targetSection = targetSection;
	}

	/**
	 * Get the player that is moving.
	 *
	 * @return Player that is moving
	 */
	public Player getPlayer() {
		return this.player;
	}

	/**
	 * Get the left section.
	 *
	 * @return Left section
	 */
	public Section getLeftSection() {
		return this.leftSection;
	}

	/**
	 * Get the target section.
	 *
	 * @return Target section
	 */
	public Section getTargetSection() {
		return this.targetSection;
	}

	@Override
	public boolean isCancelled() {
		return this.cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	@Override
	public @NonNull HandlerList getHandlers() {
		return handlers;
	}

	/**
	 * Return handlers of this event. Mandated by Bukkit.
	 *
	 * @return Handler list
	 */
	public static HandlerList getHandlerList() {
		return handlers;
	}
}
