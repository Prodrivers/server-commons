package fr.prodrivers.minecraft.server.commons.exceptions;

/**
 * Exception thrown by the section manager when a player should not enter a section., when moving from one section to
 * another.
 */
public class IllegalSectionEnteringException extends RuntimeException {
	/**
	 * Default constructor, with no message.
	 */
	public IllegalSectionEnteringException() {
	}

	/**
	 * Build an exception with only a message.
	 *
	 * @param s Message
	 */
	public IllegalSectionEnteringException(String s) {
		super(s);
	}

	/**
	 * Build an exception with a message and another throwable as a cause.
	 *
	 * @param message Message
	 * @param cause   Cause of exception
	 */
	public IllegalSectionEnteringException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Build an exception with no message and another throwable as a cause.
	 *
	 * @param cause Cause of exception
	 */
	public IllegalSectionEnteringException(Throwable cause) {
		super(cause);
	}
}
