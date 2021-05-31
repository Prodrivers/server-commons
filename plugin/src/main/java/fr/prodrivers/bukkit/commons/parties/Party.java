package fr.prodrivers.bukkit.commons.parties;

import fr.prodrivers.bukkit.commons.Chat;
import fr.prodrivers.bukkit.commons.plugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class Party {
	private UUID owner;
	private final ArrayList<UUID> players = new ArrayList<>();

	Party(final UUID ownerUniqueId) {
		this.owner = ownerUniqueId;
		this.players.add(this.owner);
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
				if(!PartyManager.isInParty(playerUniqueId)) {
					this.players.add(playerUniqueId);
					PartyManager.addParty(playerUniqueId, this);
					Player ownerPlayer = Bukkit.getPlayer(this.getOwnerUniqueId());
					if(ownerPlayer == null) {
						Main.logger.severe("Owner " + this.getOwnerUniqueId() + "  of party is null on party add of " + player);
						return false;
					}
					Main.getChat().success(player, Main.getMessages().party_you_joined_party.replaceAll("%PLAYER%", ownerPlayer.getName()), Main.getMessages().party_prefix);
					this.tellAll(Main.getMessages().party_player_joined_party.replaceAll("%PLAYER%", player.getName()), Collections.singletonList(playerUniqueId));
					return true;
				}
			}
		}
		return false;
	}

	public void electOwner(UUID newOwner) {
		Main.logger.fine("Electing owner " + newOwner);
		// Tell the party who is the new leader
		final Player newOwnerPlayer = Bukkit.getPlayer(newOwner);
		if(newOwnerPlayer != null && this.players.contains(newOwner)) {
			// Set new owner
			owner = newOwner;
			// Inform other party members
			Main.getChat().send(newOwnerPlayer, Main.getMessages().party_you_were_elected_as_new_leader, Main.getMessages().party_prefix);
			this.tellAll(Main.getMessages().party_player_elected_as_new_leader.replaceAll("%PLAYER%", newOwnerPlayer.getName()), Collections.singletonList(newOwner));
		}
	}

	public boolean removePlayer(final UUID playerUniqueId) {
		if(playerUniqueId != null) {
			if(isPartyOwner(playerUniqueId)) {
				Main.logger.fine("Party owner leaving");
				// If there is still more than one player in the party
				if(size() > 1) {
					Main.logger.fine("Still players in party");
					// Remove the leaving player
					this.players.remove(playerUniqueId);
					PartyManager.removeParty(playerUniqueId);
					// Elect a player to be the new leader
					electOwner(this.players.get(0));
				} else {
					Main.logger.fine("Disbanding party");
					disband();
				}
				return true;
			}

			if(this.players.contains(playerUniqueId)) {
				this.players.remove(playerUniqueId);
				PartyManager.removeParty(playerUniqueId);
				final Player removedPlayer = Bukkit.getPlayer(playerUniqueId);
				if(removedPlayer != null) {
					Player ownerPlayer = Bukkit.getPlayer(this.getOwnerUniqueId());
					if(ownerPlayer == null) {
						Main.logger.severe("Owner " + this.getOwnerUniqueId() + "  of party is null on party remove of " + removedPlayer);
						return false;
					}
					Main.getChat().success(removedPlayer, Main.getMessages().party_you_left_party.replaceAll("%PLAYER%", ownerPlayer.getName()), Main.getMessages().party_prefix);
					this.tellAll(Main.getMessages().party_player_left_party.replaceAll("%PLAYER%", removedPlayer.getName()), Collections.singletonList(playerUniqueId));
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
		this.tellAll(Main.getMessages().party_disbanded);
		for(UUID p : this.players)
			PartyManager.removeParty(p);
		//this.players.clear();
	}

	public void chat(Player player, String message) {
		if(message.trim().length() > 0) {
			String format = Main.getMessages().party_chat_format;
			message = format.replaceAll("%PLAYER%", player.getDisplayName()).replaceAll("%MESSAGE%", message);
			for(final UUID partyMember : this.getPlayers()) {
				final Player partyMemberPlayer = Bukkit.getPlayer(partyMember);
				if(partyMemberPlayer != null) {
					partyMemberPlayer.sendMessage(message);
				}
			}
		}
	}

	public void broadcast(Chat chat, final String msg) {
		for(final UUID partyMember : this.getPlayers()) {
			final Player partyMemberPlayer = Bukkit.getPlayer(partyMember);
			if(partyMemberPlayer != null) {
				chat.send(partyMemberPlayer, msg);
			}
		}
	}

	public void broadcast(Chat chat, final String msg, final List<UUID> excluded) {
		for(final UUID partyMember : this.getPlayers()) {
			if(!excluded.contains(partyMember)) {
				final Player partyMemberPlayer = Bukkit.getPlayer(partyMember);
				if(partyMemberPlayer != null) {
					chat.send(partyMemberPlayer, msg);
				}
			}
		}
	}

	private void tellAll(final String msg) {
		for(final UUID partyMember : this.getPlayers()) {
			final Player partyMemberPlayer = Bukkit.getPlayer(partyMember);
			if(partyMemberPlayer != null) {
				Main.getChat().send(partyMemberPlayer, msg, Main.getMessages().party_prefix);
			}
		}
	}

	private void tellAll(final String msg, final List<UUID> excluded) {
		for(final UUID partyMember : this.getPlayers()) {
			if(!excluded.contains(partyMember)) {
				final Player partyMemberPlayer = Bukkit.getPlayer(partyMember);
				if(partyMemberPlayer != null) {
					Main.getChat().send(partyMemberPlayer, msg, Main.getMessages().party_prefix);
				}
			}
		}
	}
}
