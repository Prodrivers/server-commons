package fr.prodrivers.bukkit.commons.configuration.file.actions;

import fr.prodrivers.bukkit.commons.configuration.IConfigurationAction;
import fr.prodrivers.bukkit.commons.configuration.file.AbstractFileAttributeConfiguration;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.lang.reflect.Field;

public class LocationFileConfigurationAction implements IConfigurationAction {
	private AbstractFileAttributeConfiguration configClass;

	public LocationFileConfigurationAction( AbstractFileAttributeConfiguration configuration ) {
		configClass = configuration;
	}

	@Override
	public Class<?>[] getTypes() {
		return new Class<?>[]{ Location.class };
	}

	@Override
	public Object get( Field field ) {
		String key = AbstractFileAttributeConfiguration.filterFieldName( field.getName() );
		if( configClass.getConfiguration().isSet( key + ".world" ) && configClass.getConfiguration().isSet( key + ".X" ) && configClass.getConfiguration().isSet( key + ".Y" ) && configClass.getConfiguration().isSet( key + ".Z" ) ) {
			if( configClass.getConfiguration().isSet( key + ".yaw" ) && configClass.getConfiguration().isSet( key + ".pitch" ) ) {
				return new Location(
					Bukkit.getWorld( configClass.getConfiguration().getString( key + ".world" ) ),
					configClass.getConfiguration().getDouble( key + ".X" ),
					configClass.getConfiguration().getDouble( key + ".Y" ),
					configClass.getConfiguration().getDouble( key + ".Z" ),
					(float) configClass.getConfiguration().getDouble( key + ".yaw" ),
					(float) configClass.getConfiguration().getDouble( key + ".pitch" )
				);
			} else {
				return new Location(
					Bukkit.getWorld( configClass.getConfiguration().getString( key + ".world" ) ),
					configClass.getConfiguration().getDouble( key + ".X" ),
					configClass.getConfiguration().getDouble( key + ".Y" ),
					configClass.getConfiguration().getDouble( key + ".Z" )
				);
			}
		} else {
			return null;
		}
	}

	@Override
	public void set( Field field, Object value ) {
		String key = AbstractFileAttributeConfiguration.filterFieldName( field.getName() );
		if( value != null ) {
			configClass.getConfiguration().set( key + ".world", ( ( (Location) value ).getWorld() != null ? ( (Location) value ).getWorld().getName() : "" ) );
			configClass.getConfiguration().set( key + ".X", ( (Location) value ).getX() );
			configClass.getConfiguration().set( key + ".Y", ( (Location) value ).getY() );
			configClass.getConfiguration().set( key + ".Z", ( (Location) value ).getZ() );
			configClass.getConfiguration().set( key + ".yaw", ( (Location) value ).getYaw() );
			configClass.getConfiguration().set( key + ".pitch", ( (Location) value ).getPitch() );
		} else {
			configClass.getConfiguration().set( key, null );
		}
	}

	@Override
	public void setDefault( Field field, Object value ) {
		String key = AbstractFileAttributeConfiguration.filterFieldName( field.getName() );
		if( value != null ) {
			configClass.getConfiguration().addDefault( key + ".world", ( ( (Location) value ).getWorld() != null ? ( (Location) value ).getWorld().getName() : "" ) );
			configClass.getConfiguration().addDefault( key + ".X", ( (Location) value ).getX() );
			configClass.getConfiguration().addDefault( key + ".Y", ( (Location) value ).getY() );
			configClass.getConfiguration().addDefault( key + ".Z", ( (Location) value ).getZ() );
			configClass.getConfiguration().addDefault( key + ".yaw", ( (Location) value ).getYaw() );
			configClass.getConfiguration().addDefault( key + ".pitch", ( (Location) value ).getPitch() );
		}
	}
}
