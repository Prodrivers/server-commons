package fr.prodrivers.minecraft.commons.configuration.file;

import fr.prodrivers.minecraft.commons.configuration.AbstractAttributeConfiguration;
import fr.prodrivers.minecraft.commons.configuration.file.actions.*;
import org.bukkit.configuration.file.FileConfiguration;

public abstract class AbstractFileAttributeConfiguration extends AbstractAttributeConfiguration {
	protected FileConfiguration configuration;

	@Override
	protected void init() {
		registerAction(new ObjectFileConfigurationAction(this.configuration));
		registerAction(new StringFileAttributeConfigurationAction(this.configuration));
		registerAction(new LocationFileConfigurationAction(this.configuration));
		registerAction(new FloatFileAttributeConfigurationAction(this.configuration));
		registerAction(new ByteFileAttributeConfigurationAction(this.configuration));
		registerAction(new MaterialFileAttributeConfigurationAction(this.configuration));
		registerAction(new MapFileAttributeConfigurationAction(this.configuration));
		registerAction(new PropertiesFileAttributeConfigurationAction(this.configuration));
		registerAction(new ComponentFileAttributeConfigurationAction(this.configuration));
		super.init();
	}

	public static String filterFieldName(String fieldName) {
		return fieldName.replaceAll("_", ".");
	}

	@Override
	protected void saveDefaults() {
		super.saveDefaults();

		configuration.options().copyDefaults(true);
	}

	public FileConfiguration getConfiguration() {
		return configuration;
	}
}
