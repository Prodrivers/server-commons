package fr.prodrivers.bukkit.commons.exceptions;

/**
 * Exception thrown by the party or section manager when a player wants to do an action only authorized to the party
 * owner.
 */
public class NotPartyOwnerException extends RuntimeException {
	/**
	 * Default constructor, with no message.
	 */
	public NotPartyOwnerException() {
	}

	/**
	 * Build an exception with only a message.
	 *
	 * @param s Message
	 */
	public NotPartyOwnerException(String s) {
		super(s);
	}

	/**
	 * Build an exception with a message and another throwable as a cause.
	 *
	 * @param message Message
	 * @param cause   Cause of exception
	 */
	public NotPartyOwnerException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Build an exception with no message and another throwable as a cause.
	 *
	 * @param cause Cause of exception
	 */
	public NotPartyOwnerException(Throwable cause) {
		super(cause);
	}
}