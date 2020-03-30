package fr.prodrivers.bukkit.commons.configuration.file.actions;

import fr.prodrivers.bukkit.commons.configuration.IConfigurationAction;
import fr.prodrivers.bukkit.commons.configuration.file.AbstractFileAttributeConfiguration;
import org.bukkit.configuration.file.FileConfiguration;

import java.lang.reflect.Field;

public class ObjectFileConfigurationAction implements IConfigurationAction {
	protected AbstractFileAttributeConfiguration configClass;

	public ObjectFileConfigurationAction( AbstractFileAttributeConfiguration configuration ) {
		this.configClass = configuration;
	}

	@Override
	public Class<?>[] getTypes() {
		return new Class<?>[]{ Object.class };
	}

	@Override
	public Object get( Field field ) {
		return configClass.getConfiguration().get( AbstractFileAttributeConfiguration.filterFieldName( field.getName() ) );
	}

	@Override
	public void set( Field field, Object value ) {
		configClass.getConfiguration().set( AbstractFileAttributeConfiguration.filterFieldName( field.getName() ), value );
	}

	@Override
	public void setDefault( Field field, Object value ) {
		configClass.getConfiguration().addDefault( AbstractFileAttributeConfiguration.filterFieldName( field.getName() ), value );
	}
}
