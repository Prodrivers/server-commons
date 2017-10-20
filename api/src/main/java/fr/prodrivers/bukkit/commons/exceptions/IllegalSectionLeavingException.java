package fr.prodrivers.bukkit.commons.exceptions;

public class IllegalSectionLeavingException extends RuntimeException {
	public IllegalSectionLeavingException() {}

	public IllegalSectionLeavingException( String s ) {
		super( s );
	}

	public IllegalSectionLeavingException( String message, Throwable cause ) {
		super( message, cause );
	}

	public IllegalSectionLeavingException( Throwable cause ) {
		super( cause );
	}
}
