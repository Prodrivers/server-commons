package fr.prodrivers.minecraft.server.commons.configuration.file.actions;

import fr.prodrivers.minecraft.server.commons.configuration.file.AbstractFileAttributeConfiguration;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.lang.reflect.Field;

@Deprecated
public class MaterialFileAttributeConfigurationAction extends ObjectFileConfigurationAction {
	public MaterialFileAttributeConfigurationAction(FileConfiguration configuration) {
		super(configuration);
	}

	@Override
	public Class<?>[] getTypes() {
		return new Class<?>[]{Material.class};
	}

	@Override
	public Object get(Field field) {
		return Material.valueOf(configuration.getString(AbstractFileAttributeConfiguration.filterFieldName(field.getName())));
	}

	@Override
	public void set(Field field, Object value) {
		configuration.set(AbstractFileAttributeConfiguration.filterFieldName(field.getName()), value.toString());
	}

	@Override
	public void setDefault(Field field, Object value) {
		configuration.addDefault(AbstractFileAttributeConfiguration.filterFieldName(field.getName()), value.toString());
	}
}
