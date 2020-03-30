package fr.prodrivers.bukkit.commons.sections;

import fr.prodrivers.bukkit.commons.plugin.Main;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class MainHub implements IProdriversSection {
	public static String name = "main";
	public static MainHub instance;
	private Location loc;

	MainHub() {
		reload();
		instance = this;
	}

	public String getName() {
		return name;
	}

	public String getPreferredNextSection() {
		return null;
	}

	public boolean forceNextSection() {
		return false;
	}

	public boolean isHub() {
		return true;
	}

	public boolean shouldMoveParty() {
		return false;
	}

	public boolean join( Player player, String leavedSection, String subSection ) {
		return loc != null;
	}

	public void postJoin( Player player, String leavedSection, String subSection ) {
		player.teleport( loc );
		player.playSound( player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10, 5 );
	}

	public boolean leave( Player player, String enteredSection ) {
		return true;
	}

	public void postLeave( Player player, String enteredSection ) {}

	void reload() {
		loc = Main.getConfiguration().sections_mainHub;
	}
}
