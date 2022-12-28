package fr.prodrivers.minecraft.commons.sections;

/**
 * Define all section capabilities.
 * <p>
 * A capability is a way for a section to inform about what it handles and tune Prodrivers Commons' behavior when
 * dealing with it.
 */
public enum SectionCapabilities {
	/**
	 * Inform Prodrivers Commons that the section handles its UI by itself.
	 */
	CUSTOM_SELECTION_UI,
	/**
	 * Define that the section should be considered a hub. Players in parties can join hub sections by themselves, contrary to other sections.
	 */
	HUB,
	/**
	 * Inform Prodrivers Commons that the section handles party management by itself, and that Prodrivers Commons should not move party players for it.
	 */
	PARTY_AWARE,
	/**
	 * Inform Prodrivers Commons that the section handles scoreboards by itself and does not use Prodrivers Commons's Scoreboard infrastructure.
	 */
	SCOREBOARD_EXCLUSIVE,
	/**
	 * Inform Prodrivers Commons that the section is transitive, meaning that a player should not stop here.
	 * Sections created by manager to fill holes in section tree expose this capability.
	 */
	TRANSITIVE
}
