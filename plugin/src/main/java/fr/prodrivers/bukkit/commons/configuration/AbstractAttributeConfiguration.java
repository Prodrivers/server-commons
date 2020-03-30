package fr.prodrivers.bukkit.commons.configuration;

import fr.prodrivers.bukkit.commons.annotations.ExcludedFromConfiguration;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractAttributeConfiguration {
	@ExcludedFromConfiguration
	private Map<Class<?>, IConfigurationAction> actions = new HashMap<>();
	@ExcludedFromConfiguration
	private int loadCount = 0;

	private enum ProcessCallbackType {
		Normal,
		Serialize,
		Object,
		ToString
	}

	private interface ProcessCallback {
		void run( ProcessCallbackType type, IConfigurationAction action, Field field );
	}

	protected void registerAction( IConfigurationAction action ) {
		for( Class<?> type : action.getTypes() ) {
			if( !this.actions.containsKey( type ) ) {
				this.actions.put( type, action );
			}
		}
	}

	private String serialize( Object object ) {
		try {
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			ObjectOutputStream oo = new ObjectOutputStream( bo );
			oo.writeObject( object );
			oo.flush();
			return bo.toString();
		} catch( IOException ex ) {
			System.err.println( "Error while serializing object. Some configuration values might be invalid." );
			System.err.println( "Problematic object: " + object.getClass().getName() );
			ex.printStackTrace();
			return "";
		}
	}

	private Object unserialize( String serializedObject ) {
		try {
			byte b[] = serializedObject.getBytes();
			ByteArrayInputStream bi = new ByteArrayInputStream( b );
			ObjectInputStream oi = new ObjectInputStream( bi );
			return oi.readObject();
		} catch( IOException | ClassNotFoundException ex ) {
			System.err.println( "Error while unserializing object. Some configuration values might be invalid." );
			System.err.println( "Problematic object: " + serializedObject );
			ex.printStackTrace();
			return null;
		}
	}

	private void process( ProcessCallback fieldCallback ) throws IllegalStateException {
		Field[] fields = getClass().getFields();

		for( Field field : fields ) {
			if( !field.isAnnotationPresent( ExcludedFromConfiguration.class ) ) {
				if( !field.isAccessible() )
					field.setAccessible( true );
				Class<?> type = field.getType();
				IConfigurationAction action = actions.get( type );

				if( action != null ) {
					fieldCallback.run( ProcessCallbackType.Normal, action, field );
					continue;
				}

				action = actions.get( Object.class );
				if( action != null ) {
					fieldCallback.run( ProcessCallbackType.Object, action, field );
					continue;
				}

				if( type.isAssignableFrom( Serializable.class ) ) {
					action = actions.get( String.class );
					if( action != null ) {
						fieldCallback.run( ProcessCallbackType.Serialize, action, field );
						continue;
					}
				}

				try {
					Method toString = type.getMethod( "toString" );
					Method valueOf = type.getMethod( "valueOf", String.class );
					action = actions.get( String.class );
					if( toString != null && valueOf != null && action != null ) {
						fieldCallback.run( ProcessCallbackType.ToString, action, field );
					}
				} catch( NoSuchMethodException e ) {
					throw new IllegalStateException( getClass().getName() + "'s field " + field.getName() + " can not be used, as it is of type (" + field.getType().getName() + ") neither supported directly, nor serializable, nor can it be converted to String back and forth using toString and valueOf methods\nConfiguration is left in an invalid state." );
				}
			}
		}
	}

	public void init() throws IllegalStateException {
		saveDefaults();
		load();
	}

	public void save() throws IllegalStateException {
		process( ( type, action, field ) -> {
			switch( type ) {
				case Normal:
				case Object:
					try {
						action.set( field, field.get( AbstractAttributeConfiguration.this ) );
					} catch( IllegalArgumentException ex ) {
						throw new IllegalStateException( AbstractAttributeConfiguration.this.getClass().getName() + "'s field " + field.getName() + " has been given an invalid value: " + ex.getLocalizedMessage() + ".\nConfiguration is left in an invalid state." );
					} catch( IllegalAccessException e ) {
						break;
					}
					break;

				case Serialize:
					try {
						action.set( field, serialize( field.get( AbstractAttributeConfiguration.this ) ) );
					} catch( IllegalArgumentException ex ) {
						throw new IllegalStateException( AbstractAttributeConfiguration.this.getClass().getName() + "'s field " + field.getName() + " has been given an invalid value: " + ex.getLocalizedMessage() + ".\nConfiguration is left in an invalid state." );
					} catch( IllegalAccessException e ) {
						break;
					}
					break;

				case ToString:
					try {
						Method toString = field.getType().getMethod( "toString" );
						action.set( field, toString.invoke( field.get( AbstractAttributeConfiguration.this ) ) );
					} catch( IllegalArgumentException ex ) {
						throw new IllegalStateException( AbstractAttributeConfiguration.this.getClass().getName() + "'s field " + field.getName() + " has been given an invalid value: " + ex.getLocalizedMessage() + ".\nConfiguration is left in an invalid state." );
					} catch( NoSuchMethodException | IllegalAccessException | InvocationTargetException e ) {
						break;
					}
					break;
			}
		} );
		/*Field[] fields = getClass().getFields();

		for( Field field : fields ) {
			if( acceptedFieldTypes.contains( field.getType() ) ) {
				try {
					if( !field.isAccessible() )
						field.setAccessible( true );
					set( field, field.get( this ) );
				} catch( IllegalAccessException ex ) {
					System.err.println( getClass().getName() + " field " + field.getName() + " is not accessible." );
				}
			}
		}*/
	}

	public void reload() throws IllegalStateException {
		load();
	}

	protected void saveDefaults() throws IllegalStateException {
		process( ( type, action, field ) -> {
			switch( type ) {
				case Normal:
				case Object:
					try {
						action.setDefault( field, field.get( AbstractAttributeConfiguration.this ) );
					} catch( IllegalArgumentException ex ) {
						throw new IllegalStateException( AbstractAttributeConfiguration.this.getClass().getName() + "'s field " + field.getName() + " has been given an invalid value: " + ex.getLocalizedMessage() + ".\nConfiguration is left in an invalid state." );
					} catch( IllegalAccessException e ) {
						break;
					}
					break;

				case Serialize:
					try {
						action.setDefault( field, serialize( field.get( AbstractAttributeConfiguration.this ) ) );
					} catch( IllegalArgumentException ex ) {
						throw new IllegalStateException( AbstractAttributeConfiguration.this.getClass().getName() + "'s field " + field.getName() + " has been given an invalid value: " + ex.getLocalizedMessage() + ".\nConfiguration is left in an invalid state." );
					} catch( IllegalAccessException e ) {
						break;
					}
					break;

				case ToString:
					try {
						Method toString = field.getType().getMethod( "toString" );
						action.setDefault( field, toString.invoke( field.get( AbstractAttributeConfiguration.this ) ) );
					} catch( IllegalArgumentException ex ) {
						throw new IllegalStateException( AbstractAttributeConfiguration.this.getClass().getName() + "'s field " + field.getName() + " has been given an invalid value: " + ex.getLocalizedMessage() + ".\nConfiguration is left in an invalid state." );
					} catch( NoSuchMethodException | IllegalAccessException | InvocationTargetException e ) {
						break;
					}
					break;
			}
		} );
	}

	protected void load() throws IllegalStateException {
		loadCount = 0;

		process( ( type, action, field ) -> {
			Object value = null;
			switch( type ) {
				case Normal:
				case Object:
					value = action.get( field );
					break;

				case Serialize:
					value = unserialize( (String) action.get( field ) );
					break;

				case ToString:
					try {
						Method valueOf = field.getType().getMethod( "valueOf" );
						value = valueOf.invoke( action.get( field ) );
					} catch( NoSuchMethodException | IllegalAccessException | InvocationTargetException e ) {
						break;
					}
					break;
			}
			if( value != null ) {
				try {
					field.set( AbstractAttributeConfiguration.this, value );
					loadCount++;
				} catch( IllegalArgumentException ex ) {
					throw new IllegalStateException( AbstractAttributeConfiguration.this.getClass().getName() + "'s field " + field.getName() + " has been given an invalid value: " + ex.getLocalizedMessage() + ".\nConfiguration is left in an invalid state." );
				} catch( IllegalAccessException ex ) {
					throw new IllegalStateException( AbstractAttributeConfiguration.this.getClass().getName() + " field " + field.getName() + " is not accessible.\nConfiguration is left in an invalid state." );
				}
			}
		} );

		System.out.println( "- Loaded " + loadCount + " " + getClass().getSimpleName() + " fields" );

		/*Field[] fields = getClass().getFields();

		for( Field field : fields ) {
			if( acceptedFieldTypes.contains( field.getType() ) ) {
				try {
					if( !field.isAccessible() )
						field.setAccessible( true );
					addDefault( field, field.get( this ) );
				} catch( IllegalAccessException ex ) {
					System.err.println( getClass().getName() + " field " + field.getName() + " is not accessible." );
				}
			}
		}

		saveDefaults();

		for( Field field : fields ) {
			if( acceptedFieldTypes.contains( field.getType() ) ) {
				try {
					Object value = get( field );
					if( value != null ) {
						field.set( this, filter( value ) );
					} else {
						field.set( this, "** NULL VALUE **" );
						System.err.println( getClass().getName() + " field " + field.getName() + " has null value." );
					}
				} catch( IllegalAccessException ex ) {
					System.err.println( getClass().getName() + " field " + field.getName() + " is not accessible." );
				}
			}
		}*/
	}
}
