package fr.prodrivers.bukkit.commons.configuration.file.actions;

import fr.prodrivers.bukkit.commons.configuration.file.AbstractFileAttributeConfiguration;
import org.bukkit.configuration.MemorySection;

import java.lang.reflect.Field;
import java.util.Map;

public class MessageMapFileAttributeConfigurationAction extends ObjectFileConfigurationAction {
	public MessageMapFileAttributeConfigurationAction( AbstractFileAttributeConfiguration configuration ) {
		super( configuration );
	}

	@Override
	public Class<?>[] getTypes() {
		return new Class<?>[]{ Map.class };
	}

	@Override
	public Object get( Field field ) {
		String name = field.getName();
		if( configClass.getConfiguration().get( name ) instanceof MemorySection ) {
			return ( (MemorySection) configClass.getConfiguration().get( name ) ).getValues( false );
		} else {
			return configClass.getConfiguration().get( name );
		}
	}
}
