package fr.prodrivers.minecraft.commons.configuration.file;

import fr.prodrivers.minecraft.commons.annotations.ExcludedFromConfiguration;
import fr.prodrivers.minecraft.commons.configuration.AbstractAttributeConfiguration;

import java.io.File;

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
