package fr.prodrivers.minecraft.server.commons.exceptions;

/**
 * Exception thrown when an illegal action with a section occurred.
 */
public class InvalidSectionException extends RuntimeException {
	/**
	 * Default constructor, with no message.
	 */
	public InvalidSectionException() {
	}

	/**
	 * Build an exception with only a message.
	 *
	 * @param s Message
	 */
	public InvalidSectionException(String s) {
		super(s);
	}

	/**
	 * Build an exception with a message and another throwable as a cause.
	 *
	 * @param message Message
	 * @param cause   Cause of exception
	 */
	public InvalidSectionException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Build an exception with no message and another throwable as a cause.
	 *
	 * @param cause Cause of exception
	 */
	public InvalidSectionException(Throwable cause) {
		super(cause);
	}
}
