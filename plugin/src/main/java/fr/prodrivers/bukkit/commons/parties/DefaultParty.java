package fr.prodrivers.bukkit.commons.parties;

import com.google.inject.assistedinject.Assisted;
import fr.prodrivers.bukkit.commons.Log;
import fr.prodrivers.bukkit.commons.chat.Chat;
import fr.prodrivers.bukkit.commons.plugin.EMessages;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class DefaultParty extends Party {
	private UUID owner;
	private final ArrayList<UUID> players = new ArrayList<>();

	private final EMessages messages;
	private final Chat chat;
	private final PartyManager partyManager;

	@Inject
	DefaultParty(final PartyManager partyManager, final EMessages messages, final Chat chat, @Assisted final UUID ownerUniqueId) {
		this.owner = ownerUniqueId;
		this.players.add(this.owner);

		this.partyManager = partyManager;
		this.messages = messages;
		this.chat = chat;
	}

	public UUID getOwnerUniqueId() {
		return this.owner;
	}

	public ArrayList<UUID> getPlayers() {
		return this.players;
	}

	public boolean addPlayer(final UUID playerUniqueId) {
		if(playerUniqueId != null) {
			final Player player = Bukkit.getPlayer(playerUniqueId);
			if(player != null) {
				if(!this.partyManager.isInParty(playerUniqueId)) {
					this.players.add(playerUniqueId);
					this.partyManager.addParty(playerUniqueId, this);
					Player ownerPlayer = Bukkit.getPlayer(this.getOwnerUniqueId());
					if(ownerPlayer == null) {
						Log.severe("Owner " + this.getOwnerUniqueId() + "  of party is null on party add of " + player);
						return false;
					}
					this.chat.success(player, this.messages.party_you_joined_party.replaceAll("%PLAYER%", ownerPlayer.getName()), this.messages.party_prefix);
					this.tellAll(this.messages.party_player_joined_party.replaceAll("%PLAYER%", player.getName()), Collections.singletonList(playerUniqueId));
					return true;
				}
			}
		}
		return false;
	}

	public void electOwner(UUID newOwner) {
		Log.fine("Electing owner " + newOwner);
		// Tell the party who is the new leader
		final Player newOwnerPlayer = Bukkit.getPlayer(newOwner);
		if(newOwnerPlayer != null && this.players.contains(newOwner)) {
			// Set new owner
			owner = newOwner;
			// Inform other party members
			this.chat.send(newOwnerPlayer, this.messages.party_you_were_elected_as_new_leader, this.messages.party_prefix);
			this.tellAll(this.messages.party_player_elected_as_new_leader.replaceAll("%PLAYER%", newOwnerPlayer.getName()), Collections.singletonList(newOwner));
		}
	}

	public boolean removePlayer(final UUID playerUniqueId) {
		if(playerUniqueId != null) {
			if(isPartyOwner(playerUniqueId)) {
				Log.fine("Party owner leaving");
				// If there is still more than one player in the party
				if(size() > 1) {
					Log.fine("Still players in party");
					// Remove the leaving player
					this.players.remove(playerUniqueId);
					this.partyManager.removeParty(playerUniqueId);
					// Elect a player to be the new leader
					electOwner(this.players.get(0));
				} else {
					Log.fine("Disbanding party");
					disband();
				}
				return true;
			}

			if(this.players.contains(playerUniqueId)) {
				this.players.remove(playerUniqueId);
				this.partyManager.removeParty(playerUniqueId);
				final Player removedPlayer = Bukkit.getPlayer(playerUniqueId);
				if(removedPlayer != null) {
					Player ownerPlayer = Bukkit.getPlayer(this.getOwnerUniqueId());
					if(ownerPlayer == null) {
						Log.severe("Owner " + this.getOwnerUniqueId() + "  of party is null on party remove of " + removedPlayer);
						return false;
					}
					this.chat.success(removedPlayer, this.messages.party_you_left_party.replaceAll("%PLAYER%", ownerPlayer.getName()), this.messages.party_prefix);
					this.tellAll(this.messages.party_player_left_party.replaceAll("%PLAYER%", removedPlayer.getName()), Collections.singletonList(playerUniqueId));
				}
				if(size() <= 1) {
					disband();
				}
				return true;
			}
		}
		return false;
	}

	public boolean containsPlayer(final UUID playerUniqueId) {
		return playerUniqueId != null && this.players.contains(playerUniqueId);
	}

	public boolean isPartyOwner(final UUID playerUniqueId) {
		return this.owner.equals(playerUniqueId);
	}

	public int size() {
		return this.players.size();
	}

	public void disband() {
		this.tellAll(this.messages.party_disbanded);
		for(UUID p : this.players)
			this.partyManager.removeParty(p);
		//this.players.clear();
	}

	public void chat(Player player, String message) {
		if(message.trim().length() > 0) {
			String format = this.messages.party_chat_format;
			message = format.replaceAll("%PLAYER%", player.getDisplayName()).replaceAll("%MESSAGE%", message);
			for(final UUID partyMember : this.getPlayers()) {
				final Player partyMemberPlayer = Bukkit.getPlayer(partyMember);
				if(partyMemberPlayer != null) {
					partyMemberPlayer.sendMessage(message);
				}
			}
		}
	}

	private void tellAll(final String msg) {
		for(final UUID partyMember : this.getPlayers()) {
			final Player partyMemberPlayer = Bukkit.getPlayer(partyMember);
			if(partyMemberPlayer != null) {
				this.chat.send(partyMemberPlayer, msg, this.messages.party_prefix);
			}
		}
	}

	private void tellAll(final String msg, final List<UUID> excluded) {
		for(final UUID partyMember : this.getPlayers()) {
			if(!excluded.contains(partyMember)) {
				final Player partyMemberPlayer = Bukkit.getPlayer(partyMember);
				if(partyMemberPlayer != null) {
					this.chat.send(partyMemberPlayer, msg, this.messages.party_prefix);
				}
			}
		}
	}
}
