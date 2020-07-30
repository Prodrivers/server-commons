package fr.prodrivers.bukkit.commons.plugin;

import fr.prodrivers.bukkit.commons.Chat;
import fr.prodrivers.bukkit.commons.commands.CommandsManager;
import fr.prodrivers.bukkit.commons.events.PlayerOpenOwnInventoryImplementationEventListener;
import fr.prodrivers.bukkit.commons.parties.PartyManager;
import fr.prodrivers.bukkit.commons.sections.SectionManager;
import fr.prodrivers.bukkit.commons.storage.SQLProvider;
import fr.prodrivers.bukkit.commons.storage.StorageProvider;
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

//import fr.prodrivers.bukkit.commons.cache.CacheProvider;

public class Main extends JavaPlugin implements Listener {
	private static EConfiguration configuration;
	private static EMessages messages;
	private static Chat chat;
	private static Main plugin;

	public static final Logger logger = Logger.getLogger( "ProdriversCommons" );

	public static EConfiguration getConfiguration() {
		return configuration;
	}

	public static EMessages getMessages() {
		return messages;
	}

	public static Chat getChat() {
		return chat;
	}

	public static Main getPlugin() {
		return plugin;
	}

	@Override
	public void onDisable() {
		PluginDescriptionFile plugindescription = this.getDescription();
		configuration.save();
		try {
			StorageProvider.close();
		} catch( IOException ex ) {
			logger.severe( "[ProdriversCommons] Error while closing storage provider: " + ex.getLocalizedMessage() );
		}
		try {
			SQLProvider.close();
		} catch( IOException ex ) {
			logger.severe( "[ProdriversCommons] Error while closing storage provider: " + ex.getLocalizedMessage() );
		}
		logger.info( "[ProdriversCommons] " + plugindescription.getName() + " has been disabled!" );
	}

	private void registerLibraries() {
		try {
			logger.info( "[ProdriversCommons] Loading libraries..." );
			URLClassLoader urlClassLoader = (URLClassLoader) Thread.currentThread().getContextClassLoader();
			Class urlClass = URLClassLoader.class;
			Method method = urlClass.getDeclaredMethod( "addURL", URL.class );
			method.setAccessible( true );

			String pluginFolderPath = getDataFolder().getAbsolutePath();
			File libFolder = new File( pluginFolderPath + File.separator + "libs" );
			File[] libFiles = libFolder.listFiles( ( file, fileName ) -> fileName.endsWith( ".jar" ) );
			int count = 0;
			if( libFiles != null ) {
				for( File libFile : libFiles ) {
					try {
						method.invoke( urlClassLoader, libFile.toURI().toURL() );
						logger.info( "[ProdriversCommons] Loading library " + libFile.getName() );
						count++;
					} catch( IllegalAccessException | InvocationTargetException | MalformedURLException e ) {
						logger.severe( "[ProdriversCommons] Error while loading library " + libFile.getName() + ": " + e.getLocalizedMessage() );
					}
				}
			}
			logger.info( "[ProdriversCommons] Loaded " + count + " libraries." );
		} catch( NoSuchMethodException e ) {
			logger.severe( "[ProdriversCommons] Error while loading libraries (reflection error): " + e.getLocalizedMessage() );
			throw new InstantiationError( "Library loading failed" );
		}
	}

	@Override
	public void onEnable() {
		plugin = this;

		logger.info( "Java version is " + System.getProperty( "java.version" ) + "." );
		if( !System.getProperty( "java.version" ).startsWith( "1.8" ) ) {
			logger.severe( "ProdriversCommons currently only supports Java 1.8." );
			System.exit( 10 );
		}

		registerLibraries();

		getServer().getPluginManager().registerEvents( this, this );

		PluginDescriptionFile plugindescription = this.getDescription();

		chat = new Chat( plugindescription.getName() );
		configuration = new EConfiguration( this, EMessages.class, chat );
		configuration.init();
		messages = (EMessages) configuration.getMessages();

		//cacheProvider = new CacheProvider();
		StorageProvider.init();
		SQLProvider.init();

		SectionManager.init( this );
		PartyManager.init( this );
		CommandsManager.init( this );

		PlayerOpenOwnInventoryImplementationEventListener.init( this );

		logger.info( "[ProdriversCommons] " + plugindescription.getName() + " has been enabled!" );
	}

	public void reload() {}
}
