package fr.prodrivers.minecraft.commons.hubs;

import fr.prodrivers.minecraft.commons.Log;
import fr.prodrivers.minecraft.commons.plugin.EConfiguration;
import fr.prodrivers.minecraft.commons.plugin.EMessages;
import fr.prodrivers.minecraft.commons.sections.Section;
import fr.prodrivers.minecraft.commons.sections.SectionManager;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DefaultMainHub extends MainHub {
	private final EConfiguration configuration;
	private final EMessages messages;
	private final SectionManager sectionManager;

	@Inject
	DefaultMainHub(EConfiguration configuration, EMessages messages, SectionManager sectionManager) {
		super();
		this.configuration = configuration;
		this.messages = messages;
		this.sectionManager = sectionManager;
	}

	public boolean preJoin(@NonNull Player player, Section targetSection, boolean fromParty) {
		return this.configuration.sections_mainHub != null;
	}

	public boolean join(@NonNull Player player) {
		try {
			player.teleport(this.configuration.sections_mainHub);
			player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10, 5);
		} catch(Throwable e) {
			Log.severe("Error while trying to make player " + player + " enter hub. Kicking him.", e);
			player.kickPlayer(this.messages.player_kicked_invalid_hub);
			return false;
		}
		return true;
	}

	public boolean preLeave(@NonNull OfflinePlayer player, Section targetSection, boolean fromParty) {
		Section currentSection = sectionManager.getCurrentSection(player);
		if(currentSection != null && currentSection.getFullName().equals(SectionManager.ROOT_NODE_NAME) && targetSection == null) {
			if(player instanceof Player) {
				join((Player) player);
			}
			return false;
		}

		return true;
	}

	public boolean leave(@NonNull OfflinePlayer player) {
		return true;
	}
}
