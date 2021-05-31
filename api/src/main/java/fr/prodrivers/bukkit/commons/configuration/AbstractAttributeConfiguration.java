package fr.prodrivers.bukkit.commons.configuration;

/**
 * Field-based configuration framework for Prodrivers plugins.
 * <p>
 * AbstractAttributeConfiguration lays the framework of configuration classes that uses fields to represents options, loading and saving theses fields using Configuration Actions.
 * Provide your own Configuration Actions, override init()/load()/save()/saveDefaults()/reload() as necessary to create a fully-managed field-based configuration class.
 * Call then reload() and save() in your plugin when required. Direct calling of load() and saveDefaults() is strongly discouraged.
 * <p>
 * If a specific class does not provide a Configuration Action for a field's type, AbstractAttributeConfiguration serialize it if possible, or resorts to toString/valueOf to store it nonetheless. It allows a wide variety of classes to be stored without providing explicit Configuration Actions.
 * As such, each class have to provide at least a String Configuration Action in order for AbstractConfigurationAction to work properly in the majority of cases.
 * <p>
 * ExcludedFromConfiguration annotation allows specific fields not to be used by the field processor.
 * As with every AbstractAttributeConfiguration derivative, init() have to be called immediately after constructing the object.
 */
public abstract class AbstractAttributeConfiguration {
	/**
	 * Register a Configuration Action.
	 *
	 * @param action IConfigurationAction instance to register
	 */
	protected void registerAction(IConfigurationAction action) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Init a field-based configuration.
	 * Registers all the default values and loads the configuration.
	 * have to be called immediately after the object's successful construction.
	 *
	 * @throws IllegalStateException Thrown when the field is either not accessible, of a not-supported type and neither serializable nor supporting toString/valueOf, or if the associated IConfigurationAction returned a value that cannot be used (invalid cast).
	 */
	public void init() throws IllegalStateException {
		throw new UnsupportedOperationException();
	}

	/**
	 * Saves a field-based configuration.
	 * Uses IConfigurationAction set to store them.
	 *
	 * @throws IllegalStateException Thrown when the field is either not accessible, of a not-supported type and neither serializable nor supporting toString/valueOf, or if the associated IConfigurationAction returned a value that cannot be used (invalid cast).
	 */
	public void save() throws IllegalStateException {
		throw new UnsupportedOperationException();
	}

	/**
	 * Reloads a field-based configuration using the IConfigurationAction reported values.
	 * Erases all pending changes.
	 *
	 * @throws IllegalStateException Thrown when the field is either not accessible, of a not-supported type and neither serializable nor supporting toString/valueOf, or if the associated IConfigurationAction returned a value that cannot be used (invalid cast).
	 */
	public void reload() throws IllegalStateException {
		throw new UnsupportedOperationException();
	}

	/**
	 * Save the default values of a field-based configuration.
	 * Uses IConfigurationAction setDefault to store them.
	 *
	 * @throws IllegalStateException Thrown when the field is either not accessible, of a not-supported type and neither serializable nor supporting toString/valueOf, or if the associated IConfigurationAction returned a value that cannot be used (invalid cast).
	 */
	protected void saveDefaults() throws IllegalStateException {
		throw new UnsupportedOperationException();
	}

	/**
	 * Loads (and only loads) a field-based configuration using the IConfigurationAction reported values.
	 * Erases all pending changes.
	 *
	 * @throws IllegalStateException Thrown when the field is either not accessible, of a not-supported type and neither serializable nor supporting toString/valueOf, or if the associated IConfigurationAction returned a value that cannot be used (invalid cast).
	 */
	protected void load() throws IllegalStateException {
		throw new UnsupportedOperationException();
	}
}
