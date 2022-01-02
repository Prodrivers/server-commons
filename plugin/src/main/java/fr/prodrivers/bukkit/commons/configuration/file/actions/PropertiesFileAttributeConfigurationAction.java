package fr.prodrivers.bukkit.commons.configuration.file.actions;

import fr.prodrivers.bukkit.commons.configuration.file.AbstractFileAttributeConfiguration;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public class PropertiesFileAttributeConfigurationAction extends ObjectFileConfigurationAction {
	public PropertiesFileAttributeConfigurationAction(FileConfiguration configuration) {
		super(configuration);
	}

	@Override
	public Class<?>[] getTypes() {
		return new Class<?>[]{Properties.class};
	}

	@Override
	public Object get(Field field) {
		MemorySection configurationSection = ((MemorySection) configuration.get(AbstractFileAttributeConfiguration.filterFieldName(field.getName())));
		if(configurationSection == null) {
			return null;
		}
		Properties properties = new Properties();
		// Get all leafs in section (i.e. get all entries that aren't a MemorySection value)
		configurationSection
				.getValues(true)
				.entrySet()
				.stream()
				.filter(entry -> !(entry.getValue() instanceof MemorySection))
				.forEach(entry -> {
					// Map is already arranged in property format, just put it naively but convert value to string
					properties.put(entry.getKey(), entry.getValue().toString());
				});
		return properties;
	}

	@Override
	public void set(Field field, Object value) {
		String key = AbstractFileAttributeConfiguration.filterFieldName(field.getName());
		if(value instanceof Properties properties) {
			for(Map.Entry<Object, Object> entry : properties.entrySet()){
				configuration.set(key + "." + entry.getKey(), entry.getValue());
			}
		} else {
			this.configuration.set(key, null);
		}
	}

	@Override
	public void setDefault(Field field, Object value) {
		String key = AbstractFileAttributeConfiguration.filterFieldName(field.getName());
		if(value instanceof Properties properties) {
			for(Map.Entry<Object, Object> entry : properties.entrySet()){
				configuration.set(key + "." + entry.getKey(), entry.getValue());
			}
		}
	}
}
