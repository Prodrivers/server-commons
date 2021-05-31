package fr.prodrivers.bukkit.commons.storage;

import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.CreateCollectionOptions;
import com.mongodb.client.model.Filters;
import fr.prodrivers.bukkit.commons.plugin.Main;
import org.bson.Document;
import org.bson.UuidRepresentation;
import org.bson.codecs.UuidCodec;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class StorageProvider {
	private static MongoClient client;
	private static MongoDatabase database;

	public static void init() {
		try {
			if(Main.getConfiguration().storage_uri == null) {
				Main.logger.info("Storage provider is disabled, as no connection URI was provided.");
				return;
			}

			MongoClientURI uri = new MongoClientURI(Main.getConfiguration().storage_uri);

			MongoClientOptions.Builder builder = MongoClientOptions.builder(uri.getOptions());
			CodecRegistry codecRegistry = CodecRegistries.fromRegistries(CodecRegistries.fromCodecs(new UuidCodec(UuidRepresentation.STANDARD)), MongoClient.getDefaultCodecRegistry());
			builder.codecRegistry(codecRegistry);
			MongoClientOptions options = builder.build();

			List<ServerAddress> hosts = uri.getHosts().parallelStream().map(ServerAddress::new).collect(Collectors.toList());

			List<MongoCredential> creds = new ArrayList<>();
			if(uri.getUsername() != null && uri.getPassword() != null && uri.getDatabase() != null) {
				creds.add(MongoCredential.createCredential(uri.getUsername(), uri.getDatabase(), uri.getPassword()));
			}

			client = new MongoClient(hosts, creds, options);
			if(uri.getDatabase() == null || uri.getDatabase().isEmpty()) {
				Main.logger.warning("Storage provider is not available: database name not provided in connection URI");
				return;
			}

			database = client.getDatabase(uri.getDatabase());
			assert database != null;
			initDB();
		} catch(AssertionError | MongoException ex) {
			database = null;
			Main.logger.warning("Storage provider is not available: " + ex.getLocalizedMessage());
			Main.logger.warning("Please check your storage connection URI and credentials.");
		}
	}

	private static void initDB() {
		if(!database.listCollectionNames().into(new ArrayList<>()).contains(Main.getConfiguration().storage_playerCollection)) {
			database.createCollection(Main.getConfiguration().storage_playerCollection, new CreateCollectionOptions());
		}
	}

	public static MongoDatabase getStorage() {
		return database;
	}

	public static MongoCollection<Document> getPlayersCollection() {
		try {
			return (database == null ? null : database.getCollection(Main.getConfiguration().storage_playerCollection));
		} catch(MongoException ex) {
			Main.logger.severe("Player collection retrieval went wrong: " + ex.getLocalizedMessage());
		}
		return null;
	}

	private static Bson getPlayerFilter(UUID playerUniqueId) {
		return Filters.eq("_id", playerUniqueId);
	}

	public static Document getPlayer(UUID playerUniqueId) {
		MongoCollection<Document> pCol = getPlayersCollection();
		if(pCol == null)
			return null;

		try {
			FindIterable<Document> results = pCol.find(getPlayerFilter(playerUniqueId));

			if(results.first() == null) {
				return createPlayer(playerUniqueId);
			}

			return results.first();
		} catch(MongoException ex) {
			Main.logger.severe("Player retrieval in storage went wrong: " + ex.getLocalizedMessage());
		}

		return null;
	}

	public static Document createPlayer(UUID playerUniqueId) {
		MongoCollection<Document> pCol = getPlayersCollection();
		if(pCol == null)
			return null;

		try {
			Document doc = new Document().append("_id", playerUniqueId);
			pCol.insertOne(doc);
			return doc;
		} catch(MongoException ex) {
			Main.logger.severe("Player creation in storage went wrong: " + ex.getLocalizedMessage());
		}

		return null;
	}

	public static boolean updatePlayer(UUID playerUniqueId, Bson updates) {
		MongoCollection<Document> pCol = getPlayersCollection();
		if(pCol == null)
			return false;

		try {
			pCol.updateOne(getPlayerFilter(playerUniqueId), updates);
			return true;
		} catch(MongoException ex) {
			Main.logger.severe("Player retrieval in storage went wrong: " + ex.getLocalizedMessage());
		}

		return false;
	}

	public static boolean replacePlayer(UUID playerUniqueId, Document replaceDocument) {
		MongoCollection<Document> pCol = getPlayersCollection();
		if(pCol == null)
			return false;

		try {
			pCol.replaceOne(getPlayerFilter(playerUniqueId), replaceDocument);
			return true;
		} catch(MongoException ex) {
			Main.logger.severe("Player retrieval in storage went wrong: " + ex.getLocalizedMessage());
		}

		return false;
	}

	public static void close() throws IOException {
		try {
			if(client != null)
				client.close();
		} catch(Exception ex) {
			throw new IOException(ex);
		}
	}
}
