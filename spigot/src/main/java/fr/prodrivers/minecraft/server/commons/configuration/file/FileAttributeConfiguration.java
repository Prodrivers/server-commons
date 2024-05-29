package fr.prodrivers.minecraft.server.commons.configuration.file;

import fr.prodrivers.minecraft.server.commons.Log;
import fr.prodrivers.minecraft.server.commons.annotations.ExcludedFromConfiguration;
import fr.prodrivers.minecraft.server.commons.configuration.AbstractAttributeConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * Fully managed, field-based, Bukkit's FileConfiguration backed configuration class for Prodrivers plugins.
 * <p>
 * FileAttributeConfiguration extends {@link AbstractFileAttributeConfiguration} to handle all the loading and saving
 * parts left to the programmer.
 * No action is required apart from providing a valid File instance that points to the YAML file to be used, and calling
 * {@link #init()}.
 * You, of course, still need to make call by yourself to {@link #reload()} and {@link #save()} when required.
 * <p>
 * {@link ExcludedFromConfiguration} annotation allows specific fields not to
 * be used by the field processor.
 * As with every {@link AbstractAttributeConfiguration} derivative,
 * {@link #init()} have to be called immediately after constructing the object, either at the end of the constructor or
 * outside of it.
 */
public class FileAttributeConfiguration extends AbstractFileAttributeConfiguration {
	private final File configurationFile;

	/**
	 * Fully managed, field-based, Bukkit's FileConfiguration backed configuration class constructor.
	 *
	 * @param configurationFile YAML file to use for storage
	 */
	public FileAttributeConfiguration(File configurationFile) {
		super();
		this.configurationFile = configurationFile;

		try {
			if(!configurationFile.exists()) {
				if(configurationFile.getParentFile().exists() || configurationFile.getParentFile().mkdirs()) {
					if(!configurationFile.createNewFile()) {
						Log.severe("Could not create " + getClass().getName() + "'s " + configurationFile.getName() + " file. Make sure your server can create files in the plugin's personal directory.");
					}
				} else {
					Log.severe("Could not create " + getClass().getName() + "'s " + configurationFile.getName() + " file's parent folder. Make sure your server can create directories in the plugin's personal directory.");
				}
			}

			configuration = YamlConfiguration.loadConfiguration(configurationFile);
		} catch(Exception e) {
			Log.severe("Error while loading " + getClass().getName() + "'s " + configurationFile.getName() + " file configuration.", e);
		}
	}

	@Override
	public void save() {
		try {
			configuration.save(configurationFile);
		} catch(IOException e) {
			Log.severe("Error while saving " + getClass().getName() + "'s " + configurationFile.getName() + " file configuration.", e);
		}
	}

	@Override
	protected void saveDefaults() {
		super.saveDefaults();

		save();
	}

	@Override
	public void reload() {
		try {
			configuration = YamlConfiguration.loadConfiguration(configurationFile);
			super.reload();
		} catch(Exception e) {
			Log.severe("Error while reloading " + getClass().getName() + "'s " + configurationFile.getName() + " file configuration.", e);
		}
	}
}
