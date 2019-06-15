# ProdriversCommons for Bukkit

ProdriversCommons is a common set of tools for Bukkit plugins, designed to fulfil all Prodrivers plugin needs.

This repository holds the common API that all implementations should follow, with its documentation.

The API is currently not finished and may be subject to slight changes. Such changes will be documented.

## Usage

This API is available on a Maven repository.

Documentation is available on [Prodrivers Sources](http://commons.sources.prodrivers.fr).

```
<repositories>
	<repository>
    	<id>prodrivers-repo</id>
    	<url>https://gitlab.com/api/v4/groups/Prodrivers/-/packages/maven</url>
    </repository>
</repositories>
<dependencies>
	<dependency>
		<groupId>fr.prodrivers.bukkit</groupId>
		<artifactId>prodrivers-commons</artifactId>
		<version>1.0-SNAPSHOT</version>
		<scope>provided</scope>
	</dependency>
</dependencies>
```

## License

ProdriversCommons API is distributed under the LGPL version 3 license. A copy of the license is provided in LICENSE.md.

Its reference implementation is closed-source.
