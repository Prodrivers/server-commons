package fr.prodrivers.bukkit.commons.configuration.file.actions;

import fr.prodrivers.bukkit.commons.configuration.file.AbstractFileAttributeConfiguration;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.lang.reflect.Field;

public class MessageFileAttributeConfigurationAction extends ObjectFileConfigurationAction {
	public MessageFileAttributeConfigurationAction( AbstractFileAttributeConfiguration configuration ) {
		super( configuration );
	}

	@Override
	public Class<?>[] getTypes() {
		return new Class<?>[]{ String.class };
	}

	@Override
	public Object get( Field field ) {
		String msg = configClass.getConfiguration().getString( field.getName() );
		if( msg != null ) {
			String ret = ChatColor.translateAlternateColorCodes( '&', msg );
			return ret;
		}
		return "[NT_" + field.getName() + "]";
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
