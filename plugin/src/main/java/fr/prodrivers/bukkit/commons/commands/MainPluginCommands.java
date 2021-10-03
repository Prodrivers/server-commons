package fr.prodrivers.bukkit.commons.commands;

import fr.prodrivers.bukkit.commons.Chat;
import fr.prodrivers.bukkit.commons.configuration.Configuration;
import fr.prodrivers.bukkit.commons.sections.Section;
import fr.prodrivers.bukkit.commons.sections.SectionManager;
import fr.prodrivers.bukkit.commons.storage.DataSourceConfigProvider;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import javax.inject.Inject;

public class MainPluginCommands implements CommandExecutor {
	private static final String label = "pcommons";

	private final SectionManager sectionManager;
	private final Chat chat;
	private final CommandsModule manager;
	private final Configuration configuration;
	private final DataSourceConfigProvider dataSourceConfigProvider;

	@Inject
	MainPluginCommands(JavaPlugin plugin, SectionManager sectionManager, Chat chat, CommandsModule manager, Configuration configuration, DataSourceConfigProvider dataSourceConfigProvider) {
		this.chat = chat;
		this.manager = manager;
		this.sectionManager = sectionManager;
		this.configuration = configuration;
		this.dataSourceConfigProvider = dataSourceConfigProvider;
		plugin.getCommand(label).setExecutor(this);
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(label.equalsIgnoreCase(MainPluginCommands.label)) {
			if(args.length > 0) {
				switch(args[0]) {
					case "reload":
						if(sender.hasPermission("pcommons.reload"))
							this.configuration.reload();
							this.dataSourceConfigProvider.reload();
						break;
					case "sections":
						sectionsCommand(sender, args);
						break;
				}
			}

			return true;
		}

		return false;
	}

	private void sectionsCommand(CommandSender sender, String[] args) {
		if(args.length > 1) {
			switch(args[1]) {
				case "query":
					if(sender.hasPermission("pcommons.section.query")) {
						if(args.length > 2) {
							OfflinePlayer target = Bukkit.getPlayer(args[2]);
							if(target == null) {
								target = Bukkit.getOfflinePlayer(args[2]);
							}
							Section section = this.sectionManager.getCurrentSection(target);

							if(section != null)
								this.chat.send(sender, target.getName() + " -> " + section.getFullName() + " (" + section.getClass().getCanonicalName() + ")");
							else
								this.chat.send(sender, target.getName() + " -> No registered section");
						}
					}
					break;
			}
		}
	}
}
