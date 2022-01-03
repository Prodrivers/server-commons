package fr.prodrivers.bukkit.commons.configuration.file;

import fr.prodrivers.bukkit.commons.Log;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class FileAttributeConfiguration extends AbstractFileAttributeConfiguration {
	private final File configurationFile;

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
