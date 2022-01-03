package fr.prodrivers.bukkit.commons.events;

import fr.prodrivers.bukkit.commons.sections.Section;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.checkerframework.checker.nullness.qual.NonNull;

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

	public Player getPlayer() {
		return this.player;
	}

	public Section getLeftSection() {
		return this.leftSection;
	}

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

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
