package fr.prodrivers.minecraft.commons.plugin.commands;

import co.aikar.commands.BukkitCommandCompletionContext;
import co.aikar.commands.BukkitCommandManager;
import co.aikar.commands.CommandCompletions;
import fr.prodrivers.minecraft.commons.parties.Party;
import fr.prodrivers.minecraft.commons.parties.PartyManager;
import fr.prodrivers.minecraft.commons.sections.Section;
import fr.prodrivers.minecraft.commons.sections.SectionCapabilities;
import fr.prodrivers.minecraft.commons.sections.SectionManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import javax.inject.Inject;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.StreamSupport;

public class Commands {
	@Inject
	public Commands(BukkitCommandManager commandManager, HubCommands hubCommands, MainPluginCommands mainPluginCommands, PartyCommands partyCommands) {
		// Register commands
		commandManager.registerCommand(hubCommands);
		commandManager.registerCommand(partyCommands);
		commandManager.registerCommand(mainPluginCommands);
	}

	public void registerCompletions(BukkitCommandManager commandManager, SectionManager sectionManager, PartyManager partyManager) {
		CommandCompletions<BukkitCommandCompletionContext> commandCompletions = commandManager.getCommandCompletions();

		commandCompletions.registerAsyncCompletion("others", c -> Bukkit.getOnlinePlayers().stream().filter(p -> p.getUniqueId() != c.getPlayer().getUniqueId()).map(Player::getName).toList());

		commandCompletions.registerAsyncCompletion("hubs", c -> StreamSupport.stream(sectionManager.getSections().spliterator(), false).filter(s -> s.getFullName().length() > 0).filter(s -> s.getCapabilities().contains(SectionCapabilities.HUB)).map(Section::getFullName).toList());

		commandCompletions.registerAsyncCompletion("partyothers", c -> {
			Party party = partyManager.getParty(c.getPlayer().getUniqueId());
			if(party != null) {
				return party.getPlayers().stream().map(Bukkit::getOfflinePlayer).filter(p -> p.getUniqueId() != c.getPlayer().getUniqueId()).map(OfflinePlayer::getName).filter(Objects::nonNull).toList();
			}
			return Collections.emptyList();
		});

		commandCompletions.registerAsyncCompletion("partyinviters", c -> {
			Iterable<Party> inviterParties = partyManager.getPartyInvites(c.getPlayer().getUniqueId());
			if(inviterParties != null) {
				return StreamSupport.stream(inviterParties.spliterator(), false).map(Party::getOwnerUniqueId).map(Bukkit::getOfflinePlayer).map(OfflinePlayer::getName).filter(Objects::nonNull).toList();
			}
			return Collections.emptyList();
		});
	}
}
