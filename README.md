# Prodrivers Commons for Bukkit

Prodrivers Commons is a common set of tools for Bukkit plugins, designed to fulfil all Prodrivers plugin needs.

Prodrivers Commons is designed to allow easy integration of internally or externally-developed plugins.
By offering cross-plugin integration capabilities, plugins can have access to features such as cross-plugins player
movement or cross-plugins parties without coding it themselves nor having any knowledge of any other plugins.
Prodrivers Commons allows easy implementation of thin shims for incompatible plugins to integrate them in its
infrastructure.


Prodrivers Commons offers notably :

* a flexible section mechanism for cross-plugins player interactions
* a fully-featured, easily integrable party system with internal chat
* a message sending facade with pluggable backends
* pluggable components that can be replaced with other implementations by modifying its configuration, to modify
  Prodrivers Commons' default behavior or use other systems completely
* a flexible and extensible ORM for Bukkit file configuration with its Configuration and Message facilities
* Ebean ORM integration, allowing plugins that once depended on Spigot's built-in database ORM to transition without
  having to implement it themselves, with built-in pooling and caching
* A ready-to-use Aikar Command Framework instance, already integrated with Prodrivers Commons' configuration ORM


This repository holds the common API that all implementations should follow, with its documentation, and its reference
implementation for Bukkit.

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
		<groupId>fr.prodrivers.minecraft</groupId>
		<artifactId>commons-api</artifactId>
		<version>2.0.0-SNAPSHOT</version>
		<scope>provided</scope>
	</dependency>
</dependencies>
```

## License

Prodrivers Commons API and its reference implementation is distributed under the LGPL version 3 license. A copy of the
license is provided in LICENSE.md.