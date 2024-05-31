package fr.prodrivers.minecraft.server.commons.sections;

import com.google.inject.AbstractModule;
import fr.prodrivers.minecraft.server.spigot.commons.plugin.EConfiguration;

import jakarta.inject.Inject;

public class SectionManagerModule extends AbstractModule {
	private final Class<? extends SectionManager> sectionManagerClass;

	@Inject
	@SuppressWarnings("unchecked")
	public SectionManagerModule(EConfiguration configuration) {
		try {
			this.sectionManagerClass = (Class<? extends SectionManager>) Class.forName(configuration.providers_SectionManager);
		} catch(ClassNotFoundException | ClassCastException e) {
			throw new RuntimeException("Invalid section manager provider: " + configuration.providers_SectionManager, e);
		}
	}

	@Override
	protected void configure() {
		bind(SectionManager.class).to(sectionManagerClass);
	}
}
