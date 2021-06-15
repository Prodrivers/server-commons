package fr.prodrivers.bukkit.commons.configuration.file;

import fr.prodrivers.bukkit.commons.configuration.AbstractAttributeConfiguration;
import fr.prodrivers.bukkit.commons.configuration.file.actions.*;
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
		super.init();
	}

	/**
	 * Field name fitler. Allows to translate from field name to configuration path.
	 * Default behavior is to replace every "_" with ".", meaning that every "_" will actually separate into sub-fields.
	 *
	 * @param fieldName Field name
	 * @return Filtered field name
	 */
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
