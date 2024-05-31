package fr.prodrivers.bukkit.commons.plugin.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import fr.prodrivers.bukkit.commons.chat.Chat;
import fr.prodrivers.bukkit.commons.plugin.EMessages;
import fr.prodrivers.bukkit.commons.sections.Section;
import fr.prodrivers.bukkit.commons.sections.SectionCapabilities;
import fr.prodrivers.bukkit.commons.sections.SectionManager;
import org.bukkit.entity.Player;

import javax.inject.Inject;
import javax.inject.Singleton;

import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
@CommandAlias("hub")
public class HubCommands extends BaseCommand {
	private final Logger logger;
	private final SectionManager sectionManager;
	private final Chat chat;
	private final EMessages messages;

	@Inject
	HubCommands(Logger logger, SectionManager sectionManager, Chat chat, EMessages messages) {
		this.logger = logger;
		this.chat = chat;
		this.messages = messages;
		this.sectionManager = sectionManager;
	}

	@Default
	@CommandPermission("pcommons.hub")
	@Syntax("[hub name]")
	@CommandCompletion("@hubs")
	private void handle(Player player, @Optional String sectionName) {
		try {
			if(sectionName != null) {
				Section section = this.sectionManager.getSection(sectionName);
				if(section != null && section.getCapabilities().contains(SectionCapabilities.HUB)) {
					this.sectionManager.enter(player, sectionName);
				} else {
					this.chat.error(player, this.messages.invalid_hub_name);
				}
			} else {
				this.sectionManager.enter(player);
			}
		} catch(Exception e) {
			this.logger.log(Level.SEVERE, "Unexpected error during hub command.", e);
			this.chat.error(player, this.messages.error_occurred);
		}
	}
}
