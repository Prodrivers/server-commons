package fr.prodrivers.bukkit.commons.parties;

import com.google.inject.Injector;
import org.bukkit.plugin.Plugin;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

@Singleton
public class DefaultPartyManager extends PartyManager {
	private final HashMap<UUID, Party> parties = new HashMap<>();
	private final HashMap<UUID, ArrayList<Party>> partyInvites = new HashMap<>();

	private final Injector injector;

	private final PartyListener partyListener;
	private final DefaultPartyFactory defaultPartyFactory;

	@Inject
	public DefaultPartyManager(Injector injector, Plugin plugin, DefaultPartyFactory defaultPartyFactory) {
		this.injector = injector;
		this.partyListener = new PartyListener(plugin, this);
		this.defaultPartyFactory = defaultPartyFactory;
	}

	public boolean isInParty(UUID playerUniqueId) {
		return parties.containsKey(playerUniqueId);
	}

	public Party getParty(UUID playerUniqueId) {
		return parties.get(playerUniqueId);
	}

	public Iterable<Party> getParties() {
		return parties.values();
	}

	public Party createParty(UUID ownerUniqueId) {
		final Party party = this.defaultPartyFactory.create(ownerUniqueId);
		parties.put(ownerUniqueId, party);
		return party;
	}

	protected void addParty(UUID playerUniqueId, Party party) {
		parties.put(playerUniqueId, party);
	}

	protected void removeParty(UUID playerUniqueId) {
		parties.remove(playerUniqueId);
	}

	public void addPartyInvite(UUID invitedPlayerUniqueId, Party party) {
		if(!partyInvites.containsKey(invitedPlayerUniqueId)) {
			partyInvites.put(invitedPlayerUniqueId, new ArrayList<>());
		}

		partyInvites.get(invitedPlayerUniqueId).add(party);
	}

	public boolean hasPartyInvites(UUID invitedPlayerUniqueId) {
		return partyInvites.containsKey(invitedPlayerUniqueId);
	}

	public Iterable<Party> getPartyInvites(UUID invitedPlayerUniqueId) {
		return partyInvites.get(invitedPlayerUniqueId);
	}

	public void removePartyInvites(UUID invitedPlayerUniqueId) {
		partyInvites.remove(invitedPlayerUniqueId);
	}
}
