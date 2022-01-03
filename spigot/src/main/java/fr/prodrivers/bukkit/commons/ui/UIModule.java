package fr.prodrivers.bukkit.commons.ui;

import com.google.inject.AbstractModule;
import fr.prodrivers.bukkit.commons.plugin.EConfiguration;
import fr.prodrivers.bukkit.commons.ui.section.SelectionUI;

import javax.inject.Inject;

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
