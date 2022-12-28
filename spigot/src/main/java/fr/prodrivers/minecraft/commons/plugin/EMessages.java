package fr.prodrivers.minecraft.commons.plugin;

import fr.prodrivers.minecraft.commons.configuration.Messages;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("CanBeFinal")
@Singleton
public class EMessages extends Messages {
	public String permission_denied = "&cYou do not have permission to execute this.";
	public String error_occurred = "An internal error occurred.";
	public String invalid_hub_name = "Invalid hub name.";
	public String invalid_player = "Invalid player.";
	public String not_a_player = "Not a player.";
	public String invalid_number = "Invalid number.";

	public String player_kicked_invalid_hub = "The server is currently unavailable.";

	public String party_prefix = "[Party]";
	public String party_help_heading = ChatColor.DARK_GRAY + "------- " + ChatColor.BLUE + "Help" + ChatColor.DARK_GRAY + " -------";
	public String party_cannot_invite_yourself = "&cYou can not invite yourself!";
	public String party_player_not_online = "&4%s &cis not online!";
	public String party_you_invited = "&aYou invited &2%s&a!";
	public String party_you_were_invited = "&2%1$s &ainvited you to join his/her party! Click this message or type &2/party accept %1$s &ato accept.";
	public String party_you_were_invited_hover_text = "Click to join %s's party";
	public String party_not_invited_to_players_party = "&cYou are not invited to the party of &4%s&c.";
	public String party_player_not_in_party = "&4%s &cis not in your party.";
	public String party_you_joined_party = "&7You joined the party of &8%s&7.";
	public String party_player_joined_party = "&2%s &ajoined the party.";
	public String party_you_left_party = "&7You left the party of &8%s&7.";
	public String party_player_left_party = "&4%s &cleft the party.";
	public String party_disbanded = "&cThe party was disbanded.";
	public String party_list_heading = "&4Party players:";
	public String party_you_are_not_in_a_party = "&cYou are not in a party.";
	public String party_player_is_in_a_party = "&c%s is in a party.";
	public String party_you_are_not_a_party_owner = "&cYou do not own a party.";
	public String party_player_not_party_owner = "&cOnly the owner can invite players!";
	public String party_must_leave_party_before_joining_another = "&cYou must leave your current party to join another!";
	public String party_you_were_elected_as_new_leader = "&cYou were elected as the new party leader.";
	public String party_player_elected_as_new_leader = "&c%s is the new party leader.";
	public String party_player_cannot_elect_yourself = "&cYou cannot set yourself as the new party owner as you are already the owner.";
	public Map<String, String> party_help = new LinkedHashMap<>();
	public String party_usage_prefix = "&aUsage: ";
	public String party_chat_format = "%PLAYER%: %MESSAGE%";

	{
		party_help.put("invite <player>", "Invites a player to your party and creates one if you don't have one yet.");
		party_help.put("accept <player>", "Accepts an invitation to a party");
		party_help.put("disband", "Disbands the party");
		party_help.put("kick <player>", "Kicks a player from your party.");
		party_help.put("leave", "Leaves a party you're in.");
		party_help.put("list", "Lists all players and the owner of the party.");
		party_help.put("owner <player>", "Set the player as the new party owner.");
		party_help.put("chat", "Send a message to all party players.");
	}

	@Inject
	public EMessages(Plugin plugin) {
		super(plugin);
	}
}
