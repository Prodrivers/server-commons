package fr.prodrivers.bukkit.commons.plugin;

import com.google.inject.Guice;
import com.google.inject.Injector;
import fr.prodrivers.bukkit.commons.Chat;
import fr.prodrivers.bukkit.commons.Log;
import fr.prodrivers.bukkit.commons.commands.CommandsModule;
import fr.prodrivers.bukkit.commons.configuration.Configuration;
import fr.prodrivers.bukkit.commons.configuration.Messages;
import fr.prodrivers.bukkit.commons.parties.PartyManager;
import fr.prodrivers.bukkit.commons.parties.PartyModule;
import fr.prodrivers.bukkit.commons.sections.MainHub;
import fr.prodrivers.bukkit.commons.sections.SectionManager;
import fr.prodrivers.bukkit.commons.storage.SQLProvider;
import fr.prodrivers.bukkit.commons.storage.StorageModule;
import fr.prodrivers.bukkit.commons.storage.StorageProvider;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.logging.Logger;

public class Main extends JavaPlugin implements Listener {
	private static Main instance;

	private EConfiguration configuration;
	private EMessages messages;
	private Chat chat;
	private Logger logger;

	private SectionManager sectionManager;
	private PartyManager partyManager;
	private SQLProvider sqlProvider;

	public static Main getInstance() {
		return instance;
	}

	public EConfiguration getConfiguration() {
		return configuration;
	}

	public EMessages getMessages() {
		return messages;
	}

	public Chat getChat() {
		return chat;
	}

	public SectionManager getSectionManager() {
		return sectionManager;
	}

	public PartyManager getPartyManager() {
		return partyManager;
	}

	public SQLProvider getSqlProvider() {
		return sqlProvider;
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
			SQLProvider.close();
		} catch(IOException ex) {
			logger.severe("Error while closing storage provider: " + ex.getLocalizedMessage());
		}
		logger.info("" + plugindescription.getName() + " has been disabled!");
	}

	private void registerLibraries() {
		try {
			logger.info("Loading libraries...");
			URLClassLoader urlClassLoader = (URLClassLoader) Thread.currentThread().getContextClassLoader();
			Class urlClass = URLClassLoader.class;
			Method method = urlClass.getDeclaredMethod("addURL", URL.class);
			method.setAccessible(true);

			String pluginFolderPath = getDataFolder().getAbsolutePath();
			File libFolder = new File(pluginFolderPath + File.separator + "libs");
			File[] libFiles = libFolder.listFiles((file, fileName) -> fileName.endsWith(".jar"));
			int count = 0;
			if(libFiles != null) {
				for(File libFile : libFiles) {
					try {
						method.invoke(urlClassLoader, libFile.toURI().toURL());
						logger.info("Loading library " + libFile.getName());
						count++;
					} catch(IllegalAccessException | InvocationTargetException | MalformedURLException e) {
						logger.severe("Error while loading library " + libFile.getName() + ": " + e.getLocalizedMessage());
					}
				}
			}
			logger.info("Loaded " + count + " libraries.");
		} catch(NoSuchMethodException e) {
			logger.severe("Error while loading libraries (reflection error): " + e.getLocalizedMessage());
			throw new InstantiationError("Library loading failed");
		}
	}

	@Override
	public void onEnable() {
		instance = this;
		logger = getLogger();
		Log.init(logger);

		logger.info("Java version is " + System.getProperty("java.version") + ".");
		if(!System.getProperty("java.version").startsWith("1.8")) {
			logger.severe("ProdriversCommons currently only supports Java 1.8.");
			System.exit(10);
		}

		registerLibraries();

		Injector injector = Guice.createInjector(
				new PluginModule(this),
				new CommandsModule(),
				new StorageModule(),
				new PartyModule()
		);

		chat = injector.getInstance(Chat.class);
		configuration = (EConfiguration) injector.getInstance(Configuration.class);
		messages = (EMessages) injector.getInstance(Messages.class);

		Log.setLevel(configuration);

		getServer().getPluginManager().registerEvents(this, this);

		sqlProvider = injector.getInstance(SQLProvider.class);

		sectionManager = injector.getInstance(SectionManager.class);
		partyManager = injector.getInstance(PartyManager.class);

		MainHub hub = injector.getInstance(MainHub.class);
		sectionManager.register(hub);

		Bukkit.getScheduler().runTaskLaterAsynchronously(this, () -> {
			logger.info("All plugins are loaded, building the section tree.");
			sectionManager.buildSectionTree();
			logger.info("Section tree built.");
		}, configuration.sectionTree_buildDelayTicks);

		logger.info("" + this.getDescription().getName() + " has been enabled!");
	}
}
