package fr.prodrivers.bukkit.commons.commands;

import fr.prodrivers.bukkit.commons.Chat;
import fr.prodrivers.bukkit.commons.Log;
import fr.prodrivers.bukkit.commons.parties.Party;
import fr.prodrivers.bukkit.commons.parties.PartyManager;
import fr.prodrivers.bukkit.commons.plugin.EMessages;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;
import java.util.UUID;

public class PartyCommands implements CommandExecutor {
	private static final String label = "party";
	private static final String short_chat_label = "p";
	private static final String permission = "pcommons.party";

	private final EMessages messages;
	private final Chat chat;
	private final PartyManager partyManager;

	@Inject
	PartyCommands(JavaPlugin plugin, Chat chat, EMessages messages, PartyManager partyManager) {
		this.chat = chat;
		this.messages = messages;
		this.partyManager = partyManager;

		plugin.getCommand(label).setExecutor(this);
		plugin.getCommand(short_chat_label).setExecutor(this);
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(label.equalsIgnoreCase(PartyCommands.label)) {
			if(sender instanceof Player) {
				if(sender.hasPermission(permission)) {
					if(args.length > 0) {
						switch(args[0]) {
							case "invite":
								partyInvite((Player) sender, args);
								break;
							case "accept":
								partyAccept((Player) sender, args);
								break;
							case "kick":
								partyKick((Player) sender, args);
								break;
							case "list":
								partyList((Player) sender);
								break;
							case "disband":
								partyDisband((Player) sender);
								break;
							case "leave":
								partyLeave((Player) sender);
								break;
							case "owner":
								partySetOwner((Player) sender, args);
								break;
							case "chat":
								partyChat((Player) sender, args);
								break;
							default:
								Player invitedPlayer = Bukkit.getPlayer(args[0]);
								if(invitedPlayer == null) {
									sendPartyHelp(sender);
								} else {
									partyInvite((Player) sender, new String[]{"invite", args[0]});
								}
								break;
						}
					} else {
						sendPartyHelp(sender);
					}
				} else {
					this.chat.error(sender, this.messages.no_permission, this.messages.party_prefix);
				}
			} else {
				this.chat.error(sender, this.messages.player_only_command, this.messages.party_prefix);
			}

			return true;
		} else if(label.equalsIgnoreCase(short_chat_label)) {
			partyChat((Player) sender, args, false);
			return true;
		}

		return false;
	}

	private void sendPartyHelp(final CommandSender sender) {
		Map<String, String> cmdpartydesc = this.messages.party_help;
		this.chat.send(sender, this.messages.party_help_heading, this.messages.party_prefix);
		for(final String k : cmdpartydesc.keySet()) {
			if(k.length() < 3) {
				this.chat.send(sender, "", this.messages.party_prefix);
				continue;
			}
			final String v = cmdpartydesc.get(k);
			this.chat.send(sender, ChatColor.YELLOW + "/" + label + " " + k + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + v, this.messages.party_prefix);
		}
	}

	private void partyInvite(final Player inviter, final String[] args) {
		if(args.length > 1) {
			final Player invited = Bukkit.getPlayer(args[1]);
			if(invited == null) {
				this.chat.error(inviter, this.messages.party_player_not_online.replaceAll("%PLAYER%", args[1]), this.messages.party_prefix);
				return;
			}

			if(inviter.getUniqueId().equals(invited.getUniqueId())) {
				this.chat.error(inviter, this.messages.party_cannot_invite_yourself, this.messages.party_prefix);
				return;
			}

			if(!this.partyManager.isInParty(invited.getUniqueId())) {
				Party inviterParty = this.partyManager.getParty(inviter.getUniqueId());
				if(inviterParty == null) {
					inviterParty = this.partyManager.createParty(inviter.getUniqueId());
				} else {
					if(!inviterParty.isPartyOwner(inviter.getUniqueId())) {
						this.chat.error(inviter, this.messages.party_player_not_party_owner, this.messages.party_prefix);
						return;
					}
				}
				this.partyManager.addPartyInvite(invited.getUniqueId(), inviterParty);
				this.chat.success(inviter, this.messages.party_you_invited.replaceAll("%PLAYER%", invited.getName()), this.messages.party_prefix);

				BaseComponent[] invite_message = TextComponent.fromLegacyText(this.messages.party_you_were_invited.replaceAll("%PLAYER%", inviter.getName()));
				BaseComponent[] invite_message_hover_text = TextComponent.fromLegacyText(this.messages.party_you_were_invited_hover_text.replaceAll("%PLAYER%", inviter.getName()));
				ClickEvent clickEvent = new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/party accept " + inviter.getName());
				HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, invite_message_hover_text);
				for(BaseComponent component : invite_message) {
					component.setClickEvent(clickEvent);
					component.setHoverEvent(hoverEvent);
				}

				this.chat.success(invited, invite_message, this.messages.party_prefix);
			} else {
				this.chat.error(inviter, this.messages.party_player_is_in_a_party.replaceAll("%PLAYER%", invited.getName()), this.messages.party_prefix);
			}
		} else {
			this.chat.send(inviter, this.messages.party_usage_prefix + "/" + label + " invite <player>", this.messages.party_prefix);
		}
	}

	private void partyAccept(final Player invited, final String[] args) {
		if(args.length > 1) {
			Player inviter = Bukkit.getPlayer(args[1]);
			if(inviter == null || !inviter.isOnline()) {
				this.chat.error(invited, this.messages.party_player_not_online.replaceAll("%PLAYER%", args[1]), this.messages.party_prefix);
				return;
			}
			if(!this.partyManager.hasPartyInvites(invited.getUniqueId())) {
				this.chat.error(invited, this.messages.party_not_invited_to_any_party, this.messages.party_prefix);
				return;
			}

			if(this.partyManager.isInParty(invited.getUniqueId())) {
				this.chat.error(invited, this.messages.party_must_leave_party_before_joining_another, this.messages.party_prefix);
				return;
			}

			Party party__ = null;
			for(final Party party : this.partyManager.getPartyInvites(invited.getUniqueId())) {
				if(party.getOwnerUniqueId().equals(inviter.getUniqueId())) {
					party__ = party;
					break;
				}
			}
			if(party__ != null) {
				party__.addPlayer(invited.getUniqueId());
				this.partyManager.removePartyInvites(invited.getUniqueId());
			} else {
				this.chat.error(invited, this.messages.party_not_invited_to_players_party.replaceAll("%PLAYER%", args[1]), this.messages.party_prefix);
			}
		} else {
			this.chat.send(invited, this.messages.party_usage_prefix + "/" + label + " accept <player>", this.messages.party_prefix);
		}
	}

	private void partyKick(final Player player, final String[] args) {
		if(args.length > 1) {
			final Player target = Bukkit.getPlayer(args[1]);
			if(target == null || !target.isOnline()) {
				this.chat.error(player, this.messages.party_player_not_online.replaceAll("%PLAYER%", args[1]), this.messages.party_prefix);
				return;
			}

			final Party party = this.partyManager.getParty(player.getUniqueId());
			if(party != null && party.isPartyOwner(player.getUniqueId())) {
				if(party.containsPlayer(target.getUniqueId())) {
					party.removePlayer(target.getUniqueId());
				} else {
					this.chat.error(player, this.messages.party_player_not_in_party.replaceAll("%PLAYER%", args[1]), this.messages.party_prefix);
				}
			} else {
				this.chat.send(player, this.messages.party_you_are_not_a_party_owner, this.messages.party_prefix);
			}
		} else {
			this.chat.send(player, this.messages.party_usage_prefix + "/" + label + " kick <player>", this.messages.party_prefix);
		}
	}

	private void partyList(final Player player) {
		Party party = this.partyManager.getParty(player.getUniqueId());

		if(party != null) {
			this.chat.send(player, this.messages.party_list_heading, this.messages.party_prefix);
			StringBuilder ret = new StringBuilder(ChatColor.DARK_GREEN.toString());
			Player owner = Bukkit.getPlayer(party.getOwnerUniqueId());
			if(owner == null) {
				Log.severe("Owner " + party.getOwnerUniqueId() + "  of party is null on party list command by " + player);
				return;
			}
			ret.append(owner.getName());
			for(final UUID p_ : party.getPlayers()) {
				ret.append(ChatColor.GREEN);
				ret.append(", ");
				Player other = Bukkit.getPlayer(p_);
				if(other == null) {
					Log.severe("Party player " + p_ + "  of party is null on party list command by " + player);
				} else {
					ret.append(other.getName());
				}
			}
			this.chat.send(player, ret.toString(), this.messages.party_prefix);
		} else {
			this.chat.send(player, this.messages.party_you_are_not_in_a_party, this.messages.party_prefix);
		}
	}

	private void partyDisband(final Player player) {
		Party party = this.partyManager.getParty(player.getUniqueId());
		if(party != null && party.isPartyOwner(player.getUniqueId())) {
			party.disband();
		} else {
			this.chat.send(player, this.messages.party_you_are_not_a_party_owner, this.messages.party_prefix);
		}
	}

	private void partyLeave(final Player player) {
		Party party = this.partyManager.getParty(player.getUniqueId());
		if(party == null) {
			this.chat.send(player, this.messages.party_you_are_not_in_a_party, this.messages.party_prefix);
			return;
		}

		party.removePlayer(player.getUniqueId());
	}

	private void partySetOwner(final Player player, final String[] args) {
		if(args.length > 1) {
			Party party = this.partyManager.getParty(player.getUniqueId());
			if(party == null) {
				this.chat.send(player, this.messages.party_you_are_not_in_a_party, this.messages.party_prefix);
				return;
			}

			if(!party.isPartyOwner(player.getUniqueId())) {
				this.chat.error(player, this.messages.party_player_not_party_owner, this.messages.party_prefix);
				return;
			}

			Player newOwner = Bukkit.getPlayer(args[1]);
			if(newOwner == null || !newOwner.isOnline()) {
				this.chat.error(player, this.messages.party_player_not_online.replaceAll("%PLAYER%", args[1]), this.messages.party_prefix);
				return;
			}

			if(newOwner.getUniqueId() == player.getUniqueId()) {
				this.chat.error(player, this.messages.party_player_cannot_elect_yourself, this.messages.party_prefix);
				return;
			}

			party.electOwner(newOwner.getUniqueId());
		} else {
			this.chat.send(player, this.messages.party_usage_prefix + "/" + label + " accept <player>", this.messages.party_prefix);
		}
	}

	private void partyChat(final Player player, final String[] args) {
		partyChat(player, args, true);
	}

	private void partyChat(final Player player, final String[] args, boolean removeFirstArg) {
		Party party = this.partyManager.getParty(player.getUniqueId());
		if(party == null) {
			this.chat.send(player, this.messages.party_you_are_not_in_a_party, this.messages.party_prefix);
			return;
		}

		LinkedList<String> msgList = new LinkedList<String>(Arrays.asList(args));
		// Remove two first elements from arguments
		if(msgList.size() > 1 && removeFirstArg) {
			msgList.removeFirst();
		}
		String msg = String.join(" ", msgList);

		party.chat(player, msg);
	}
}
