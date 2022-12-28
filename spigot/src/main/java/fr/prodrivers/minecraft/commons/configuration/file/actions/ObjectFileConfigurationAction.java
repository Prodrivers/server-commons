package fr.prodrivers.minecraft.commons.configuration.file.actions;

import fr.prodrivers.minecraft.commons.configuration.IConfigurationAction;
import fr.prodrivers.minecraft.commons.configuration.file.AbstractFileAttributeConfiguration;
import org.bukkit.configuration.file.FileConfiguration;

import java.lang.reflect.Field;

public class ObjectFileConfigurationAction implements IConfigurationAction {
	protected final FileConfiguration configuration;

	public ObjectFileConfigurationAction(FileConfiguration configuration) {
		this.configuration = configuration;
	}

	@Override
	public Class<?>[] getTypes() {
		return new Class<?>[]{Object.class};
	}

	@Override
	public Object get(Field field) {
		return configuration.get(AbstractFileAttributeConfiguration.filterFieldName(field.getName()));
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
