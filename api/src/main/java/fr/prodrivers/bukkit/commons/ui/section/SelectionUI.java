package fr.prodrivers.bukkit.commons.ui.section;

import fr.prodrivers.bukkit.commons.sections.Section;
import org.bukkit.entity.Player;

/**
 * Prodrivers Commons Section Selection UI
 * <p>
 * Section Manager allows to open a selection UI for a section. Implements this class to specify a default
 * implementation or custom UI for a section.
 * <p>
 * Used default implementation can be changed in configuration.
 */
public interface SelectionUI {
	/**
	 * Opens a selection UI for a specific section. If you return this as a custom UI for one or several sections, you
	 * are guaranteed to only be called for those sections.
	 *
	 * @param section Section to open UI for
	 * @param player  Player that opened the selection UI
	 */
	void ui(Section section, Player player);
}
