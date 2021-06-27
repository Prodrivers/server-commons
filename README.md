# ProdriversCommons for Bukkit

ProdriversCommons is a common set of tools for Bukkit plugins, designed to fulfil all Prodrivers plugin needs.

This repository holds the common API that all implementations should follow, with its documentation.

This new revision focus on a cleaner, idiomatic design, with heavy usage of dependency injection.

The multiple independent modules that constitute ProdriversCommons are made to have limited side effects for calling
code, focusing on doing one task but doing it well. Calling code should have limited specific code — as in, having close
to zero preconditions, checks and exception thrown — and usage of such code should be explicitly indicated by
ProdriversCommons.

## Usage

This API is available on a Maven repository.

Documentation is available on [Prodrivers Sources](http://commons.sources.prodrivers.fr).

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

ProdriversCommons API and its reference implementation is distributed under the LGPL version 3 license. A copy of the
license is provided in LICENSE.md.