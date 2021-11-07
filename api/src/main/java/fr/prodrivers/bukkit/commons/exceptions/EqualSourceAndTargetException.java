package fr.prodrivers.bukkit.commons.exceptions;

/**
 * Exception thrown by the party or section manager when the source is equal to target.
 */
public class EqualSourceAndTargetException extends RuntimeException {
	/**
	 * Default constructor, with no message.
	 */
	public EqualSourceAndTargetException() {
	}

	/**
	 * Build an exception with only a message.
	 *
	 * @param s Message
	 */
	public EqualSourceAndTargetException(String s) {
		super(s);
	}

	/**
	 * Build an exception with a message and another throwable as a cause.
	 *
	 * @param message Message
	 * @param cause   Cause of exception
	 */
	public EqualSourceAndTargetException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Build an exception with no message and another throwable as a cause.
	 *
	 * @param cause Cause of exception
	 */
	public EqualSourceAndTargetException(Throwable cause) {
		super(cause);
	}
}