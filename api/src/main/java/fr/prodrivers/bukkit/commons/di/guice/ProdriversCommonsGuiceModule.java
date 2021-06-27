package fr.prodrivers.bukkit.commons.di.guice;

import com.google.inject.AbstractModule;
import fr.prodrivers.bukkit.commons.parties.PartyManager;

/**
 * Public ProdriversCommmons module for Guice dependency injector.
 * <p>
 * Exposes all public parts of Prodrivers Commons to your dependency injector instance. You will need to use it to
 * interact with all parts of Prodrivers Commons that requires dependency injection.
 * <p>
 * Currently, the following classes are accessible through this module :
 * <ul>
 *     <li>{@link PartyManager PartyManager}
 *     <li>{@link fr.prodrivers.bukkit.commons.sections.SectionManager SectionManager}
 *     <li>{@link java.sql.Connection Connection} through {@link fr.prodrivers.bukkit.commons.storage.SQLProvider SQLProvider}
 *     <li>{@link io.ebean.Database Database} through {@link fr.prodrivers.bukkit.commons.storage.EbeanProvider EbeanProvider}
 *     <li>{@link io.ebean.datasource.DataSourceConfig DataSourceConfig} through {@link fr.prodrivers.bukkit.commons.storage.DataSourceConfigProvider DataSourceConfigProvider}
 * </ul>
 */
public class ProdriversCommonsGuiceModule extends AbstractModule {
}
