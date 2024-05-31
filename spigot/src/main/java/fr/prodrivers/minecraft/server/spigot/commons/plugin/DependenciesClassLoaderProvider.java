package fr.prodrivers.minecraft.server.spigot.commons.plugin;

import jakarta.inject.Provider;
import jakarta.inject.Singleton;

@Singleton
public class DependenciesClassLoaderProvider implements Provider<ClassLoader> {
	private final ClassLoader classLoader;

	public DependenciesClassLoaderProvider(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	@Override
	public ClassLoader get() {
		return this.classLoader;
	}
}
