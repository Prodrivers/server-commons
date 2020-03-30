package fr.prodrivers.bukkit.commons.configuration;

import fr.prodrivers.bukkit.commons.Chat;
import fr.prodrivers.bukkit.commons.annotations.ExcludedFromConfiguration;
import fr.prodrivers.bukkit.commons.configuration.file.AbstractFileAttributeConfiguration;
import fr.prodrivers.bukkit.commons.plugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Configuration extends AbstractFileAttributeConfiguration {
	/*private Plugin plugin;

	private FileConfiguration configuration;
	FileConfiguration messagesConfiguration;

	private Chat chat;

	private Messages messages;

	public Configuration( Plugin plugin, Class<? extends Messages> messagesClass, Chat chat ) {
		this.plugin = plugin;
		this.configuration = this.plugin.getConfig();
		this.chat = chat;

		this.configuration.options().copyDefaults( true );

		this.plugin.saveConfig();

		initMessagesConfiguration();
		initMessages( messagesClass );
		load();
	}

	private void initMessages( Class<? extends Messages> messagesClass ) {
		try {
			Constructor<? extends Messages> ctor = messagesClass.getConstructor( Plugin.class );
			this.messages = ctor.newInstance( this.plugin );
		} catch( NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException ex ) {
			System.err.println( "Error while loading " + plugin.getDescription().getName() + " messages configuration." );
			ex.printStackTrace();
		}
	}

	private void initMessagesConfiguration() {
		try {
			File pluginmessagesfile = new File( this.plugin.getDataFolder(), "messages.yml" );

			if( !pluginmessagesfile.exists() ) {
				if( pluginmessagesfile.getParentFile().mkdirs() ) {
					if( !pluginmessagesfile.createNewFile() ) {
						System.err.println( "Could not create "  + plugin.getDescription().getName() +  " messages file. Make sure your server can create files in the plugin's personal directory." );
					}
				} else {
					System.err.println( "Could not create "  + plugin.getDescription().getName() +  " messages folder. Make sure your server can create directories in the plugins directory." );
				}
			}

			this.messagesConfiguration = YamlConfiguration.loadConfiguration( pluginmessagesfile );
		} catch( Exception e ) {
			System.err.println( "Error while loading " + plugin.getDescription().getName() + " messages configuration." );
			e.printStackTrace();
		}
	}

	private void reloadMessagesConfiguration() {
		try {
			File pluginmessagesfile = new File( this.plugin.getDataFolder(), "messages.yml" );
			this.messagesConfiguration = YamlConfiguration.loadConfiguration( pluginmessagesfile );
		} catch( Exception e ) {
			e.printStackTrace();
		}
	}

	void saveMessages() {
		File pluginmessagesfile = new File( this.plugin.getDataFolder(), "messages.yml" );

		try {
			this.messagesConfiguration.save( pluginmessagesfile );
		} catch( IOException ex ) {
			System.err.println( "Error while saving " + plugin.getDescription().getName() + " messages configuration." );
			ex.printStackTrace();
		}
	}

	private void loadChat() {
		if( chat != null )
			this.chat.load( this );
	}

	protected void load() {
		loadChat();
	}

	public void reload() {
		this.plugin.reloadConfig();

		reloadMessagesConfiguration();
		this.messages.reload();
		loadChat();
	}

	public String getString( String key ) {
		return this.configuration.getString( key );
	}

	public Integer getInt( String key ) {
		return this.configuration.getInt( key );
	}

	public float getFloat( String key ) {
		return (float) this.configuration.getDouble( key );
	}

	public double getDouble( String key ) {
		return this.configuration.getDouble( key );
	}

	public boolean getBoolean( String key ) {
		return this.configuration.getBoolean( key );
	}

	public Location getLocation( String key ) {
		return new Location(
			Bukkit.getWorld( this.configuration.getString( key + ".world" ) ),
	        this.configuration.getDouble( key + ".X" ),
			this.configuration.getDouble( key + ".Y" ),
			this.configuration.getDouble( key + ".Z" ),
			(float) this.configuration.getDouble( key + ".yaw" ),
			(float) this.configuration.getDouble( key + ".ptich" )
		);
	}

	public void setLocation( String key, Location location ) {
		this.configuration.set( key + ".world", location.getWorld().getName() );
		this.configuration.set( key + ".X", location.getX() );
		this.configuration.set( key + ".Y", location.getY() );
		this.configuration.set( key + ".Z", location.getZ() );
		this.configuration.set( key + ".yaw", location.getYaw() );
		this.configuration.set( key + ".pitch", location.getPitch() );
	}

	public FileConfiguration getConfiguration() {
		return this.configuration;
	}

	public Messages getMessages() {
		return this.messages;
	}*/

	@ExcludedFromConfiguration
	private Plugin plugin;
	@ExcludedFromConfiguration
	private Chat chat;
	@ExcludedFromConfiguration
	private Messages messages;

	public Configuration( Plugin plugin, Class<? extends Messages> messagesClass, Chat chat ) {
		super();
		this.plugin = plugin;
		this.configuration = this.plugin.getConfig();
		this.chat = chat;
		initMessages( messagesClass );
	}

	private void initMessages( Class<? extends Messages> messagesClass ) {
		try {
			Constructor<? extends Messages> ctor = messagesClass.getConstructor( Plugin.class );
			this.messages = ctor.newInstance( this.plugin );
		} catch( NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException ex ) {
			System.err.println( "Error while loading " + plugin.getDescription().getName() + " messages configuration." );
			ex.printStackTrace();
		}
	}

	@Override
	public void init() {
		super.init();
		if( this.messages != null ) {
			this.messages.init();
			if( this.chat != null )
				this.chat.load( this );
		}
	}

	@Override
	public void save() {
		super.save();

		this.plugin.saveConfig();
		if( this.messages != null )
			this.messages.save();
	}

	@Override
	protected void saveDefaults() {
		super.saveDefaults();

		this.plugin.saveConfig();
	}

	@Override
	public void reload() {
		this.plugin.reloadConfig();
		super.reload();
		if( this.messages != null )
			this.messages.reload();
		if( chat != null )
			this.chat.load( this );
	}

	public Messages getMessages() {
		return this.messages;
	}
}
