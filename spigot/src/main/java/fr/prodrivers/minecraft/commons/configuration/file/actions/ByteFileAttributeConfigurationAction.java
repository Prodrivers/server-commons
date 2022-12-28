package fr.prodrivers.minecraft.commons.configuration.file.actions;

import fr.prodrivers.minecraft.commons.configuration.file.AbstractFileAttributeConfiguration;
import org.bukkit.configuration.file.FileConfiguration;

import java.lang.reflect.Field;

public class ByteFileAttributeConfigurationAction extends ObjectFileConfigurationAction {
	public ByteFileAttributeConfigurationAction(FileConfiguration configuration) {
		super(configuration);
	}

	@Override
	public Class<?>[] getTypes() {
		return new Class<?>[]{Byte.class, byte.class};
	}

	@Override
	public Object get(Field field) {
		return (byte) configuration.getInt(AbstractFileAttributeConfiguration.filterFieldName(field.getName()));
	}
}
