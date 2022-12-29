package fr.prodrivers.minecraft.commons.commands;

import co.aikar.commands.BukkitCommandManager;
import fr.prodrivers.minecraft.commons.configuration.Messages;

import javax.inject.Provider;
import javax.inject.Singleton;

/**
 * Aikar Command Framework provider for Prodrivers plugins. Requires dependency injection.
 * <p>
 * Aikar Command Framework, or ACF, is a Command Dispatch Framework. This allows easy implementation of Bukkit commands
 * as it takes nearly every concept of boilerplate code commonly found in command handlers, and abstracts them away
 * behind powerful annotations. It provides out-of-the-box, among other things, Validation, Tab Completion, Help
 * Documentation, Syntax Advice, and Stateful Conditions.
 * <p>
 * ACFProvider is intended to be used with a dependency injector. The command manager returned by it contains
 * integrations with {@link Messages} facility, so that all messages are
 * correctly prefixed and strings from your {@link Messages} instance can be
 * used in ACF as you would with other strings from it.
 */
@Singleton
public class ACFCommandManagerProvider implements Provider<BukkitCommandManager> {
	/**
	 * Make class non-instantiable
	 */
	private ACFCommandManagerProvider() {
	}

	/**
	 * Gets a new ACF Bukkit Command Manager.
	 *
	 * @return Database or null
	 */
	@Override
	public BukkitCommandManager get() {
		throw new UnsupportedOperationException();
	}
}