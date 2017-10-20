package fr.prodrivers.bukkit.commons.exceptions;

public class IllegalSectionEnteringException extends RuntimeException {
	public IllegalSectionEnteringException() {}

	public IllegalSectionEnteringException( String s ) {
		super( s );
	}

	public IllegalSectionEnteringException( String message, Throwable cause ) {
		super( message, cause );
	}

	public IllegalSectionEnteringException( Throwable cause ) {
		super( cause );
	}
}
