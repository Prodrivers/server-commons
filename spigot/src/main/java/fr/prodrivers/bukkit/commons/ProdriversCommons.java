package fr.prodrivers.bukkit.commons;

import fr.prodrivers.bukkit.commons.di.guice.ProdriversCommonsGuiceModule;
import fr.prodrivers.bukkit.commons.plugin.Main;

public class ProdriversCommons {
	private static Main plugin;

	public static void init(Main plugin) {
		ProdriversCommons.plugin = plugin;
	}

	public static ProdriversCommonsGuiceModule getGuiceModule() {
		return plugin.getInjector().getInstance(ProdriversCommonsGuiceModule.class);
	}
}
