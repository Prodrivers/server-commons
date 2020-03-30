package fr.prodrivers.bukkit.commons.configuration.file;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class FileAttributeConfiguration extends AbstractFileAttributeConfiguration {
	private File configurationFile;

	public FileAttributeConfiguration( File configurationFile ) {
		super();
		this.configurationFile = configurationFile;

		try {
			if( !configurationFile.exists() ) {
				if( configurationFile.getParentFile().exists() || configurationFile.getParentFile().mkdirs() ) {
					if( !configurationFile.createNewFile() ) {
						System.err.println( "Could not create " + getClass().getName() + "'s " + configurationFile.getName() + " file. Make sure your server can create files in the plugin's personal directory." );
					}
				} else {
					System.err.println( "Could not create " + getClass().getName() + "'s " + configurationFile.getName() + " file's parent folder. Make sure your server can create directories in the plugin's personal directory." );
				}
			}

			configuration = YamlConfiguration.loadConfiguration( configurationFile );
		} catch( Throwable e ) {
			System.err.println( "Error while loading " + getClass().getName() + "'s " + configurationFile.getName() + " file configuration." );
			e.printStackTrace();
		}
	}

	@Override
	public void save() {
		try {
			configuration.save( configurationFile );
		} catch( Throwable ex ) {
			System.err.println( "Error while saving " + getClass().getName() + "'s " + configurationFile.getName() + " file configuration." );
			ex.printStackTrace();
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
			configuration = YamlConfiguration.loadConfiguration( configurationFile );
			super.reload();
		} catch( Throwable e ) {
			System.err.println( "Error while reloading " + getClass().getName() + "'s " + configurationFile.getName() + " file configuration." );
			e.printStackTrace();
		}
	}
}
