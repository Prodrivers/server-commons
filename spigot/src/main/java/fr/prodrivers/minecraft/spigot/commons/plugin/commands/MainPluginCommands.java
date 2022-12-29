package fr.prodrivers.minecraft.spigot.commons.plugin.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import fr.prodrivers.minecraft.spigot.commons.plugin.Main;
import fr.prodrivers.minecraft.commons.chat.SystemMessage;
import fr.prodrivers.minecraft.commons.sections.Section;
import fr.prodrivers.minecraft.commons.sections.SectionManager;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
@CommandAlias("pcommons")
public class MainPluginCommands extends BaseCommand {
	private final Main plugin;
	private final SectionManager sectionManager;
	private final SystemMessage chat;

	@Inject
	MainPluginCommands(Main plugin, SectionManager sectionManager, SystemMessage chat) {
		this.plugin = plugin;
		this.chat = chat;
		this.sectionManager = sectionManager;
	}

	@HelpCommand
	@CommandPermission("pcommons.help")
	public void onHelp(CommandSender sender, CommandHelp help) {
		help.showHelp();
	}

	@Subcommand("reload")
	@CommandPermission("pcommons.reload")
	public void reload(CommandSender sender) {
		this.plugin.reload();
	}

	@Subcommand("section query")
	@CommandPermission("pcommons.section.query")
	@Syntax("<player>")
	private void sectionsCommand(CommandSender sender, @Optional OnlinePlayer onlinePlayer) {
		Player player = null;

		if(onlinePlayer == null) {
			if(sender instanceof Player) {
				player = (Player) sender;
			}
		} else {
			player = onlinePlayer.getPlayer();
		}

		if(player != null) {
			Section section = this.sectionManager.getCurrentSection(player);

			if(section != null) {
				this.chat.info(sender, Component.text(player.getName() + " -> " + section.getFullName() + " (" + section.getClass().getCanonicalName() + ")"));
			} else {
				this.chat.error(sender,  Component.text(player.getName() + " -> No registered section"));
			}
		}
	}
}
