package fr.prodrivers.minecraft.commons;

import fr.prodrivers.minecraft.commons.di.guice.ProdriversCommonsGuiceModule;
import fr.prodrivers.minecraft.spigot.commons.plugin.Main;

public class ProdriversCommons {
	private static Main plugin;

	public static void init(Main plugin) {
		ProdriversCommons.plugin = plugin;
	}

	public static ProdriversCommonsGuiceModule getGuiceModule() {
		return plugin.getInjector().getInstance(ProdriversCommonsGuiceModule.class);
	}
}
