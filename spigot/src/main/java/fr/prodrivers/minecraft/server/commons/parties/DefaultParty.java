package fr.prodrivers.minecraft.server.commons.parties;

import com.google.inject.assistedinject.Assisted;
import fr.prodrivers.minecraft.server.commons.chat.Chat;
import fr.prodrivers.minecraft.server.spigot.commons.plugin.EMessages;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DefaultParty implements Party {
	private UUID owner;
	private final ArrayList<UUID> players = new ArrayList<>();

	private final EMessages messages;
	private final Chat chat;

	@Inject
	DefaultParty(final EMessages messages, final Chat chat, @Assisted final UUID ownerUniqueId) {
		this.owner = ownerUniqueId;
		this.players.add(this.owner);

		this.messages = messages;
		this.chat = chat;
	}

	public UUID getOwnerUniqueId() {
		return this.owner;
	}

	public ArrayList<UUID> getPlayers() {
		return this.players;
	}

	public void registerPlayer(final UUID playerUniqueId) {
		this.players.add(playerUniqueId);
	}

	public boolean assignOwner(final UUID newOwner) {
		if(this.players.contains(newOwner)) {
			owner = newOwner;
			return true;
		}
		return false;
	}

	public void unregisterPlayer(final UUID playerUniqueId) {
		this.players.remove(playerUniqueId);
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

	public void clear() {
		this.players.clear();
	}

	public void chatAsPlayer(final Player player, String message) {
		if(message.trim().length() > 0) {
			String format = this.messages.party_chat_format;
			message = format.replaceAll("%PLAYER%", player.getDisplayName()).replaceAll("%MESSAGE%", message);
			for(final UUID partyMember : this.getPlayers()) {
				this.chat.send(partyMember, message, this.messages.party_prefix);
			}
		}
	}

	private String getStringForMessage(PartyMessage message) {
		return switch(message) {
			case DISBANDED -> this.messages.party_disbanded;
			case JOINED_YOU -> this.messages.party_you_joined_party;
			case JOINED_OTHERS -> this.messages.party_player_joined_party;
			case LEFT_YOU -> this.messages.party_you_left_party;
			case LEFT_OTHERS -> this.messages.party_player_left_party;
			case CANNOT_INVITE_YOURSELF -> this.messages.party_cannot_invite_yourself;
			case NOT_PARTY_OWNER_YOU -> this.messages.party_player_not_party_owner;
			case PLAYER_INVITED_INVITER -> this.messages.party_you_invited;
			case PLAYER_INVITED_YOU -> this.messages.party_you_were_invited;
			case PLAYER_IS_IN_PARTY -> this.messages.party_player_is_in_a_party;
			case PARTY_LEAVE_BEFORE_JOINING_ANOTHER -> this.messages.party_must_leave_party_before_joining_another;
			case PARTY_NOT_INVITED_YOU -> this.messages.party_not_invited_to_players_party;
			case PARTY_LEADER_ASSIGNED_YOU -> this.messages.party_you_were_elected_as_new_leader;
			case PARTY_LEADER_ASSIGNED_OTHERS -> this.messages.party_player_elected_as_new_leader;
			default -> "NOT_DEFINED:" + message.toString().toUpperCase();
		};
	}

	private BaseComponent[] getComponentsFromMessage(PartyMessage message, final Object... substitutions) {
		if(message == PartyMessage.PLAYER_INVITED_YOU) {
			BaseComponent[] invite_message = TextComponent.fromLegacyText(getStringForMessage(PartyMessage.PLAYER_INVITED_YOU).formatted(substitutions));
			BaseComponent[] invite_message_hover_text = TextComponent.fromLegacyText(this.messages.party_you_were_invited_hover_text.formatted(substitutions));
			ClickEvent clickEvent = new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/party accept " + substitutions[0]);
			HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, invite_message_hover_text);
			for(BaseComponent component : invite_message) {
				component.setClickEvent(clickEvent);
				component.setHoverEvent(hoverEvent);
			}
			return invite_message;
		}

		return TextComponent.fromLegacyText(getStringForMessage(message).formatted(substitutions));
	}

	public void send(final UUID receiverUniqueId, final PartyMessage message, final Object... substitutions) {
		BaseComponent[] components = getComponentsFromMessage(message, substitutions);
		switch(message) {
			case JOINED_YOU:
			case LEFT_YOU:
			case PLAYER_INVITED_INVITER:
			case PLAYER_INVITED_YOU:
				this.chat.success(receiverUniqueId, components, this.messages.party_prefix);
				return;
			case CANNOT_INVITE_YOURSELF:
			case NOT_PARTY_OWNER_YOU:
			case PLAYER_IS_IN_PARTY:
			case PARTY_LEAVE_BEFORE_JOINING_ANOTHER:
			case PARTY_NOT_INVITED_YOU:
				this.chat.error(receiverUniqueId, components, this.messages.party_prefix);
				return;
			default:
				this.chat.send(receiverUniqueId, components, this.messages.party_prefix);
		}
	}

	public void broadcast(final PartyMessage message, final Object... substitutions) {
		for(final UUID partyMember : this.getPlayers()) {
			this.send(partyMember, message, substitutions);
		}
	}

	public void broadcast(final PartyMessage message, final List<UUID> excluded, final Object... substitutions) {
		for(final UUID partyMember : this.getPlayers()) {
			if(!excluded.contains(partyMember)) {
				this.send(partyMember, message, substitutions);
			}
		}
	}

	@Override
	public void broadcast(String message) {
		for(final UUID partyMember : this.getPlayers()) {
			this.chat.send(partyMember, message, this.messages.party_prefix);
		}
	}

	@Override
	public void broadcast(String message, List<UUID> excluded) {
		for(final UUID partyMember : this.getPlayers()) {
			if(!excluded.contains(partyMember)) {
				this.chat.send(partyMember, message, this.messages.party_prefix);
			}
		}
	}
}
