package fr.prodrivers.bukkit.commons.configuration.file;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileAttributeConfiguration extends AbstractFileAttributeConfiguration {
	private final File configurationFile;

	public FileAttributeConfiguration(Logger logger, File configurationFile) {
		super(logger);
		this.configurationFile = configurationFile;

		try {
			if(!configurationFile.exists()) {
				if(configurationFile.getParentFile().exists() || configurationFile.getParentFile().mkdirs()) {
					if(!configurationFile.createNewFile()) {
						this.logger.severe("Could not create " + getClass().getName() + "'s " + configurationFile.getName() + " file. Make sure your server can create files in the plugin's personal directory.");
					}
				} else {
					this.logger.severe("Could not create " + getClass().getName() + "'s " + configurationFile.getName() + " file's parent folder. Make sure your server can create directories in the plugin's personal directory.");
				}
			}

			configuration = YamlConfiguration.loadConfiguration(configurationFile);
		} catch(Exception e) {
			this.logger.log(Level.SEVERE, "Error while loading " + getClass().getName() + "'s " + configurationFile.getName() + " file configuration.", e);
		}
	}

	@Override
	public void save() {
		try {
			configuration.save(configurationFile);
		} catch(IOException e) {
			this.logger.log(Level.SEVERE, "Error while saving " + getClass().getName() + "'s " + configurationFile.getName() + " file configuration.", e);
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
			this.logger.log(Level.SEVERE, "Error while reloading " + getClass().getName() + "'s " + configurationFile.getName() + " file configuration.", e);
		}
	}
}
