package fr.prodrivers.bukkit.commons.exceptions;

public class NoParentSectionException extends RuntimeException {
	public NoParentSectionException() {}

	public NoParentSectionException(String s ) {
		super( s );
	}

	public NoParentSectionException(String message, Throwable cause ) {
		super( message, cause );
	}

	public NoParentSectionException(Throwable cause ) {
		super( cause );
	}
}
