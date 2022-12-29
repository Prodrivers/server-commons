package fr.prodrivers.minecraft.spigot.commons.plugin;

import fr.prodrivers.minecraft.spigot.commons.chat.PaperMessageSender;
import fr.prodrivers.minecraft.commons.configuration.Configuration;
import fr.prodrivers.minecraft.commons.configuration.Messages;
import fr.prodrivers.minecraft.commons.parties.DefaultPartyManager;
import fr.prodrivers.minecraft.commons.sections.DefaultSectionManager;
import fr.prodrivers.minecraft.commons.ui.section.DefaultSelectionUI;
import org.bukkit.plugin.Plugin;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Properties;
import java.util.logging.Level;

@SuppressWarnings("CanBeFinal")
@Singleton
public class EConfiguration extends Configuration {
	public Level logLevel = Level.INFO;
	public Properties storage_ebean = new Properties();
	public long sectionTree_buildDelayTicks = 40L;
	public String providers_SectionManager = DefaultSectionManager.class.getCanonicalName();
	public String providers_PartyManager = DefaultPartyManager.class.getCanonicalName();
	public String providers_SelectionUI = DefaultSelectionUI.class.getCanonicalName();
	public String providers_MessageSender = PaperMessageSender.class.getCanonicalName();

	@Inject
	public EConfiguration(Plugin plugin, Messages messages) {
		super(plugin, messages);
		init();
	}
}