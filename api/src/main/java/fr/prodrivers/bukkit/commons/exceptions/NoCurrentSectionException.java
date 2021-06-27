package fr.prodrivers.bukkit.commons.exceptions;

/**
 * Exception thrown when a player has no current section, ie. is not registered against Prodrivers Commons
 * infrastructure.
 */
public class NoCurrentSectionException extends RuntimeException {
	/**
	 * Default constructor, with no message.
	 */
	public NoCurrentSectionException() {
	}

	/**
	 * Build an exception with only a message.
	 *
	 * @param s Message
	 */
	public NoCurrentSectionException(String s) {
		super(s);
	}

	/**
	 * Build an exception with a message and another throwable as a cause.
	 *
	 * @param message Message
	 * @param cause   Cause of exception
	 */
	public NoCurrentSectionException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Build an exception with no message and another throwable as a cause.
	 *
	 * @param cause Cause of exception
	 */
	public NoCurrentSectionException(Throwable cause) {
		super(cause);
	}
}
