package fr.prodrivers.bukkit.commons.exceptions;

/**
 * Exception thrown by the section manager when asection reports to have custom UI but does not implements it.
 */
public class InvalidUIException extends RuntimeException {
	/**
	 * Default constructor, with no message.
	 */
	public InvalidUIException() {
	}

	/**
	 * Build an exception with only a message.
	 *
	 * @param s Message
	 */
	public InvalidUIException(String s) {
		super(s);
	}

	/**
	 * Build an exception with a message and another throwable as a cause.
	 *
	 * @param message Message
	 * @param cause   Cause of exception
	 */
	public InvalidUIException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Build an exception with no message and another throwable as a cause.
	 *
	 * @param cause Cause of exception
	 */
	public InvalidUIException(Throwable cause) {
		super(cause);
	}
}
