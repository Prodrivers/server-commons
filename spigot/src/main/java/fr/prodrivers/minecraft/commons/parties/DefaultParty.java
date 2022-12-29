package fr.prodrivers.minecraft.commons.parties;

import com.google.inject.assistedinject.Assisted;
import fr.prodrivers.minecraft.commons.chat.SystemMessage;
import fr.prodrivers.minecraft.spigot.commons.plugin.EMessages;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DefaultParty implements Party {
	private UUID owner;
	private final ArrayList<UUID> players = new ArrayList<>();

	private final EMessages messages;
	private final SystemMessage chat;

	@Inject
	DefaultParty(final EMessages messages, final SystemMessage chat, @Assisted final UUID ownerUniqueId) {
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
			Component messageToSend = this.messages.miniMessage().deserialize(this.messages.party_chat_format, Placeholder.unparsed("player", player.getName()), Placeholder.unparsed("message", message));
			for(final UUID partyMember : this.getPlayers()) {
				this.chat.info(partyMember, messageToSend, this.messages.party_prefix);
			}
		}
	}

	private Object getElementForMessage(PartyMessage message) {
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

	private Component getComponentsFromMessage(PartyMessage message, final Object... substitutions) {
		if(message == PartyMessage.PLAYER_INVITED_YOU) {
			Component invite_message_hover_text = this.messages.deserialize(this.messages.party_you_were_invited_hover_text.formatted(substitutions));
			String mainTextMiniMessage = (String) getElementForMessage(PartyMessage.PLAYER_INVITED_YOU);
			return this.messages.deserialize(mainTextMiniMessage.formatted(substitutions))
					.clickEvent(ClickEvent.runCommand("/party accept " + substitutions[0]))
					.hoverEvent(HoverEvent.showText(invite_message_hover_text));
		}

		Object mainTextElement = getElementForMessage(message);
		if(mainTextElement instanceof Component mainTextComponent) {
			return mainTextComponent;
		}
		return this.messages.deserialize(((String) mainTextElement).formatted(substitutions));
	}

	public void send(final UUID receiverUniqueId, final PartyMessage message, final Object... substitutions) {
		Component components = getComponentsFromMessage(message, substitutions);
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
				this.chat.info(receiverUniqueId, components, this.messages.party_prefix);
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
			this.chat.info(partyMember, Component.text(message), this.messages.party_prefix);
		}
	}

	@Override
	public void broadcast(String message, List<UUID> excluded) {
		for(final UUID partyMember : this.getPlayers()) {
			if(!excluded.contains(partyMember)) {
				this.chat.info(partyMember, Component.text(message), this.messages.party_prefix);
			}
		}
	}
}
