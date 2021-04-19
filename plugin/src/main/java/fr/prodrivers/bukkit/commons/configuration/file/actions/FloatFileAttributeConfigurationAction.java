package fr.prodrivers.bukkit.commons.configuration.file.actions;

import fr.prodrivers.bukkit.commons.configuration.file.AbstractFileAttributeConfiguration;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class FloatFileAttributeConfigurationAction extends ObjectFileConfigurationAction {
	public FloatFileAttributeConfigurationAction( FileConfiguration configuration ) {
		super( configuration );
	}

	@Override
	public Class<?>[] getTypes() {
		return new Class<?>[]{ Float.class, float.class };
	}

	@Override
	public Object get( Field field ) {
		return (float) configuration.getDouble( AbstractFileAttributeConfiguration.filterFieldName( field.getName() ) );
	}
}
