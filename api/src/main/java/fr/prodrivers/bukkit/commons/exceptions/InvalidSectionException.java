package fr.prodrivers.bukkit.commons.exceptions;

public class InvalidSectionException extends RuntimeException {
	public InvalidSectionException() {}

	public InvalidSectionException( String s ) {
		super( s );
	}

	public InvalidSectionException( String message, Throwable cause ) {
		super( message, cause );
	}

	public InvalidSectionException( Throwable cause ) {
		super( cause );
	}
}
