package fr.prodrivers.bukkit.commons.configuration.file;

import fr.prodrivers.bukkit.commons.configuration.AbstractAttributeConfiguration;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Field-based, Bukkit's FileConfiguration backed configuration framework for Prodrivers plugins.
 * <p>
 * AbstractFileAttributeConfiguration extends AbstractAttributeConfiguration to uses Bukkit's own FileConfiguration class as a storage.
 * It provides all the required configuration actions, and most logic for loading, saving and saving defaults.
 * <p>
 * In your constructor, after super(), you have to provide a valid FileConfiguration instance in the configuration field.
 * Override save()/saveDefault() to account for your FileConfiguration saving (set is already handled).
 * Override reload() to account for your FileConfiguration reload.
 * Keep in mind that, in both cases, the super method have to be called before doing any saving on your own.
 * <p>
 * ExcludedFromConfiguration annotation allows specific fields not to be used by the field processor.
 * As with every AbstractAttributeConfiguration derivative, init() have to be called immediately after constructing the object.
 */
public abstract class AbstractFileAttributeConfiguration extends AbstractAttributeConfiguration {
	/**
	 * FileConfiguration used by this AbstractFileAttributeConfiguration instance.
	 */
	protected FileConfiguration configuration;

	/**
	 * Field name fitler. Allows to translate from field name to configuration path.
	 * Default behavior is to replace every "_" with ".", meaning that every "_" will actually separate into sub-fields.
	 *
	 * @param fieldName Field name
	 * @return Filtered field name
	 */
	public static String filterFieldName(String fieldName) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Get the underlying FileConfiguration instance.
	 *
	 * @return FileConfiguration instance
	 */
	public FileConfiguration getConfiguration() {
		throw new UnsupportedOperationException();
	}
}
