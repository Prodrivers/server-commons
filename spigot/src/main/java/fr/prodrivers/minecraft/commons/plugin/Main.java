package fr.prodrivers.minecraft.commons.plugin;

import co.aikar.commands.BukkitCommandManager;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import fr.prodrivers.minecraft.commons.Log;
import fr.prodrivers.minecraft.commons.ProdriversCommons;
import fr.prodrivers.minecraft.commons.chat.Chat;
import fr.prodrivers.minecraft.commons.chat.ChatModule;
import fr.prodrivers.minecraft.commons.configuration.Configuration;
import fr.prodrivers.minecraft.commons.configuration.Messages;
import fr.prodrivers.minecraft.commons.hubs.MainHub;
import fr.prodrivers.minecraft.commons.hubs.MainHubModule;
import fr.prodrivers.minecraft.commons.parties.PartyManager;
import fr.prodrivers.minecraft.commons.parties.PartyModule;
import fr.prodrivers.minecraft.commons.plugin.commands.Commands;
import fr.prodrivers.minecraft.commons.plugin.commands.CommandsModule;
import fr.prodrivers.minecraft.commons.sections.SectionManager;
import fr.prodrivers.minecraft.commons.sections.SectionManagerModule;
import fr.prodrivers.minecraft.commons.storage.EbeanPropertiesProvider;
import fr.prodrivers.minecraft.commons.storage.StorageModule;
import fr.prodrivers.minecraft.commons.ui.UIModule;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

@Singleton
public class Main extends JavaPlugin implements Listener {
	private Injector injector;

	private EConfiguration configuration;
	private Logger logger;

	private SectionManager sectionManager;
	private EbeanPropertiesProvider ebeanPropertiesProvider;

	public Injector getInjector() {
		return injector;
	}

	@Override
	public void onDisable() {
		PluginDescriptionFile plugindescription = this.getDescription();
		logger.info("" + plugindescription.getName() + " has been disabled!");
	}

	@Override
	public void onEnable() {
		logger = getLogger();
		Log.init(logger);

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

		Log.setLevel(configuration);

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
			Log.info("No main hub available to ProdriversCommons.");
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
