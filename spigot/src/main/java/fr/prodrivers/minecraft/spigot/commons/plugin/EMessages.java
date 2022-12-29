package fr.prodrivers.minecraft.spigot.commons.plugin;

import fr.prodrivers.minecraft.commons.configuration.Messages;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.plugin.Plugin;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("CanBeFinal")
@Singleton
public class EMessages extends Messages {
	public Component permission_denied = miniMessage.deserialize("You do not have permission to execute this.");
	public Component error_occurred = miniMessage.deserialize("An internal error occurred.");
	public Component invalid_hub_name = miniMessage.deserialize("Invalid hub name.");
	public Component invalid_player = miniMessage.deserialize("Invalid player.");
	public Component not_a_player = miniMessage.deserialize("Not a player.");
	public Component invalid_number = miniMessage.deserialize("Invalid number.");

	public Component player_kicked_invalid_hub = miniMessage.deserialize("<red>The server is currently unavailable.</red>");

	public Component party_prefix = miniMessage.deserialize("[Party]");
	public Component party_help_heading = miniMessage.deserialize("<dark_gray>-------</dark_gray> <blue>Help</blue> <dark_gray>-------</dark_gray>");
	public Component party_cannot_invite_yourself = miniMessage.deserialize("<red>You can not invite yourself!</red>");
	public String party_player_not_online = "<red><dark_red>%s</dark_red> &cis not online!</red>";
	public String party_you_invited = "<green>You invited <dark_green>%s</dark_green>!</green>";
	public String party_you_were_invited = "<dark_green>%1$s</dark_green><green> invited you to join his/her party! Click this message or type <fdark_green>/party accept %1$s</dark_green> to accept.</green>";
	public String party_you_were_invited_hover_text = "Click to join %s's party";
	public String party_not_invited_to_players_party = "<red>You are not invited to the party of <dark_red>%s</dark_red>.</red>";
	public String party_player_not_in_party = "<red><dark_red>%s</dark_red> is not in your party.</red>";
	public String party_you_joined_party = "<gray>You joined the party of <dark_gray>%s</dark_gray>.</gray>";
	public String party_player_joined_party = "<green><dark_green>%s</dark_green> joined the party.</green>";
	public String party_you_left_party = "<gray>You left the party of <dark_gray>%s</dark_gray>.</gray>";
	public String party_player_left_party = "<red><dark_red>%s</dark_red> left the party.</red>";
	public Component party_disbanded = miniMessage.deserialize("<red>The party was disbanded.</red>");
	public Component party_list_heading = miniMessage.deserialize("<dark_red>Party players:</dark_red>");
	public Component party_you_are_not_in_a_party = miniMessage.deserialize("<red>You are not in a party.</red>");
	public String party_player_is_in_a_party = "<red><dark_red>%s</dark_red> is in a party.</red>";
	public Component party_you_are_not_a_party_owner = miniMessage.deserialize("<red>You do not own a party.</red>");
	public Component party_player_not_party_owner = miniMessage.deserialize("<red>Only the owner can invite players!</red>");
	public Component party_must_leave_party_before_joining_another = miniMessage.deserialize("<red>You must leave your current party to join another!</red>");
	public Component party_you_were_elected_as_new_leader = miniMessage.deserialize("<gold>You were elected as the new party leader.</gold>");
	public String party_player_elected_as_new_leader = "<gold>%s is the new party leader.</gold>";
	public Component party_player_cannot_elect_yourself = miniMessage.deserialize("<red>You cannot set yourself as the new party owner as you are already the owner.</red>");
	public Map<String, String> party_help = new LinkedHashMap<>();
	public Component party_usage_prefix = miniMessage.deserialize("<green>Usage: </green>");
	public String party_chat_format = "<player>: <message>";

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
