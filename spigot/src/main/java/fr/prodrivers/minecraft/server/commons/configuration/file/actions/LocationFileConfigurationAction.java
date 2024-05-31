package fr.prodrivers.minecraft.server.commons.configuration.file.actions;

import fr.prodrivers.minecraft.server.commons.configuration.IConfigurationAction;
import fr.prodrivers.minecraft.server.commons.configuration.file.AbstractFileAttributeConfiguration;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.lang.reflect.Field;

@Deprecated
public class LocationFileConfigurationAction implements IConfigurationAction {
	private final FileConfiguration configuration;

	public LocationFileConfigurationAction(FileConfiguration configuration) {
		this.configuration = configuration;
	}

	@Override
	public Class<?>[] getTypes() {
		return new Class<?>[]{Location.class};
	}

	@Override
	public Object get(Field field) {
		String key = AbstractFileAttributeConfiguration.filterFieldName(field.getName());
		if(this.configuration.isSet(key + ".world") && this.configuration.isSet(key + ".X") && this.configuration.isSet(key + ".Y") && this.configuration.isSet(key + ".Z")) {
			if(this.configuration.isSet(key + ".yaw") && this.configuration.isSet(key + ".pitch")) {
				return new Location(
						Bukkit.getWorld(this.configuration.getString(key + ".world")),
						this.configuration.getDouble(key + ".X"),
						this.configuration.getDouble(key + ".Y"),
						this.configuration.getDouble(key + ".Z"),
						(float) this.configuration.getDouble(key + ".yaw"),
						(float) this.configuration.getDouble(key + ".pitch")
				);
			} else {
				return new Location(
						Bukkit.getWorld(this.configuration.getString(key + ".world")),
						this.configuration.getDouble(key + ".X"),
						this.configuration.getDouble(key + ".Y"),
						this.configuration.getDouble(key + ".Z")
				);
			}
		} else {
			return null;
		}
	}

	@Override
	public void set(Field field, Object value) {
		String key = AbstractFileAttributeConfiguration.filterFieldName(field.getName());
		if(value != null) {
			this.configuration.set(key + ".world", (((Location) value).getWorld() != null ? ((Location) value).getWorld().getName() : ""));
			this.configuration.set(key + ".X", ((Location) value).getX());
			this.configuration.set(key + ".Y", ((Location) value).getY());
			this.configuration.set(key + ".Z", ((Location) value).getZ());
			this.configuration.set(key + ".yaw", ((Location) value).getYaw());
			this.configuration.set(key + ".pitch", ((Location) value).getPitch());
		} else {
			this.configuration.set(key, null);
		}
	}

	@Override
	public void setDefault(Field field, Object value) {
		String key = AbstractFileAttributeConfiguration.filterFieldName(field.getName());
		if(value != null) {
			this.configuration.addDefault(key + ".world", (((Location) value).getWorld() != null ? ((Location) value).getWorld().getName() : ""));
			this.configuration.addDefault(key + ".X", ((Location) value).getX());
			this.configuration.addDefault(key + ".Y", ((Location) value).getY());
			this.configuration.addDefault(key + ".Z", ((Location) value).getZ());
			this.configuration.addDefault(key + ".yaw", ((Location) value).getYaw());
			this.configuration.addDefault(key + ".pitch", ((Location) value).getPitch());
		}
	}
}
