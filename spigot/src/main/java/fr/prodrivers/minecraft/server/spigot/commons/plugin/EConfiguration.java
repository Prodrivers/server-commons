package fr.prodrivers.minecraft.server.spigot.commons.plugin;

import fr.prodrivers.minecraft.server.commons.chat.SpigotMessageSender;
import fr.prodrivers.minecraft.server.commons.configuration.Configuration;
import fr.prodrivers.minecraft.server.commons.configuration.Messages;
import fr.prodrivers.minecraft.server.commons.hubs.DefaultMainHub;
import fr.prodrivers.minecraft.server.commons.parties.DefaultPartyManager;
import fr.prodrivers.minecraft.server.commons.sections.DefaultSectionManager;
import fr.prodrivers.minecraft.server.commons.ui.section.DefaultSelectionUI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("CanBeFinal")
@Singleton
public class EConfiguration extends Configuration {
	public Level logLevel = Level.INFO;
	public Location sections_mainHub = new Location(Bukkit.getWorld("world"), 0, 70, 0);
	public Properties storage_ebean = new Properties();
	public long sectionTree_buildDelayTicks = 40L;
	public String providers_SectionManager = DefaultSectionManager.class.getCanonicalName();
	public String providers_PartyManager = DefaultPartyManager.class.getCanonicalName();
	public String providers_MainHub = DefaultMainHub.class.getCanonicalName();
	public String providers_SelectionUI = DefaultSelectionUI.class.getCanonicalName();
	public String providers_MessageSender = SpigotMessageSender.class.getCanonicalName();

	@Inject
	public EConfiguration(Logger logger, Plugin plugin, Messages messages) {
		super(logger, plugin, messages);
		init();
	}
}
