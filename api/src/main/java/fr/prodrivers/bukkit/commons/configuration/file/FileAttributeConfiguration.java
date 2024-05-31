package fr.prodrivers.bukkit.commons.configuration.file;

import java.io.File;
import java.util.logging.Logger;

/**
 * Fully managed, field-based, Bukkit's FileConfiguration backed configuration class for Prodrivers plugins.
 * <p>
 * FileAttributeConfiguration extends {@link AbstractFileAttributeConfiguration} to handle all the loading and saving
 * parts left to the programmer.
 * No action is required apart from providing a valid File instance that points to the YAML file to be used, and calling
 * {@link #init()}.
 * You, of course, still need to make call by yourself to {@link #reload()} and {@link #save()} when required.
 * <p>
 * {@link fr.prodrivers.bukkit.commons.annotations.ExcludedFromConfiguration} annotation allows specific fields not to
 * be used by the field processor.
 * As with every {@link fr.prodrivers.bukkit.commons.configuration.AbstractAttributeConfiguration} derivative,
 * {@link #init()} have to be called immediately after constructing the object, either at the end of the constructor or
 * outside of it.
 */

public class FileAttributeConfiguration extends AbstractFileAttributeConfiguration {
	/**
	 * Fully managed, field-based, Bukkit's FileConfiguration backed configuration class constructor.
	 *
	 * @param logger            Logger to use
	 * @param configurationFile YAML file to use for storage
	 */
	public FileAttributeConfiguration(Logger logger, File configurationFile) {
		super(logger);
		throw new UnsupportedOperationException();
	}
}
