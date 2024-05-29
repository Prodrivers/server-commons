package fr.prodrivers.minecraft.server.commons;

import fr.prodrivers.minecraft.server.commons.di.guice.ProdriversCommonsGuiceModule;
import fr.prodrivers.minecraft.server.spigot.commons.plugin.Main;

/**
 * Public entry point for Prodrivers Commons.
 * <p>
 * Methods here allows access to all public parts of Prodrivers Commons.
 */
public class ProdriversCommons {
	private static Main plugin;

	public static void init(Main plugin) {
		ProdriversCommons.plugin = plugin;
	}

	/**
	 * Returns a module for Guice dependency injector, adding all public parts of Prodrivers Commons to your dependency
	 * injector instance. The first call to do before interacting with parts of Prodrivers Commons that requires a
	 * dependency injector.
	 *
	 * @return Instance of Prodrivers Commons module for Guice
	 */
	public static ProdriversCommonsGuiceModule getGuiceModule() {
		return plugin.getInjector().getInstance(ProdriversCommonsGuiceModule.class);
	}
}
