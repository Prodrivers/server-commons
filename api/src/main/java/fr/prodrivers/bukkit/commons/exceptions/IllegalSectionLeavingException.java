package fr.prodrivers.bukkit.commons.exceptions;

/**
 * Exception thrown by the section manager when a player should not leave a section, when moving from one section to
 * another.
 */
public class IllegalSectionLeavingException extends RuntimeException {
	/**
	 * Default constructor, with no message.
	 */
	public IllegalSectionLeavingException() {
	}

	/**
	 * Build an exception with only a message.
	 *
	 * @param s Message
	 */
	public IllegalSectionLeavingException(String s) {
		super(s);
	}

	/**
	 * Build an exception with a message and another throwable as a cause.
	 *
	 * @param message Message
	 * @param cause   Cause of exception
	 */
	public IllegalSectionLeavingException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Build an exception with no message and another throwable as a cause.
	 *
	 * @param cause Cause of exception
	 */
	public IllegalSectionLeavingException(Throwable cause) {
		super(cause);
	}
}
