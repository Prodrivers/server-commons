package fr.prodrivers.bukkit.commons.configuration.file;

import fr.prodrivers.bukkit.commons.configuration.AbstractAttributeConfiguration;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.logging.Logger;

/**
 * Field-based, Bukkit's {@link FileConfiguration} backed configuration framework for Prodrivers plugins.
 * <p>
 * AbstractFileAttributeConfiguration extends {@link AbstractAttributeConfiguration} to uses Bukkit's own
 * {@link FileConfiguration} class as a storage. It provides all the required configuration actions, and most logic for
 * loading, saving and saving defaults.
 * <p>
 * In your constructor, after {@code super()}, you have to provide a valid {@link FileConfiguration} instance in the
 * configuration field.
 * Override {@link #save()}/{@link #saveDefaults()} to account for your FileConfiguration saving (set is already
 * handled).
 * Override {@link #reload()} to account for your {@link FileConfiguration} reload.
 * Keep in mind that, in both cases, the super method have to be called before doing any saving on your own.
 * <p>
 * {@link fr.prodrivers.bukkit.commons.annotations.ExcludedFromConfiguration} annotation allows specific fields not to
 * be used by the field processor.
 * As with every {@link fr.prodrivers.bukkit.commons.configuration.AbstractAttributeConfiguration} derivative,
 * {@link #init()} have to be called immediately after constructing the object, either at the end of the constructor or
 * outside of it.
 */
public abstract class AbstractFileAttributeConfiguration extends AbstractAttributeConfiguration {
	/**
	 * FileConfiguration used by this AbstractFileAttributeConfiguration instance.
	 */
	protected FileConfiguration configuration;

	/**
	 * Creates a new attributed and file-based configuration instance.
	 *
	 * @param logger Logger to use
	 */
	public AbstractFileAttributeConfiguration(Logger logger) {
		super(logger);
		throw new UnsupportedOperationException();
	}

	/**
	 * Field name filter. Allows translating from field name to configuration path.
	 * Default behavior is to replace every "_" with ".", meaning that every "_" will actually separate into subfields.
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
