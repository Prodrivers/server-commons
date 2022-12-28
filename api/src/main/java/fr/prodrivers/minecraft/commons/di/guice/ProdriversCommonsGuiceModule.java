package fr.prodrivers.minecraft.commons.di.guice;

import com.google.inject.AbstractModule;
import fr.prodrivers.minecraft.commons.chat.MessageSender;
import fr.prodrivers.minecraft.commons.parties.PartyManager;
import fr.prodrivers.minecraft.commons.commands.ACFCommandManagerProvider;
import fr.prodrivers.minecraft.commons.sections.SectionManager;
import fr.prodrivers.minecraft.commons.storage.DataSourceConfigProvider;
import fr.prodrivers.minecraft.commons.storage.EbeanProvider;
import fr.prodrivers.minecraft.commons.ui.section.SelectionUI;

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
	/**
	 * Make class non-instantiable
	 */
	private ProdriversCommonsGuiceModule() {
	}
}
