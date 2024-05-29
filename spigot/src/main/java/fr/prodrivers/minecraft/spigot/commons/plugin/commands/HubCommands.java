package fr.prodrivers.minecraft.spigot.commons.plugin.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import fr.prodrivers.minecraft.commons.Log;
import fr.prodrivers.minecraft.commons.chat.SystemMessage;
import fr.prodrivers.minecraft.commons.players.PPlayer;
import fr.prodrivers.minecraft.commons.players.PPlayerAdapter;
import fr.prodrivers.minecraft.spigot.commons.plugin.EMessages;
import fr.prodrivers.minecraft.commons.sections.Section;
import fr.prodrivers.minecraft.commons.sections.SectionCapabilities;
import fr.prodrivers.minecraft.commons.sections.SectionManager;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
@CommandAlias("hub")
public class HubCommands extends BaseCommand {
	private final PPlayerAdapter pPlayerAdapter;
	private final SectionManager sectionManager;
	private final SystemMessage chat;
	private final EMessages messages;

	@Inject
	HubCommands(PPlayerAdapter pPlayerAdapter, SectionManager sectionManager, SystemMessage chat, EMessages messages) {
		this.chat = chat;
		this.messages = messages;
		this.pPlayerAdapter = pPlayerAdapter;
		this.sectionManager = sectionManager;
	}

	@Default
	@CommandPermission("pcommons.hub")
	@Syntax("[hub name]")
	@CommandCompletion("@hubs")
	private void handle(Player bukkitPlayer, @Optional String sectionName) {
		try {
			PPlayer player = this.pPlayerAdapter.of(bukkitPlayer);
			if(sectionName != null) {
				Section section = this.sectionManager.getSection(sectionName);
				if(section != null && section.getCapabilities().contains(SectionCapabilities.HUB)) {
					this.sectionManager.enter(player, sectionName);
				} else {
					this.chat.error(bukkitPlayer, this.messages.invalid_hub_name);
				}
			} else {
				this.sectionManager.enter(player);
			}
		} catch(Exception e) {
			Log.severe("Unexpected error during hub command.", e);
			this.chat.error(bukkitPlayer, this.messages.error_occurred);
		}
	}
}
