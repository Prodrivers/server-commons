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
	@ExcludedFromConfiguration
	private Plugin plugin;
	@ExcludedFromConfiguration
	private Chat chat;
	@ExcludedFromConfiguration
	private Messages messages;

	public Configuration( Plugin plugin ) {
		this( plugin, Messages.class );
	}

	public Configuration( Plugin plugin, Class<? extends Messages> messagesClass ) {
		this( plugin, messagesClass, null );
	}

	public Configuration( Plugin plugin, Class<? extends Messages> messagesClass, Chat chat ) {
		super();
		this.plugin = plugin;
		this.configuration = this.plugin.getConfig();
		this.chat = chat;
		this.messages = null;
		if( messagesClass != Messages.class ) {
			initMessages( messagesClass );
		}
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
