package fr.prodrivers.minecraft.server.commons.ui.section;

import fr.prodrivers.minecraft.server.commons.sections.Section;
import org.bukkit.entity.Player;

/**
 * Default Selection UI for sections.
 * <p>
 * This implementation is currently non-functional.
 */
@Deprecated
public class DefaultSelectionUI implements SelectionUI {
	@Override
	public void ui(Section section, Player player) {
		throw new UnsupportedOperationException();
	}
}
