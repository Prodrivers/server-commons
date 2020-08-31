package fr.prodrivers.bukkit.commons.configuration.file.actions;

import fr.prodrivers.bukkit.commons.configuration.file.AbstractFileAttributeConfiguration;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class MessageListFileAttributeConfigurationAction extends ObjectFileConfigurationAction {
	public MessageListFileAttributeConfigurationAction( AbstractFileAttributeConfiguration configuration ) {
		super( configuration );
	}

	@Override
	public Class<?>[] getTypes() {
		return new Class<?>[]{ List.class };
	}

	@Override
	public Object get( Field field ) {
		return configClass.getConfiguration()
				.getStringList( field.getName() )
				.parallelStream()
				.map( msg -> ( msg != null ? ChatColor.translateAlternateColorCodes( '&', msg ) : null ) )
				.collect( Collectors.toList() );
	}

	@Override
	public void set( Field field, Object value ) {
		configClass.getConfiguration().set( field.getName(), value );
	}

	@Override
	public void setDefault( Field field, Object value ) {
		configClass.getConfiguration().addDefault( field.getName(), value );
	}
}
