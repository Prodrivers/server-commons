package fr.prodrivers.minecraft.server.commons.configuration.file.actions;

import fr.prodrivers.minecraft.server.commons.configuration.file.AbstractFileAttributeConfiguration;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Deprecated
public class MapFileAttributeConfigurationAction extends ObjectFileConfigurationAction {
	public MapFileAttributeConfigurationAction(FileConfiguration configuration) {
		super(configuration);
	}

	@Override
	public Class<?>[] getTypes() {
		return new Class<?>[]{Map.class, HashMap.class, LinkedHashMap.class};
	}

	public Object convertToMap(Object value) {
		if(value instanceof MemorySection memorySection) {
			Map<String, Object> values = memorySection.getValues(false);
			values.replaceAll((k, v) -> convertToMap(v));
			return values;
		}

		return value;
	}

	@Override
	public Object get(Field field) {
		return convertToMap(configuration.get(AbstractFileAttributeConfiguration.filterFieldName(field.getName())));
	}
}
