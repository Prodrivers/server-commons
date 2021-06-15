package fr.prodrivers.bukkit.commons.configuration;

import fr.prodrivers.bukkit.commons.Log;
import fr.prodrivers.bukkit.commons.annotations.ExcludedFromConfiguration;
import fr.prodrivers.bukkit.commons.annotations.ForceSkipObjectAction;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractAttributeConfiguration {
	@ExcludedFromConfiguration
	private final Map<Class<?>, IConfigurationAction> actions = new HashMap<>();
	@ExcludedFromConfiguration
	private int loadCount = 0;

	private enum ProcessCallbackType {
		Normal,
		Serialize,
		Object,
		ToStringValueOf,
		ToStringParse
	}

	private interface ProcessCallback {
		void run(ProcessCallbackType type, IConfigurationAction action, Field field);
	}

	protected void registerAction(IConfigurationAction action) {
		for(Class<?> type : action.getTypes()) {
			if(!this.actions.containsKey(type)) {
				this.actions.put(type, action);
			}
		}
	}

	private String serialize(Object object) {
		try {
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			ObjectOutputStream oo = new ObjectOutputStream(bo);
			oo.writeObject(object);
			oo.flush();
			return Base64.getEncoder().encodeToString(bo.toByteArray());
		} catch(IOException ex) {
			Log.severe("Error while serializing object. Some configuration values might be invalid. Problematic object: " + object.getClass().getName(), ex);
			return "";
		}
	}

	private Object unserialize(String serializedObject) {
		try {
			byte[] b = Base64.getDecoder().decode(serializedObject);
			ByteArrayInputStream bi = new ByteArrayInputStream(b);
			ObjectInputStream oi = new ObjectInputStream(bi);
			return oi.readObject();
		} catch(IOException | ClassNotFoundException ex) {
			Log.severe("Error while unserializing object. Some configuration values might be invalid. Problematic object: " + serializedObject, ex);
			return null;
		}
	}

	private void process(ProcessCallback fieldCallback) throws IllegalStateException {
		Field[] fields = getClass().getFields();

		for(Field field : fields) {
			if(!field.isAnnotationPresent(ExcludedFromConfiguration.class)) {
				if(!field.isAccessible())
					field.setAccessible(true);
				Class<?> type = field.getType();
				IConfigurationAction action = actions.get(type);

				if(action != null) {
					fieldCallback.run(ProcessCallbackType.Normal, action, field);
					continue;
				}

				action = actions.get(Object.class);
				if(action != null && !field.isAnnotationPresent(ForceSkipObjectAction.class)) {
					fieldCallback.run(ProcessCallbackType.Object, action, field);
					continue;
				}

				if(Serializable.class.isAssignableFrom(type)) {
					action = actions.get(String.class);
					if(action != null) {
						fieldCallback.run(ProcessCallbackType.Serialize, action, field);
						continue;
					}
				}

				// ToStringValueOf
				try {
					type.getMethod("toString");
					type.getMethod("valueOf", String.class);
					action = actions.get(String.class);
					if(action != null) {
						fieldCallback.run(ProcessCallbackType.ToStringValueOf, action, field);
						continue;
					}
				} catch(NoSuchMethodException e) {
					// Silently ignore
				}

				// ToStringParse
				try {
					type.getMethod("toString");
					type.getMethod("parse", String.class);
					action = actions.get(String.class);
					if(action != null) {
						fieldCallback.run(ProcessCallbackType.ToStringParse, action, field);
						continue;
					}
				} catch(NoSuchMethodException e) {
					// Silently ignore
				}
			}
		}
	}

	protected void init() throws IllegalStateException {
		saveDefaults();
		load();
	}

	public void save() throws IllegalStateException {
		process((type, action, field) -> {
			switch(type) {
				case Normal:
				case Object:
					try {
						action.set(field, field.get(AbstractAttributeConfiguration.this));
					} catch(IllegalArgumentException ex) {
						throw new IllegalStateException(AbstractAttributeConfiguration.this.getClass().getName() + "'s field " + field.getName() + " has been given an invalid value: " + ex.getLocalizedMessage() + ".\nConfiguration is left in an invalid state.");
					} catch(IllegalAccessException e) {
						throw new IllegalStateException(AbstractAttributeConfiguration.this.getClass().getName() + "'s field " + field.getName() + " was not saved.\nConfiguration is left in an invalid state.", e);
					}
					break;

				case Serialize:
					try {
						action.set(field, serialize(field.get(AbstractAttributeConfiguration.this)));
					} catch(IllegalArgumentException ex) {
						throw new IllegalStateException(AbstractAttributeConfiguration.this.getClass().getName() + "'s field " + field.getName() + " has been given an invalid value: " + ex.getLocalizedMessage() + ".\nConfiguration is left in an invalid state.");
					} catch(IllegalAccessException e) {
						throw new IllegalStateException(AbstractAttributeConfiguration.this.getClass().getName() + "'s field " + field.getName() + " was not saved.\nConfiguration is left in an invalid state.", e);
					}
					break;
					}
					break;

				case ToStringValueOf:
				case ToStringParse:
					try {
						Method toString = field.getType().getMethod("toString");
						action.set(field, toString.invoke(field.get(AbstractAttributeConfiguration.this)));
					} catch(IllegalArgumentException ex) {
						throw new IllegalStateException(AbstractAttributeConfiguration.this.getClass().getName() + "'s field " + field.getName() + " has been given an invalid value: " + ex.getLocalizedMessage() + ".\nConfiguration is left in an invalid state.");
					} catch(NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
						throw new IllegalStateException(AbstractAttributeConfiguration.this.getClass().getName() + "'s field " + field.getName() + " was not saved.\nConfiguration is left in an invalid state.", e);
					}
					break;
			}
		});
	}

	public void reload() throws IllegalStateException {
		load();
	}

	protected void saveDefaults() throws IllegalStateException {
		process((type, action, field) -> {
			switch(type) {
				case Normal:
				case Object:
					try {
						action.setDefault(field, field.get(AbstractAttributeConfiguration.this));
					} catch(IllegalArgumentException ex) {
						throw new IllegalStateException(AbstractAttributeConfiguration.this.getClass().getName() + "'s field " + field.getName() + " has been given an invalid value: " + ex.getLocalizedMessage() + ".\nConfiguration is left in an invalid state.");
					} catch(IllegalAccessException e) {
						throw new IllegalStateException(AbstractAttributeConfiguration.this.getClass().getName() + "'s field " + field.getName() + " was not saved.\nConfiguration is left in an invalid state.", e);
					}
					break;

				case Serialize:
					try {
						action.setDefault(field, serialize(field.get(AbstractAttributeConfiguration.this)));
					} catch(IllegalArgumentException ex) {
						throw new IllegalStateException(AbstractAttributeConfiguration.this.getClass().getName() + "'s field " + field.getName() + " has been given an invalid value: " + ex.getLocalizedMessage() + ".\nConfiguration is left in an invalid state.");
					} catch(IllegalAccessException e) {
						throw new IllegalStateException(AbstractAttributeConfiguration.this.getClass().getName() + "'s field " + field.getName() + " was not saved.\nConfiguration is left in an invalid state.", e);
					}
					break;
					}
					break;

				case ToStringValueOf:
				case ToStringParse:
					try {
						Method toString = field.getType().getMethod("toString");
						action.setDefault(field, toString.invoke(field.get(AbstractAttributeConfiguration.this)));
					} catch(IllegalArgumentException ex) {
						throw new IllegalStateException(AbstractAttributeConfiguration.this.getClass().getName() + "'s field " + field.getName() + " has been given an invalid value: " + ex.getLocalizedMessage() + ".\nConfiguration is left in an invalid state.");
					} catch(NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
						throw new IllegalStateException(AbstractAttributeConfiguration.this.getClass().getName() + "'s field " + field.getName() + " was not saved.\nConfiguration is left in an invalid state.", e);
					}
					break;
			}
		});
	}

	protected void load() throws IllegalStateException {
		loadCount = 0;

		process((type, action, field) -> {
			Object value = null;
			switch(type) {
				case Normal:
				case Object:
					value = action.get(field);
					break;

				case Serialize:
					value = unserialize((String) action.get(field));
					break;

				case ToStringValueOf:
					try {
						Method valueOf = field.getType().getMethod("valueOf", String.class);
						String stringValue = (String) action.get(field);
						value = valueOf.invoke(AbstractAttributeConfiguration.this, stringValue);
					} catch(NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
						throw new IllegalStateException(AbstractAttributeConfiguration.this.getClass().getName() + "'s field " + field.getName() + " was not loaded.\nConfiguration is left in an invalid state.", e);
					}
					break;

				case ToStringParse:
					try {
						Method valueOf = field.getType().getMethod("parse", String.class);
						String stringValue = (String) action.get(field);
						value = valueOf.invoke(AbstractAttributeConfiguration.this, stringValue);
					} catch(NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
						throw new IllegalStateException(AbstractAttributeConfiguration.this.getClass().getName() + "'s field " + field.getName() + " was not loaded.\nConfiguration is left in an invalid state.", e);
					}
					break;
			}
			if(value != null) {
				try {
					field.set(AbstractAttributeConfiguration.this, value);
					loadCount++;
				} catch(IllegalArgumentException ex) {
					throw new IllegalStateException(AbstractAttributeConfiguration.this.getClass().getName() + "'s field " + field.getName() + " has been given an invalid value: " + ex.getLocalizedMessage() + ".\nConfiguration is left in an invalid state.");
				} catch(IllegalAccessException ex) {
					throw new IllegalStateException(AbstractAttributeConfiguration.this.getClass().getName() + " field " + field.getName() + " is not accessible.\nConfiguration is left in an invalid state.");
				}
			}
		});

		Log.info("- Loaded " + loadCount + " " + getClass().getSimpleName() + " fields");
	}
}
