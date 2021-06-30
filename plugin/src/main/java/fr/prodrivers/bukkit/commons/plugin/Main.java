package fr.prodrivers.bukkit.commons.plugin;

import com.google.inject.Guice;
import com.google.inject.Injector;
import fr.prodrivers.bukkit.commons.Log;
import fr.prodrivers.bukkit.commons.ProdriversCommons;
import fr.prodrivers.bukkit.commons.commands.CommandsModule;
import fr.prodrivers.bukkit.commons.configuration.Configuration;
import fr.prodrivers.bukkit.commons.parties.PartyModule;
import fr.prodrivers.bukkit.commons.sections.MainHub;
import fr.prodrivers.bukkit.commons.sections.SectionManager;
import fr.prodrivers.bukkit.commons.storage.SQLProvider;
import fr.prodrivers.bukkit.commons.storage.StorageModule;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.logging.Logger;

public class Main extends JavaPlugin implements Listener {
	private Injector injector;

	private EConfiguration configuration;
	private Logger logger;

	private SectionManager sectionManager;

	private SQLProvider sqlProvider;

	public Injector getInjector() {
		return injector;
	}

	@Override
	public void onDisable() {
		PluginDescriptionFile plugindescription = this.getDescription();
		if(configuration != null) {
			configuration.save();
		} else {
			logger.warning("Configuration not saved because it is null.");
		}
		try {
			sqlProvider.close();
		} catch(IOException ex) {
			logger.severe("Error while closing storage provider: " + ex.getLocalizedMessage());
		}
		logger.info("" + plugindescription.getName() + " has been disabled!");
	}

	@Override
	public void onEnable() {
		logger = getLogger();
		Log.init(logger);

		logger.info("Java version is " + System.getProperty("java.version") + ".");

		injector = Guice.createInjector(
				new PluginModule(this, getClassLoader()),
				new CommandsModule(),
				new StorageModule(),
				new PartyModule()
		);

		configuration = (EConfiguration) injector.getInstance(Configuration.class);

		Log.setLevel(configuration);

		getServer().getPluginManager().registerEvents(this, this);

		sqlProvider = injector.getInstance(SQLProvider.class);

		sectionManager = injector.getInstance(SectionManager.class);

		MainHub hub = injector.getInstance(MainHub.class);
		sectionManager.register(hub);

		Bukkit.getScheduler().runTaskLaterAsynchronously(this, () -> {
			logger.info("All plugins are loaded, building the section tree.");
			sectionManager.buildSectionTree();
			logger.info("Section tree built.");
		}, configuration.sectionTree_buildDelayTicks);

		ProdriversCommons.init(this);

		logger.info("" + this.getDescription().getName() + " has been enabled!");
	}
}
