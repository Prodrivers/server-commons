package fr.prodrivers.minecraft.commons.di.guice;

import co.aikar.commands.BukkitCommandManager;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import fr.prodrivers.minecraft.commons.chat.MessageSender;
import fr.prodrivers.minecraft.commons.commands.ACFCommandManagerProvider;
import fr.prodrivers.minecraft.commons.parties.PartyManager;
import fr.prodrivers.minecraft.spigot.commons.plugin.DependenciesClassLoaderProvider;
import fr.prodrivers.minecraft.commons.sections.SectionManager;
import fr.prodrivers.minecraft.commons.storage.EbeanPropertiesProvider;
import fr.prodrivers.minecraft.commons.storage.EbeanProvider;
import fr.prodrivers.minecraft.commons.ui.section.SelectionUI;
import io.ebean.Database;

import javax.inject.Inject;
import java.util.Properties;

/**
 * Public ProdriversCommmons module for Guice dependency injector.
 * <p>
 * Exposes all public parts of Prodrivers Commons to your dependency injector instance. You will need to use it to
 * interact with all parts of Prodrivers Commons that requires dependency injection.
 * <p>
 * Currently, the following classes are accessible through this module :
 * <ul>
 *     <li>{@link PartyManager PartyManager}
 *     <li>{@link SectionManager SectionManager}
 *     <li>{@link MessageSender MessageSender}
 *     <li>{@link SelectionUI SelectionUI}
 *     <li>{@link io.ebean.Database Database} through {@link EbeanProvider EbeanProvider}
 *     <li>{@link io.ebean.datasource.DataSourceConfig DataSourceConfig} through {@link DataSourceConfigProvider DataSourceConfigProvider}
 *     <li>{@link co.aikar.commands.BukkitCommandManager BukkitCommandManager} through {@link ACFCommandManagerProvider ACFCommandManagerProvider}
 * </ul>
 */
public class ProdriversCommonsGuiceModule extends AbstractModule {
	@Inject
	private PartyManager partyManager;
	@Inject
	private SectionManager sectionManager;
	@Inject
	private MessageSender messageSender;

	@Inject
	private EbeanPropertiesProvider ebeanPropertiesProvider;

	@Inject
	private DependenciesClassLoaderProvider dependenciesClassLoaderProvider;

	@Inject
	private SelectionUI selectionUI;

	@Override
	protected void configure() {
		bind(PartyManager.class).toInstance(partyManager);
		bind(SectionManager.class).toInstance(sectionManager);
		bind(MessageSender.class).toInstance(messageSender);
		bind(SelectionUI.class).toInstance(selectionUI);
		bind(Database.class).toProvider(EbeanProvider.class);
		bind(Properties.class).annotatedWith(Names.named("ebean")).toProvider(ebeanPropertiesProvider);
		bind(DependenciesClassLoaderProvider.class).toInstance(dependenciesClassLoaderProvider);
		bind(BukkitCommandManager.class).toProvider(ACFCommandManagerProvider.class);
	}
}
