package fr.prodrivers.bukkit.commons.plugin;

import co.aikar.commands.BukkitCommandManager;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import fr.prodrivers.bukkit.commons.ProdriversCommons;
import fr.prodrivers.bukkit.commons.chat.Chat;
import fr.prodrivers.bukkit.commons.chat.ChatModule;
import fr.prodrivers.bukkit.commons.configuration.Configuration;
import fr.prodrivers.bukkit.commons.configuration.Messages;
import fr.prodrivers.bukkit.commons.hubs.MainHub;
import fr.prodrivers.bukkit.commons.hubs.MainHubModule;
import fr.prodrivers.bukkit.commons.parties.PartyManager;
import fr.prodrivers.bukkit.commons.parties.PartyModule;
import fr.prodrivers.bukkit.commons.plugin.commands.Commands;
import fr.prodrivers.bukkit.commons.plugin.commands.CommandsModule;
import fr.prodrivers.bukkit.commons.sections.SectionManager;
import fr.prodrivers.bukkit.commons.sections.SectionManagerModule;
import fr.prodrivers.bukkit.commons.storage.EbeanPropertiesProvider;
import fr.prodrivers.bukkit.commons.storage.StorageModule;
import fr.prodrivers.bukkit.commons.ui.UIModule;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;

@Singleton
public class Main extends JavaPlugin implements Listener {
	// Ensure that logback.xml is parsed by LoggerFactory _before_ Guice calls JUL.
	private final Logger logger = org.slf4j.LoggerFactory.getLogger(Main.class);

	private Injector injector;

	private EConfiguration configuration;

	private SectionManager sectionManager;
	private EbeanPropertiesProvider ebeanPropertiesProvider;

	public Injector getInjector() {
		return injector;
	}

	@Override
	public void onDisable() {
		PluginDescriptionFile plugindescription = this.getDescription();
		logger.info(plugindescription.getName() + " has been disabled!");
	}

	@Override
	public void onEnable() {
		logger.info("Java version is " + System.getProperty("java.version") + ".");

		// Create an injector with only the plugin module, containing its configuration
		injector = Guice.createInjector(
				new PluginModule(this, getClassLoader())
		);

		// Instantiate the other modules using injectors, as those modules may depend on configuration values
		CommandsModule commandsModule = injector.getInstance(CommandsModule.class);
		StorageModule storageModule = injector.getInstance(StorageModule.class);
		PartyModule partyModule = injector.getInstance(PartyModule.class);
		SectionManagerModule sectionManagerModule = injector.getInstance(SectionManagerModule.class);
		MainHubModule mainHubModule = injector.getInstance(MainHubModule.class);
		UIModule uiModule = injector.getInstance(UIModule.class);
		ChatModule chatModule = injector.getInstance(ChatModule.class);

		// Create a child injector that contains all those modules
		injector = injector.createChildInjector(
				commandsModule,
				storageModule,
				partyModule,
				sectionManagerModule,
				mainHubModule,
				chatModule,
				uiModule
		);

		configuration = (EConfiguration) injector.getInstance(Configuration.class);

		Chat chat = injector.getInstance(Chat.class);
		chat.setName(this.getDescription().getName());
		chat.load(injector.getInstance(Messages.class));

		getServer().getPluginManager().registerEvents(this, this);

		sectionManager = injector.getInstance(SectionManager.class);
		ebeanPropertiesProvider = injector.getInstance(EbeanPropertiesProvider.class);

		MainHub hub = injector.getInstance(MainHub.class);
		if(hub != null) {
			sectionManager.register(hub);
		} else {
			this.logger.info("No main hub available to ProdriversCommons.");
		}

		BukkitCommandManager bukkitCommandManager = injector.getInstance(BukkitCommandManager.class);
		Commands commands = injector.getInstance(Commands.class);
		PartyManager partyManager = injector.getInstance(PartyManager.class);

		Bukkit.getScheduler().runTaskLaterAsynchronously(this, () -> {
			logger.info("All plugins are loaded, building the section tree.");
			sectionManager.buildSectionTree();
			logger.info("Section tree built.");
			commands.registerCompletions(bukkitCommandManager, sectionManager, partyManager);
		}, configuration.sectionTree_buildDelayTicks);

		ProdriversCommons.init(this);

		logger.info("" + this.getDescription().getName() + " has been enabled!");
	}

	public void reload() {
		this.configuration.reload();
	}
}
