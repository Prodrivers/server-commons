package fr.prodrivers.minecraft.commons.configuration.file.actions;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.lang.reflect.Field;

public class MessageFileAttributeConfigurationAction extends ObjectFileConfigurationAction {
	public MessageFileAttributeConfigurationAction(FileConfiguration configuration) {
		super(configuration);
	}

	@Override
	public Class<?>[] getTypes() {
		return new Class<?>[]{String.class};
	}

	@Override
	public Object get(Field field) {
		String msg = configuration.getString(field.getName());
		if(msg != null)
			return ChatColor.translateAlternateColorCodes('&', msg);
		return null;
	}

	@Override
	public void set(Field field, Object value) {
		configuration.set(field.getName(), value);
	}

	@Override
	public void setDefault(Field field, Object value) {
		configuration.addDefault(field.getName(), value);
	}
}
