package fr.prodrivers.bukkit.commons.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Event fired when player open its own inventory.
 * 
 * ProdriversCommons offers a convenient way to be informed of a player opening its own inventory, which is normally not possible.
 * As it is using an achievement trick, they are not available on servers who's ProdriversCommons implementation offers this feature.
 * This event is not fired immediately as a significant delay happens between the moment the player opens it and the moment the event is fired.
 */
public final class PlayerOpenOwnInventoryEvent extends Event implements Cancellable {
	private static final HandlerList handlers = new HandlerList();
	private Player player;
	private boolean cancelled = false;

	public PlayerOpenOwnInventoryEvent( Player player ) {
		this.player = player;
	}

	/**
	 * Get the concerned player
	 * @return Concerned player
	 */
	public final Player getPlayer() {
		return this.player;
	}

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	/**
	 * Get if the event is cancelled
	 * @return {@code true} Event is cancelled
	 */
	public boolean isCancelled() {
		return cancelled;
	}

	/**
	 * Set that the event is cancelled
	 * @param cancelled Cancellation state
	 */
	public void setCancelled( boolean cancelled ) {
		this.cancelled = cancelled;
	}
}