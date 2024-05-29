package fr.prodrivers.minecraft.server.commons.exceptions;

/**
 * Exception thrown by the party or section manager when an invited player is already in a party.
 */
public class PlayerNotInvitedToParty extends RuntimeException {
	/**
	 * Default constructor, with no message.
	 */
	public PlayerNotInvitedToParty() {
	}

	/**
	 * Build an exception with only a message.
	 *
	 * @param s Message
	 */
	public PlayerNotInvitedToParty(String s) {
		super(s);
	}

	/**
	 * Build an exception with a message and another throwable as a cause.
	 *
	 * @param message Message
	 * @param cause   Cause of exception
	 */
	public PlayerNotInvitedToParty(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Build an exception with no message and another throwable as a cause.
	 *
	 * @param cause Cause of exception
	 */
	public PlayerNotInvitedToParty(Throwable cause) {
		super(cause);
	}
}