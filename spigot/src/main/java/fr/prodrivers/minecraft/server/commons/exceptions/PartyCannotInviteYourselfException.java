package fr.prodrivers.minecraft.server.commons.exceptions;

/**
 * Exception thrown by the party manager when a player tries to invite itself.
 */
public class PartyCannotInviteYourselfException extends RuntimeException {
	/**
	 * Default constructor, with no message.
	 */
	public PartyCannotInviteYourselfException() {
	}

	/**
	 * Build an exception with only a message.
	 *
	 * @param s Message
	 */
	public PartyCannotInviteYourselfException(String s) {
		super(s);
	}

	/**
	 * Build an exception with a message and another throwable as a cause.
	 *
	 * @param message Message
	 * @param cause   Cause of exception
	 */
	public PartyCannotInviteYourselfException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Build an exception with no message and another throwable as a cause.
	 *
	 * @param cause Cause of exception
	 */
	public PartyCannotInviteYourselfException(Throwable cause) {
		super(cause);
	}
}