package fr.prodrivers.minecraft.server.commons.configuration.file.actions;

import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;

import java.lang.reflect.Field;
import java.util.Map;

public class MessageMapFileAttributeConfigurationAction extends ObjectFileConfigurationAction {
	public MessageMapFileAttributeConfigurationAction(FileConfiguration configuration) {
		super(configuration);
	}

	@Override
	public Class<?>[] getTypes() {
		return new Class<?>[]{Map.class};
	}

	@Override
	public Object get(Field field) {
		String name = field.getName();
		if(configuration.get(name) instanceof MemorySection) {
			return ((MemorySection) configuration.get(name)).getValues(false);
		} else {
			return configuration.get(name);
		}
	}
}
