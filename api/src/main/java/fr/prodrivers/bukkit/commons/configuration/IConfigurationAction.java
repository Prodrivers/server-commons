package fr.prodrivers.bukkit.commons.configuration;

import java.lang.reflect.Field;

/**
 * Configuration Action interface.
 * <p>
 * Configuration Actions are called by configurations when processing configuration fields, and, for a determined type,
 * provide getters and setters for the underlying storage mechanism, abstracting away storage concerns from the core
 * logic.
 */
public interface IConfigurationAction {
	/**
	 * Returns an array of classes (types) that this configuration action can handle.
	 *
	 * @return Supported types
	 */
	Class<?>[] getTypes();

	/**
	 * Returns, for a provided field, the value stored by the configuration action's storage mechanism.
	 *
	 * @param field Requested field
	 * @return Field's stored value
	 */
	Object get(Field field);

	/**
	 * Sets a provided field's value to the configuration action's storage mechanism.
	 *
	 * @param field Requested field
	 * @param value Field's value to store
	 */
	void set(Field field, Object value);

	/**
	 * Sets a provided field's default value to the configuration action's storage mechanism.
	 *
	 * @param field Requested field
	 * @param value Field's default value to store
	 */
	void setDefault(Field field, Object value);
}
