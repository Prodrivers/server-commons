package fr.prodrivers.bukkit.commons.configuration.file.actions;

import fr.prodrivers.bukkit.commons.configuration.file.AbstractFileAttributeConfiguration;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;

import java.lang.reflect.Field;
import java.util.Map;

public class MapFileAttributeConfigurationAction extends ObjectFileConfigurationAction {
	public MapFileAttributeConfigurationAction( FileConfiguration configuration ) {
		super( configuration );
	}

	@Override
	public Class<?>[] getTypes() {
		return new Class<?>[]{ Map.class };
	}

	@Override
	public Object get( Field field ) {
		return ( (MemorySection) configuration.get( AbstractFileAttributeConfiguration.filterFieldName( field.getName() ) ) ).getValues( false );
	}
}
