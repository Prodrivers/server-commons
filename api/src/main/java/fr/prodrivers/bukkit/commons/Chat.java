package fr.prodrivers.bukkit.commons;

import org.bukkit.command.CommandSender;

/**
 * Chat helper for Prodrivers plugins.
 * 
 * Expose simple send functions to send messages to players.
 * Handles prefix and colors directly.
 */
public class Chat {
	/**
	 * Constructor for the Chat helper for Prodrivers plugins
	 * @param name Name to display in the prefix (usually plugin's name)
	 */
	public Chat( String name ) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Generic message receiver
	 * @param receiver Receiving entity
	 * @param message Message to send
	 */
	public void send( CommandSender receiver , String message ) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Success message receiver
	 * @param receiver Receiving entity
	 * @param message Success message to send
	 */
	public void success( CommandSender receiver , String message ) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Error message receiver
	 * @param receiver Receiving entity
	 * @param message Error message to send
	 */
	public void error( CommandSender receiver , String message ) {
		throw new UnsupportedOperationException();
	}
}
