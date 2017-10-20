package fr.prodrivers.bukkit.commons.exceptions;

public class NoNextSectionException extends RuntimeException {
	public NoNextSectionException() {}

	public NoNextSectionException( String s ) {
		super( s );
	}

	public NoNextSectionException( String message, Throwable cause ) {
		super( message, cause );
	}

	public NoNextSectionException( Throwable cause ) {
		super( cause );
	}
}
