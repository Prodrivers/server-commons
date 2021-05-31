package fr.prodrivers.bukkit.commons.exceptions;

public class NoRegisteredParentSectionException extends RuntimeException {
	public NoRegisteredParentSectionException() {
	}

	public NoRegisteredParentSectionException(String s) {
		super(s);
	}

	public NoRegisteredParentSectionException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoRegisteredParentSectionException(Throwable cause) {
		super(cause);
	}
}
