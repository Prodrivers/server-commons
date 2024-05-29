package fr.prodrivers.minecraft.commons.exceptions;

/**
 * Exception thrown by the party or section manager when a specified player is not connected.
 */
public class PlayerNotConnectedException extends RuntimeException {
	/**
	 * Default constructor, with no message.
	 */
	public PlayerNotConnectedException() {
	}

	/**
	 * Build an exception with only a message.
	 *
	 * @param s Message
	 */
	public PlayerNotConnectedException(String s) {
		super(s);
	}

	/**
	 * Build an exception with a message and another throwable as a cause.
	 *
	 * @param message Message
	 * @param cause   Cause of exception
	 */
	public PlayerNotConnectedException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Build an exception with no message and another throwable as a cause.
	 *
	 * @param cause Cause of exception
	 */
	public PlayerNotConnectedException(Throwable cause) {
		super(cause);
	}
}