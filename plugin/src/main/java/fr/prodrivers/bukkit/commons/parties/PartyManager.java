package fr.prodrivers.bukkit.commons.parties;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class PartyManager {
	private static final HashMap<UUID, Party> parties = new HashMap<>();
	private static final HashMap<UUID, ArrayList<Party>> partyInvites = new HashMap<>();

	public static void init(JavaPlugin plugin) {
		new PartyListener(plugin);
	}

	public static boolean isInParty(UUID playerUniqueId) {
		return parties.containsKey(playerUniqueId);
	}

	public static Party getParty(UUID playerUniqueId) {
		return parties.get(playerUniqueId);
	}

	public static Iterable<Party> getParties() {
		return parties.values();
	}

	public static Party createParty(UUID ownerUniqueId) {
		final Party party = new Party(ownerUniqueId);
		parties.put(ownerUniqueId, party);
		return party;
	}

	static void addParty(UUID playerUniqueId, Party party) {
		parties.put(playerUniqueId, party);
	}

	static void removeParty(UUID playerUniqueId) {
		parties.remove(playerUniqueId);
	}

	public static void addPartyInvite(UUID invitedPlayerUniqueId, Party party) {
		if(!partyInvites.containsKey(invitedPlayerUniqueId)) {
			partyInvites.put(invitedPlayerUniqueId, new ArrayList<Party>());
		}

		partyInvites.get(invitedPlayerUniqueId).add(party);
	}

	public static boolean hasPartyInvites(UUID invitedPlayerUniqueId) {
		return partyInvites.containsKey(invitedPlayerUniqueId);
	}

	public static Iterable<Party> getPartyInvites(UUID invitedPlayerUniqueId) {
		return partyInvites.get(invitedPlayerUniqueId);
	}

	public static void removePartyInvites(UUID invitedPlayerUniqueId) {
		partyInvites.remove(invitedPlayerUniqueId);
	}
}
