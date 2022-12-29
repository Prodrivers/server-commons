package fr.prodrivers.minecraft.commons.configuration.file.actions;

import fr.prodrivers.minecraft.commons.configuration.IConfigurationAction;
import fr.prodrivers.minecraft.commons.configuration.file.AbstractFileAttributeConfiguration;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.configuration.file.FileConfiguration;

import java.lang.reflect.Field;

public class ComponentFileAttributeConfigurationAction implements IConfigurationAction {
	private final FileConfiguration configuration;
	private final MiniMessage miniMessage;

	public ComponentFileAttributeConfigurationAction(FileConfiguration configuration) {
		this.configuration = configuration;
		this.miniMessage = MiniMessage.miniMessage();
	}

	@Override
	public Class<?>[] getTypes() {
		return new Class<?>[]{Component.class};
	}

	@Override
	public Object get(Field field) {
		String key = AbstractFileAttributeConfiguration.filterFieldName(field.getName());
		String storedString = configuration.getString(key);
		// If there is no value, return a default one
		if(storedString == null) {
			return Component.text("NOT_DEFINED:" + key.toUpperCase());
		}
		// If not, consider it is a MiniMessage-formatted string
		return miniMessage.deserialize(storedString);
	}

	@Override
	public void set(Field field, Object value) {
		// In case a plugin wants to modify a value, serialize as a MiniMessage
		String miniMessageValue = miniMessage.serialize((Component) value);
		configuration.set(AbstractFileAttributeConfiguration.filterFieldName(field.getName()), miniMessageValue);
	}

	@Override
	public void setDefault(Field field, Object value) {
		// In case a plugin wants to modify a value, serialize as a MiniMessage
		String miniMessageValue = miniMessage.serialize((Component) value);
		configuration.addDefault(AbstractFileAttributeConfiguration.filterFieldName(field.getName()), miniMessageValue);
	}
}
