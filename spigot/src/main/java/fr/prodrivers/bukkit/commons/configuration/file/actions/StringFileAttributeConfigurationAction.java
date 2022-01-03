package fr.prodrivers.bukkit.commons.configuration.file.actions;

import fr.prodrivers.bukkit.commons.configuration.file.AbstractFileAttributeConfiguration;
import org.bukkit.configuration.file.FileConfiguration;

import java.lang.reflect.Field;

public class StringFileAttributeConfigurationAction extends ObjectFileConfigurationAction {
	public StringFileAttributeConfigurationAction(FileConfiguration configuration) {
		super(configuration);
	}

	@Override
	public Class<?>[] getTypes() {
		return new Class<?>[]{String.class};
	}

	@Override
	public Object get(Field field) {
		return configuration.getString(AbstractFileAttributeConfiguration.filterFieldName(field.getName()));
	}

	@Override
	public void set(Field field, Object value) {
		configuration.set(AbstractFileAttributeConfiguration.filterFieldName(field.getName()), value);
	}

	@Override
	public void setDefault(Field field, Object value) {
		configuration.addDefault(AbstractFileAttributeConfiguration.filterFieldName(field.getName()), value);
	}
}
