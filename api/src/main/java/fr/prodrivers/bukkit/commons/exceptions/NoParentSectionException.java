package fr.prodrivers.bukkit.commons.exceptions;

/**
 * Exception thrown by the section manager when a section has no parent. Indicates that the tree is probably broken.
 */
public class NoParentSectionException extends RuntimeException {
	/**
	 * Default constructor, with no message.
	 */
	public NoParentSectionException() {
	}

	/**
	 * Build an exception with only a message.
	 *
	 * @param s Message
	 */
	public NoParentSectionException(String s) {
		super(s);
	}

	/**
	 * Build an exception with a message and another throwable as a cause.
	 *
	 * @param message Message
	 * @param cause   Cause of exception
	 */
	public NoParentSectionException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Build an exception with no message and another throwable as a cause.
	 *
	 * @param cause Cause of exception
	 */
	public NoParentSectionException(Throwable cause) {
		super(cause);
	}
}
