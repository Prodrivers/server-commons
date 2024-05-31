package fr.prodrivers.minecraft.server.commons.ui;

import com.google.inject.AbstractModule;
import fr.prodrivers.minecraft.server.commons.ui.section.SelectionUI;
import fr.prodrivers.minecraft.server.spigot.commons.plugin.EConfiguration;

import javax.inject.Inject;

@Deprecated
public class UIModule extends AbstractModule {
	private final Class<? extends SelectionUI> sectionSelectionUiClass;

	@Inject
	@SuppressWarnings("unchecked")
	public UIModule(EConfiguration configuration) {
		try {
			this.sectionSelectionUiClass = (Class<? extends SelectionUI>) Class.forName(configuration.providers_SelectionUI);
		} catch(ClassNotFoundException | ClassCastException e) {
			throw new RuntimeException("Invalid section selection UI provider: " + configuration.providers_SelectionUI, e);
		}
	}

	@Override
	protected void configure() {
		bind(SelectionUI.class).to(sectionSelectionUiClass);
	}
}
