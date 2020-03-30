package fr.prodrivers.bukkit.commons.configuration.file.actions;

import fr.prodrivers.bukkit.commons.configuration.file.AbstractFileAttributeConfiguration;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.lang.reflect.Field;

public class MaterialFileAttributeConfigurationAction extends ObjectFileConfigurationAction {
	public MaterialFileAttributeConfigurationAction( AbstractFileAttributeConfiguration configuration ) {
		super( configuration );
	}

	@Override
	public Class<?>[] getTypes() {
		return new Class<?>[]{ Material.class };
	}

	@Override
	public Object get( Field field ) {
		return Material.valueOf( configClass.getConfiguration().getString( AbstractFileAttributeConfiguration.filterFieldName( field.getName() ) ) );
	}

	@Override
	public void set( Field field, Object value ) {
		configClass.getConfiguration().set( AbstractFileAttributeConfiguration.filterFieldName( field.getName() ), value.toString() );
	}

	@Override
	public void setDefault( Field field, Object value ) {
		configClass.getConfiguration().addDefault( AbstractFileAttributeConfiguration.filterFieldName( field.getName() ), value.toString() );
	}
}
