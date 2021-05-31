package fr.prodrivers.bukkit.commons.exceptions;

public class NotPartyOwnerException extends RuntimeException {
	public NotPartyOwnerException() {
	}

	public NotPartyOwnerException(String s) {
		super(s);
	}

	public NotPartyOwnerException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotPartyOwnerException(Throwable cause) {
		super(cause);
	}
}