package fr.prodrivers.minecraft.commons.plugin;

import javax.inject.Provider;
import javax.inject.Singleton;

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
