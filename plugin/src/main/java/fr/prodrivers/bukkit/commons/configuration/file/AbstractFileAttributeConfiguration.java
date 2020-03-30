package fr.prodrivers.bukkit.commons.configuration.file;

import fr.prodrivers.bukkit.commons.configuration.AbstractAttributeConfiguration;
import fr.prodrivers.bukkit.commons.configuration.file.actions.*;
import org.bukkit.configuration.file.FileConfiguration;

public abstract class AbstractFileAttributeConfiguration extends AbstractAttributeConfiguration {
	protected FileConfiguration configuration;

	public AbstractFileAttributeConfiguration() {
		super();
	}

	@Override
	public void init() {
		registerAction( new ObjectFileConfigurationAction( this ) );
		registerAction( new StringFileAttributeConfigurationAction( this ) );
		registerAction( new LocationFileConfigurationAction( this ) );
		registerAction( new FloatFileAttributeConfigurationAction( this ) );
		registerAction( new ByteFileAttributeConfigurationAction( this ) );
		registerAction( new MaterialFileAttributeConfigurationAction( this ) );
		registerAction( new MapFileAttributeConfigurationAction( this ) );
		super.init();
	}

	/*@Override
	Set<Class<?>> getAcceptedFieldTypes() {
		Set<Class<?>> acceptedFieldTypes = new HashSet<>();
		acceptedFieldTypes.add( Short.class );
		acceptedFieldTypes.add( Integer.class );
		acceptedFieldTypes.add( Long.class );
		acceptedFieldTypes.add( Float.class );
		acceptedFieldTypes.add( Double.class );
		acceptedFieldTypes.add( Boolean.class );
		acceptedFieldTypes.add( Byte.class );
		acceptedFieldTypes.add( String.class );
		acceptedFieldTypes.add( List.class );
		return acceptedFieldTypes;
	}*/

	/**
	 * Field name fitler. Allows to translate from field name to configuration path.
	 * Default behavior is to replace every "_" with ".", meaning that every "_" will actually separate into sub-fields.
	 * @param fieldName Field name
	 * @return Filtered field name
	 */
	public static String filterFieldName( String fieldName ) {
		return fieldName.replaceAll( "_", "." );
	}

	/*@Override
	void addDefault( Field field, Object value ) {
		configuration.addDefault( filterFieldName( field.getName() ), value );
	}*/

	@Override
	protected void saveDefaults() {
		super.saveDefaults();

		configuration.options().copyDefaults( true );
	}

	/*@Override
	Object get( Field field ) {
		String path = filterFieldName( field.getName() );
		Class<?> type = field.getType();
		if( type.equals( Byte.class ) || type.equals( Short.class ) || type.equals( Integer.class ) ) {
			return configuration.getInt( path );
		}
		if( type.equals( Long.class ) ) {
			return configuration.getLong( path );
		}
		if( type.equals( Float.class ) || type.equals( Double.class ) ) {
			return configuration.getDouble( path );
		}
		if( type.equals( Boolean.class ) ) {
			return configuration.getBoolean( path );
		}
		if( type.equals( String.class ) ) {
			return configuration.getString( path );
		}
		if( type.equals( List.class ) ) {
			return configuration.getList( path );
		}
		return configuration.get( path );
	}

	@Override
	void set( Field field, Object value ) {
		configuration.set( filterFieldName( field.getName() ), value );
	}*/

	public FileConfiguration getConfiguration() {
		return configuration;
	}
}
