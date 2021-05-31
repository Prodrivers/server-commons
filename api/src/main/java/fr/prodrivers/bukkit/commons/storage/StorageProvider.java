package fr.prodrivers.bukkit.commons.storage;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.UUID;

/**
 * Persistent storage provider for Prodrivers plugins.
 * <p>
 * StorageProvider allows Prodrivers plugins to store persistent data across servers in a document format, backed by MongoDB.
 * Its primary feature is to allow plugins to store players data across servers.
 * <p>
 * StorageProvider exposes a Java MongoDB driver database, as well as players-related storage primitives.
 * Initialization is handled by the plugin.
 * It is strongly encouraged to use player primitives when dealing with players data, as they take care of document initialization.
 * <p>
 * StorageProvider is an optional part of the plugin, meaning that the result of its methods, on top of MongoDB internal quirks and wrong queries, is not guaranteed to be correct.
 * One must check against null each results returned by StorageProvider.
 */
public class StorageProvider {
	/**
	 * Gets the used MongoDatabase instance.
	 *
	 * @return Mongo database instance or null
	 */
	public static MongoDatabase getStorage() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Gets the players collection from database.
	 *
	 * @return Players document collection or null
	 */
	public static MongoCollection<Document> getPlayersCollection() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Get a player's document.
	 *
	 * @param playerUniqueId Player's Unique ID
	 * @return Player's document or null
	 */
	public static Document getPlayer(UUID playerUniqueId) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Creates a document for a player in the database, and returns it.
	 * There is little need to call it directly, as getPlayer automatically creates it if it does not exists.
	 *
	 * @param playerUniqueId Player's Unique ID
	 * @return Player's document or null
	 */
	public static Document createPlayer(UUID playerUniqueId) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Updates a player's document with provided BSON values.
	 *
	 * @param playerUniqueId Player's Unique ID
	 * @param updates        BSON values to insert/update/delete in the document
	 * @return {@code true} if the update was successful
	 */
	public static boolean updatePlayer(UUID playerUniqueId, Bson updates) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Replaces a player's document with another
	 *
	 * @param playerUniqueId  Player's Unique ID
	 * @param replaceDocument Document
	 * @return {@code true} if the repalce was successful
	 */
	public static boolean replacePlayer(UUID playerUniqueId, Document replaceDocument) {
		throw new UnsupportedOperationException();
	}
}
