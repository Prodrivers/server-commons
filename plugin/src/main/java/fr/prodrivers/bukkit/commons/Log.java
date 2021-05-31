package fr.prodrivers.bukkit.commons;

import fr.prodrivers.bukkit.commons.configuration.Configuration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Log {
	private final Logger logger;

	public Log(JavaPlugin plugin, Configuration configuration) {
		logger = plugin.getLogger();
		logger.setLevel(configuration.logLevel);
		for(Handler handler : logger.getHandlers()) {
			handler.setLevel(configuration.logLevel);
		}
	}

	public void config(String msg) {
		logger.log(Level.CONFIG, msg);
	}

	public void config(String msg, Throwable thrown) {
		logger.log(Level.CONFIG, msg, thrown);
	}

	public void finest(String msg) {
		logger.log(Level.FINEST, msg);
	}

	public void finest(String msg, Throwable thrown) {
		logger.log(Level.FINEST, msg, thrown);
	}

	public void finer(String msg) {
		logger.log(Level.FINER, msg);
	}

	public void finer(String msg, Throwable thrown) {
		logger.log(Level.FINER, msg, thrown);
	}

	public void fine(String msg) {
		logger.log(Level.FINE, msg);
	}

	public void fine(String msg, Throwable thrown) {
		logger.log(Level.INFO, msg, thrown);
	}

	public void info(String msg) {
		logger.log(Level.INFO, msg);
	}

	public void info(String msg, Throwable thrown) {
		logger.log(Level.INFO, msg, thrown);
	}

	public void warning(String msg) {
		logger.log(Level.WARNING, msg);
	}

	public void warning(String msg, Throwable thrown) {
		logger.log(Level.WARNING, msg, thrown);
	}

	public void severe(String msg) {
		logger.log(Level.SEVERE, msg);
	}

	public void severe(String msg, Throwable thrown) {
		logger.log(Level.SEVERE, msg, thrown);
	}
}
