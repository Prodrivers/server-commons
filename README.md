# Prodrivers Commons for Minecraft servers

Prodrivers Commons is a common set of tools for Minecraft servers components, designed to fulfil all Prodrivers' needs.

Prodrivers Commons is designed to allow easy integration of internally or externally-developed components.
By offering cross-component integration capabilities, components can have access to features such as cross-components
player movement or cross-components parties without coding it themselves nor having any knowledge of any other
components.
Prodrivers Commons allows easy implementation of thin shims for incompatible components to integrate them in its
infrastructure.


Prodrivers Commons offers notably :

* cross-platform, cross-components types to replace server's owns, such as a database-backed player implementation
* a flexible section mechanism for cross-components, distributed player interactions
* distributed chat system with channels
* a fully-featured, distributed party system that integrates with the chat system
* standardized configurations and i18n-ready messages facilities
* various player-related utilities, such as protocol version and client type checking
* Ebean ORM integration, with built-in pooling and caching
* A ready-to-use Aikar Command Framework instance, already integrated with Prodrivers Commons' configuration ORM
* pluggable components that can be replaced with other implementations by modifying its configuration, to modify
  Prodrivers Commons' default behavior or use other systems completely


This repository holds the common API that all implementations should follow, with its documentation, and its reference
implementation for Spigot.

## Documentation

Documentation is available on Prodrivers Sources for:

* [latest release](http://commons.sources.prodrivers.fr/release/apidocs)
* [latest commit](http://commons.sources.prodrivers.fr/snapshot/apidocs)

## Usage

This API is available on a Maven repository.

```
<repositories>
	<repository>
    	<id>prodrivers-repo</id>
    	<url>https://repo.prodrivers.fr/</url>
    </repository>
</repositories>
<dependencies>
	<dependency>
		<groupId>fr.prodrivers.minecraft.server</groupId>
		<artifactId>commons-core</artifactId>
		<version>0.1.0-SNAPSHOT</version>
		<scope>provided</scope>
	</dependency>
</dependencies>
```

## License

Prodrivers Commons API and its reference implementation is distributed under the LGPL version 3 license. A copy of the
license is provided in LICENSE.md.
