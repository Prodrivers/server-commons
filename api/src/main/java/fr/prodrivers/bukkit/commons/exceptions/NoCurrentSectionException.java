package fr.prodrivers.bukkit.commons.exceptions;

public class NoCurrentSectionException extends RuntimeException {
	public NoCurrentSectionException() {}

	public NoCurrentSectionException( String s ) {
		super( s );
	}

	public NoCurrentSectionException( String message, Throwable cause ) {
		super( message, cause );
	}

	public NoCurrentSectionException( Throwable cause ) {
		super( cause );
	}
}
