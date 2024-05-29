# Prodrivers Commons for Bukkit

## Status

This repository holds the legacy, Spigot-specific 2.X branch of Prodrivers Commons for Minecraft servers, then called
Prodrivers Commons for Bukkit.

Deep integration with Spigot and between Commons' components means that it is too difficult to port select components
to other platforms, notably Velocity and Minestom. This also means it is difficult to implement distributed actions to
support multiple servers.

This revision is in maintenance mode and will not evolve further.


A refactor is currently in progress in branch "master" with a new name, Prodrivers Commons for Minecraft servers, based
around Vert.x.  Dependency injection will still be used for select, non-distributed components which cannot be built
around Vert.x.

The usage of Vert.x and its event bus will make Prodrivers Commons distributed by default. Proper separation between
core logic and platform-specific parts will be ensured, with platform-specific parts at a minimum.

## Introduction

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
		<groupId>fr.prodrivers.bukkit</groupId>
		<artifactId>prodrivers-commons</artifactId>
		<version>2.0.0-SNAPSHOT</version>
		<scope>provided</scope>
	</dependency>
</dependencies>
```

## License

Prodrivers Commons API and its reference implementation is distributed under the LGPL version 3 license. A copy of the
license is provided in LICENSE.md.