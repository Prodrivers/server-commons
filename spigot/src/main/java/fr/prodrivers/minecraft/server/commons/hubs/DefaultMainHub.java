package fr.prodrivers.minecraft.server.commons.hubs;

import fr.prodrivers.minecraft.server.commons.sections.Section;
import fr.prodrivers.minecraft.server.spigot.commons.plugin.EConfiguration;
import fr.prodrivers.minecraft.server.spigot.commons.plugin.EMessages;
import fr.prodrivers.minecraft.server.commons.sections.SectionManager;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Default implementation of main hub.
 * <p>
 * Teleports a player to a location defined in configuration and plays a sound.
 * If the player can not be teleported (for example, due to an invalid location), the player is kicked.
 * <p>
 * Correctly handles players wanting to leave the hub by forbidding them and teleporting them.
 */
@Deprecated
@Singleton
public class DefaultMainHub extends MainHub {
	private final Logger logger;
	private final EConfiguration configuration;
	private final EMessages messages;
	private final SectionManager sectionManager;

	@Inject
	DefaultMainHub(Logger logger, EConfiguration configuration, EMessages messages, SectionManager sectionManager) {
		super();
		this.logger = logger;
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
			this.logger.log(Level.SEVERE, "Error while trying to make player " + player + " enter hub. Kicking him.", e);
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