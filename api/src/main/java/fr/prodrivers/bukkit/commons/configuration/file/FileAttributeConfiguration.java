package fr.prodrivers.bukkit.commons.configuration.file;

import java.io.File;

/**
 * Fully managed, field-based, Bukkit's FileConfiguration backed configuration class for Prodrivers plugins.
 * <p>
 * FileAttributeConfiguration extends AbstractFileAttributeConfiguration to handle all the loading and saving parts left to the programmer.
 * No action is required apart from providing a valid File instance that points to the YAML file to be used, and calling init().
 * You, of course, still need to make call by yourself to reload() and save() when required.
 * <p>
 * ExcludedFromConfiguration annotation allows specific fields not to be used by the field processor.
 * As with every AbstractAttributeConfiguration derivative, init() have to be called immediately after constructing the object.
 */

public class FileAttributeConfiguration extends AbstractFileAttributeConfiguration {
	/**
	 * Fully managed, field-based, Bukkit's FileConfiguration backed configuration class constructor.
	 *
	 * @param configurationFile YAML file to use for storage
	 */
	public FileAttributeConfiguration(File configurationFile) {
		super();
		throw new UnsupportedOperationException();
	}
}
