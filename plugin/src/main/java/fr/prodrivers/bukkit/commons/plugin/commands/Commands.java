package fr.prodrivers.bukkit.commons.plugin.commands;

import co.aikar.commands.BukkitCommandCompletionContext;
import co.aikar.commands.BukkitCommandManager;
import co.aikar.commands.CommandCompletions;
import fr.prodrivers.bukkit.commons.sections.SectionManager;

import javax.inject.Inject;
import java.util.stream.StreamSupport;

public class Commands {
	@Inject
	public Commands(BukkitCommandManager commandManager, HubCommands hubCommands, MainPluginCommands mainPluginCommands, PartyCommands partyCommands, SectionManager sectionManager) {
		// Register commands
		commandManager.registerCommand(hubCommands);
		commandManager.registerCommand(partyCommands);
		commandManager.registerCommand(mainPluginCommands);

		// Register completions
		registerCompletions(commandManager);
	}

	public void registerCompletions(BukkitCommandManager commandManager, SectionManager sectionManager) {
		CommandCompletions<BukkitCommandCompletionContext> commandCompletions = commandManager.getCommandCompletions();
		commandCompletions.registerAsyncCompletion("sections", c -> StreamSupport.stream(sectionManager.getSections().spliterator()).map(Enum::name).toList());
	}
}
