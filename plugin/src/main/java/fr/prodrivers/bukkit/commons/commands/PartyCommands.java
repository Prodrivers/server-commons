package fr.prodrivers.bukkit.commons.commands;

import com.google.common.collect.Lists;
import fr.prodrivers.bukkit.commons.parties.Party;
import fr.prodrivers.bukkit.commons.parties.PartyManager;
import fr.prodrivers.bukkit.commons.plugin.Main;
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

import java.util.*;

public class PartyCommands implements CommandExecutor {
	private static String label = "party";
	private static String short_chat_label = "p";
	private static String permission = "pcommons.party";

	PartyCommands( JavaPlugin plugin ) {
		plugin.getCommand( label ).setExecutor( this );
		plugin.getCommand( short_chat_label ).setExecutor( this );
	}

	public boolean onCommand( CommandSender sender, Command command, String label, String[] args ) {
		if( label.equalsIgnoreCase( PartyCommands.label ) ) {
			if( sender instanceof Player ) {
				if( sender.hasPermission( permission ) ) {
					if( args.length > 0 ) {
						switch( args[ 0 ] ) {
							case "invite":
								partyInvite( (Player) sender, args );
								break;
							case "accept":
								partyAccept( (Player) sender, args );
								break;
							case "kick":
								partyKick( (Player) sender, args );
								break;
							case "list":
								partyList( (Player) sender );
								break;
							case "disband":
								partyDisband( (Player) sender );
								break;
							case "leave":
								partyLeave( (Player) sender );
								break;
							case "owner":
								partySetOwner( (Player) sender, args );
								break;
							case "chat":
								partyChat( (Player) sender, args );
								break;
							default:
								Player invitedPlayer = Bukkit.getPlayer( args[ 0 ] );
								if( invitedPlayer == null ) {
									sendPartyHelp( sender );
								} else {
									partyInvite( (Player) sender, new String[] { "invite", args[ 0 ] } );
								}
								break;
						}
					} else {
						sendPartyHelp( sender );
					}
				} else {
					Main.getChat().error( sender, Main.getMessages().no_permission, Main.getMessages().party_prefix );
				}
			} else {
				Main.getChat().error( sender, Main.getMessages().player_only_command, Main.getMessages().party_prefix );
			}

			return true;
		} else if( label.equalsIgnoreCase( short_chat_label ) ) {
			partyChat( (Player) sender, args, false );
			return true;
		}

		return false;
	}

	private static void sendPartyHelp( final CommandSender sender ) {
		Map<String, String> cmdpartydesc = Main.getMessages().party_help;
		Main.getChat().send( sender, Main.getMessages().party_help_heading, Main.getMessages().party_prefix );
		for( final String k : cmdpartydesc.keySet() ) {
			if( k.length() < 3 ) {
				Main.getChat().send( sender, "", Main.getMessages().party_prefix );
				continue;
			}
			final String v = cmdpartydesc.get( k );
			Main.getChat().send( sender, ChatColor.YELLOW + "/" + label + " " + k + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + v, Main.getMessages().party_prefix );
		}
	}

	private void partyInvite( final Player inviter, final String[] args ) {
		if( args.length > 1 ) {
			final Player invited = Bukkit.getPlayer( args[ 1 ] );
			if( invited == null ) {
				Main.getChat().error( inviter, Main.getMessages().party_player_not_online.replaceAll( "%PLAYER%", args[ 1 ] ), Main.getMessages().party_prefix );
				return;
			}

			if( inviter.getUniqueId().equals( invited.getUniqueId() ) ) {
				Main.getChat().error( inviter, Main.getMessages().party_cannot_invite_yourself, Main.getMessages().party_prefix );
				return;
			}

			if( !PartyManager.isInParty( invited.getUniqueId() ) ) {
				Party inviterParty = PartyManager.getParty( inviter.getUniqueId() );
				if( inviterParty == null ) {
					inviterParty = PartyManager.createParty( inviter.getUniqueId() );
				} else {
					if( !inviterParty.isPartyOwner( inviter.getUniqueId() ) ) {
						Main.getChat().error( inviter, Main.getMessages().party_player_not_party_owner, Main.getMessages().party_prefix );
						return;
					}
				}
				PartyManager.addPartyInvite( invited.getUniqueId(), inviterParty );
				Main.getChat().success( inviter, Main.getMessages().party_you_invited.replaceAll( "%PLAYER%", invited.getName() ), Main.getMessages().party_prefix );

				BaseComponent[] invite_message = TextComponent.fromLegacyText( Main.getMessages().party_you_were_invited.replaceAll( "%PLAYER%", inviter.getName() ) );
				BaseComponent[] invite_message_hover_text = TextComponent.fromLegacyText( Main.getMessages().party_you_were_invited_hover_text.replaceAll( "%PLAYER%", inviter.getName() ) );
				ClickEvent clickEvent = new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/party accept " + inviter.getName() );
				HoverEvent hoverEvent = new HoverEvent( HoverEvent.Action.SHOW_TEXT, invite_message_hover_text );
				for( BaseComponent component : invite_message ) {
					component.setClickEvent( clickEvent );
					component.setHoverEvent( hoverEvent );
				}

				Main.getChat().success( invited, invite_message, Main.getMessages().party_prefix );
			} else {
				Main.getChat().error( inviter, Main.getMessages().party_player_is_in_a_party.replaceAll( "%PLAYER%", invited.getName() ), Main.getMessages().party_prefix );
			}
		} else {
			Main.getChat().send( inviter, Main.getMessages().party_usage_prefix + "/" + label + " invite <player>", Main.getMessages().party_prefix );
		}
	}

	private void partyAccept( final Player invited, final String[] args ) {
		if( args.length > 1 ) {
			Player inviter = Bukkit.getPlayer( args[ 1 ] );
			if( inviter == null || !inviter.isOnline() ) {
				Main.getChat().error( invited, Main.getMessages().party_player_not_online.replaceAll( "%PLAYER%", args[ 1 ] ), Main.getMessages().party_prefix );
				return;
			}
			if( !PartyManager.hasPartyInvites( invited.getUniqueId() ) ) {
				Main.getChat().error( invited, Main.getMessages().party_not_invited_to_any_party, Main.getMessages().party_prefix );
				return;
			}

			if( PartyManager.isInParty( invited.getUniqueId() ) ) {
				Main.getChat().error( invited, Main.getMessages().party_must_leave_party_before_joining_another, Main.getMessages().party_prefix );
				return;
			}

			Party party__ = null;
			for( final Party party : PartyManager.getPartyInvites( invited.getUniqueId() ) ) {
				if( party.getOwnerUniqueId().equals( inviter.getUniqueId() ) ) {
					party__ = party;
					break;
				}
			}
			if( party__ != null ) {
				party__.addPlayer( invited.getUniqueId() );
				PartyManager.removePartyInvites( invited.getUniqueId() );
			} else {
				Main.getChat().error( invited, Main.getMessages().party_not_invited_to_players_party.replaceAll( "%PLAYER%", args[ 1 ] ), Main.getMessages().party_prefix );
			}
		} else {
			Main.getChat().send( invited, Main.getMessages().party_usage_prefix + "/" + label + " accept <player>", Main.getMessages().party_prefix );
		}
	}

	private void partyKick( final Player player, final String[] args ) {
		if( args.length > 1 ) {
			final Player target = Bukkit.getPlayer( args[ 1 ] );
			if( target == null || !target.isOnline() ) {
				Main.getChat().error( player, Main.getMessages().party_player_not_online.replaceAll( "%PLAYER%", args[ 1 ] ), Main.getMessages().party_prefix );
				return;
			}

			final Party party = PartyManager.getParty( player.getUniqueId() );
			if( party != null && party.isPartyOwner( player.getUniqueId() ) ) {
				if( party.containsPlayer( target.getUniqueId() ) ) {
					party.removePlayer( target.getUniqueId() );
				} else {
					Main.getChat().error( player, Main.getMessages().party_player_not_in_party.replaceAll( "%PLAYER%", args[ 1 ] ), Main.getMessages().party_prefix );
				}
			} else {
				Main.getChat().send( player, Main.getMessages().party_you_are_not_a_party_owner, Main.getMessages().party_prefix );
			}
		} else {
			Main.getChat().send( player, Main.getMessages().party_usage_prefix + "/" + label + " kick <player>", Main.getMessages().party_prefix );
		}
	}

	private void partyList( final Player player ) {
		Party party = PartyManager.getParty( player.getUniqueId() );

		if( party != null ) {
			Main.getChat().send( player, Main.getMessages().party_list_heading, Main.getMessages().party_prefix );
			StringBuilder ret = new StringBuilder( ChatColor.DARK_GREEN.toString() );
			ret.append( Bukkit.getPlayer( party.getOwnerUniqueId() ).getName() );
			for( final UUID p_ : party.getPlayers() ) {
				ret.append( ChatColor.GREEN );
				ret.append( ", " );
				ret.append( Bukkit.getPlayer( p_ ).getName() );
			}
			Main.getChat().send( player, ret.toString(), Main.getMessages().party_prefix );
		} else {
			Main.getChat().send( player, Main.getMessages().party_you_are_not_in_a_party, Main.getMessages().party_prefix );
		}
	}

	private void partyDisband( final Player player ) {
		Party party = PartyManager.getParty( player.getUniqueId() );
		if( party != null && party.isPartyOwner( player.getUniqueId() ) ) {
			party.disband();
		} else {
			Main.getChat().send( player, Main.getMessages().party_you_are_not_a_party_owner, Main.getMessages().party_prefix );
		}
	}

	private void partyLeave( final Player player ) {
		Party party = PartyManager.getParty( player.getUniqueId() );
		if( party == null ) {
			Main.getChat().send( player, Main.getMessages().party_you_are_not_in_a_party, Main.getMessages().party_prefix );
			return;
		}

		party.removePlayer( player.getUniqueId() );
	}

	private void partySetOwner( final Player player, final String[] args ) {
		if( args.length > 1 ) {
			Party party = PartyManager.getParty( player.getUniqueId() );
			if( party == null ) {
				Main.getChat().send( player, Main.getMessages().party_you_are_not_in_a_party, Main.getMessages().party_prefix );
				return;
			}

			if( !party.isPartyOwner( player.getUniqueId() ) ) {
				Main.getChat().error( player, Main.getMessages().party_player_not_party_owner, Main.getMessages().party_prefix );
				return;
			}

			Player newOwner = Bukkit.getPlayer( args[ 1 ] );
			if( newOwner == null || !newOwner.isOnline() ) {
				Main.getChat().error( player, Main.getMessages().party_player_not_online.replaceAll( "%PLAYER%", args[ 1 ] ), Main.getMessages().party_prefix );
				return;
			}

			if( newOwner.getUniqueId() == player.getUniqueId() ) {
				Main.getChat().error( player, Main.getMessages().party_player_cannot_elect_yourself, Main.getMessages().party_prefix );
				return;
			}

			party.electOwner( newOwner.getUniqueId() );
		} else {
			Main.getChat().send( player, Main.getMessages().party_usage_prefix + "/" + label + " accept <player>", Main.getMessages().party_prefix );
		}
	}

	private void partyChat( final Player player, final String[] args ) {
		partyChat( player, args, true );
	}

	private void partyChat( final Player player, final String[] args, boolean removeFirstArg ) {
		Party party = PartyManager.getParty( player.getUniqueId() );
		if( party == null ) {
			Main.getChat().send( player, Main.getMessages().party_you_are_not_in_a_party, Main.getMessages().party_prefix );
			return;
		}

		LinkedList<String> msgList = new LinkedList<String>( Arrays.asList( args ) );
		// Remove two first elements from arguments
		System.out.println( String.join( ", ", args ) );
		System.out.println( String.join( ", ", msgList )  );
		System.out.println( removeFirstArg );
		if( msgList.size() > 1 && removeFirstArg ) {
			msgList.removeFirst();
		}
		String msg = String.join( " ", msgList );
		System.out.println( msg );

		party.chat( player, msg );
	}
}
