package fr.prodrivers.bukkit.commons;

import fr.prodrivers.bukkit.commons.di.guice.ProdriversCommonsGuiceModule;

/**
 * Public entry point for ProdriversCommons.
 * <br/>
 * Methods here allows access to all public parts of ProdriversCommons.
 */
public class ProdriversCommons {
	/**
	 * Returns a module for Guice dependency injector, adding all public parts of ProdriversCommons to your dependency
	 * injector instance. The first call to do before interacting with parts of ProdriversCommons that requires a
	 * dependency injector.
	 *
	 * @return Instance of Prodrivers Commons module for Guice
	 */
	public static ProdriversCommonsGuiceModule getGuiceModule() {
		throw new UnsupportedOperationException();
	}
}
