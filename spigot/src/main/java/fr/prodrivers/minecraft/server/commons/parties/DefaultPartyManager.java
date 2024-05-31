package fr.prodrivers.minecraft.server.commons.parties;

import fr.prodrivers.minecraft.server.commons.Log;
import fr.prodrivers.minecraft.server.commons.exceptions.PartyCannotInviteYourselfException;
import fr.prodrivers.minecraft.server.commons.exceptions.PlayerNotConnectedException;
import fr.prodrivers.minecraft.server.commons.exceptions.PlayerNotInvitedToParty;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
public class DefaultPartyManager implements PartyManager {
	private final Logger logger;

	private final HashMap<UUID, Party> parties = new HashMap<>();
	private final HashMap<UUID, ArrayList<Party>> partyInvites = new HashMap<>();

	private final PartyListener partyListener;
	private final DefaultPartyFactory defaultPartyFactory;

	@Inject
	public DefaultPartyManager(Logger logger, Plugin plugin, DefaultPartyFactory defaultPartyFactory) {
		this.logger = logger;
		this.partyListener = new PartyListener(plugin, this);
		this.defaultPartyFactory = defaultPartyFactory;
	}

	@Override
	public boolean isInParty(@NonNull final UUID playerUniqueId) {
		return parties.containsKey(playerUniqueId);
	}

	@Override
	public Party getParty(@NonNull final UUID playerUniqueId) {
		return parties.get(playerUniqueId);
	}

	@Override
	public Iterable<Party> getParties() {
		return parties.values();
	}

	@Override
	public void addPartyInvite(@NonNull final UUID invitedPlayerUniqueId, @NonNull Party party) {
		if(!partyInvites.containsKey(invitedPlayerUniqueId)) {
			partyInvites.put(invitedPlayerUniqueId, new ArrayList<>());
		}

		partyInvites.get(invitedPlayerUniqueId).add(party);
	}

	@Override
	public boolean hasPartyInvites(@NonNull final UUID invitedPlayerUniqueId) {
		return partyInvites.containsKey(invitedPlayerUniqueId);
	}

	@Override
	public Iterable<Party> getPartyInvites(@NonNull final UUID invitedPlayerUniqueId) {
		return partyInvites.get(invitedPlayerUniqueId);
	}

	@Override
	public void removePartyInvites(@NonNull final UUID invitedPlayerUniqueId) {
		partyInvites.remove(invitedPlayerUniqueId);
	}

	@Override
	public Party createParty(@NonNull final UUID ownerUniqueId) {
		final Party party = this.defaultPartyFactory.create(ownerUniqueId);
		this.parties.put(ownerUniqueId, party);
		return party;
	}

	@Override
	public boolean addToParty(@NonNull final Party party, @NonNull final UUID playerToAddUniqueId) {
		final Player playerToAdd = Bukkit.getPlayer(playerToAddUniqueId);
		if(playerToAdd != null) {
			if(!isInParty(playerToAddUniqueId)) {
				Player ownerPlayer = Bukkit.getPlayer(party.getOwnerUniqueId());
				if(ownerPlayer == null) {
					this.logger.severe("Owner " + party.getOwnerUniqueId() + "  of party is null on party add of " + playerToAdd);
					return false;
				}
				this.parties.put(playerToAddUniqueId, party);
				party.registerPlayer(playerToAddUniqueId);

				party.send(playerToAddUniqueId, PartyMessage.JOINED_YOU, ownerPlayer.getName());
				party.broadcast(PartyMessage.JOINED_OTHERS, Collections.singletonList(playerToAddUniqueId), playerToAdd.getName());
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean removeFromParty(@NonNull final UUID playerToRemoveUniqueId) {
		return removeFromParty(playerToRemoveUniqueId, false);
	}

	public boolean removeFromParty(@NonNull final UUID playerToRemoveUniqueId, boolean fromDisband) {
		Party party = getParty(playerToRemoveUniqueId);
		if(party == null) {
			return false;
		}

		if(fromDisband) {
			// Remove the leaving player
			party.unregisterPlayer(playerToRemoveUniqueId);
			this.parties.remove(playerToRemoveUniqueId);

			return true;
		}

		if(party.isPartyOwner(playerToRemoveUniqueId)) {
			this.logger.fine("Party owner leaving");
			// If there is still more than one player in the party
			if(party.size() > 1) {
				this.logger.fine("Still players in party");
				// Remove the leaving player
				party.unregisterPlayer(playerToRemoveUniqueId);
				this.parties.remove(playerToRemoveUniqueId);
				// Elect a player to be the new leader
				party.assignOwner(party.getPlayers().get(0));
			} else {
				this.logger.fine("Disbanding party");
				disband(party);
			}
			return true;
		}

		if(party.containsPlayer(playerToRemoveUniqueId)) {
			party.unregisterPlayer(playerToRemoveUniqueId);
			this.parties.remove(playerToRemoveUniqueId);
			final Player playerToRemove = Bukkit.getPlayer(playerToRemoveUniqueId);
			if(playerToRemove != null) {
				Player ownerPlayer = Bukkit.getPlayer(party.getOwnerUniqueId());
				if(ownerPlayer == null) {
					this.logger.severe("Owner " + party.getOwnerUniqueId() + "  of party is null on party remove of " + playerToRemove);
					return false;
				}

				party.send(playerToRemoveUniqueId, PartyMessage.LEFT_YOU, ownerPlayer.getName());
				party.broadcast(PartyMessage.LEFT_OTHERS, Collections.singletonList(playerToRemoveUniqueId), playerToRemove.getName());
			}
			if(party.size() <= 1) {
				disband(party);
			}
			return true;
		}

		return false;
	}

	@Override
	public boolean invite(@NonNull final UUID inviterPlayerUniqueId, @NonNull final UUID invitedPlayerUniqueId) throws PlayerNotConnectedException, PartyCannotInviteYourselfException {
		if(inviterPlayerUniqueId.equals(invitedPlayerUniqueId)) {
			throw new PartyCannotInviteYourselfException(inviterPlayerUniqueId + "tried to invite itself.");
		}

		Player inviter = Bukkit.getPlayer(inviterPlayerUniqueId);
		if(inviter == null) {
			throw new PlayerNotConnectedException("Inviter player is not connected.");
		}

		Player invited = Bukkit.getPlayer(invitedPlayerUniqueId);
		if(invited == null) {
			throw new PlayerNotConnectedException("Invited player is not connected.");
		}

		if(!isInParty(invitedPlayerUniqueId)) {
			Party inviterParty = getParty(inviterPlayerUniqueId);
			if(inviterParty == null) {
				inviterParty = createParty(inviterPlayerUniqueId);
			} else {
				if(!inviterParty.isPartyOwner(inviterPlayerUniqueId)) {
					inviterParty.send(inviterPlayerUniqueId, PartyMessage.NOT_PARTY_OWNER_YOU);
					return false;
				}
			}
			addPartyInvite(invitedPlayerUniqueId, inviterParty);

			inviterParty.send(inviterPlayerUniqueId, PartyMessage.PLAYER_INVITED_INVITER, invited.getName());
			inviterParty.send(invitedPlayerUniqueId, PartyMessage.PLAYER_INVITED_YOU, inviter.getName());

			return true;
		} else {
			Party invitedParty = getParty(invitedPlayerUniqueId);
			invitedParty.send(inviterPlayerUniqueId, PartyMessage.PLAYER_IS_IN_PARTY, invited.getName());
		}

		return false;
	}

	public void accept(@NonNull final UUID inviterPlayerUniqueId, @NonNull final UUID invitedPlayerUniqueId) throws PlayerNotConnectedException, PlayerNotInvitedToParty {
		Player inviter = Bukkit.getPlayer(inviterPlayerUniqueId);
		if(inviter == null || !inviter.isOnline()) {
			throw new PlayerNotConnectedException("Inviter player is not connected.");
		}

		if(!hasPartyInvites(invitedPlayerUniqueId)) {
			throw new PlayerNotInvitedToParty(invitedPlayerUniqueId + " not invited to " + inviter.getName() + "'s party.");
		}

		if(isInParty(invitedPlayerUniqueId)) {
			Party invitedParty = getParty(invitedPlayerUniqueId);
			invitedParty.send(invitedPlayerUniqueId, PartyMessage.PARTY_LEAVE_BEFORE_JOINING_ANOTHER);
			return;
		}

		Party inviterParty = null;
		for(final Party party : getPartyInvites(invitedPlayerUniqueId)) {
			if(party.getOwnerUniqueId().equals(inviter.getUniqueId())) {
				inviterParty = party;
				break;
			}
		}
		if(inviterParty != null) {
			addToParty(inviterParty, invitedPlayerUniqueId);
			removePartyInvites(invitedPlayerUniqueId);
		} else {
			throw new PlayerNotInvitedToParty(invitedPlayerUniqueId + " not invited to " + inviter.getName() + "'s party.");
		}
	}

	public void disband(@NonNull final Party party) {
		party.broadcast(PartyMessage.DISBANDED);
		for(UUID p : new ArrayList<>(party.getPlayers())) {
			removeFromParty(p, true);
		}
		party.clear();
	}

	public boolean assignOwner(@NonNull final Party party, @NonNull final UUID newOwnerUniqueId) {
		this.logger.fine("Assigning owner " + newOwnerUniqueId);
		// Tell the party who is the new leader
		final Player newOwnerPlayer = Bukkit.getPlayer(newOwnerUniqueId);
		if(newOwnerPlayer != null && party.containsPlayer(newOwnerUniqueId)) {
			// Set new owner
			party.assignOwner(newOwnerUniqueId);
			// Inform other party members
			party.send(newOwnerUniqueId, PartyMessage.PARTY_LEADER_ASSIGNED_YOU);
			party.broadcast(PartyMessage.PARTY_LEADER_ASSIGNED_OTHERS, Collections.singletonList(newOwnerUniqueId), newOwnerPlayer.getName());
		}
		return false;
	}
}
