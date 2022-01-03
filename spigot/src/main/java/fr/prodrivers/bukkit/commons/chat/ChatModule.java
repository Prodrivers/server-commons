package fr.prodrivers.bukkit.commons.chat;

import com.google.inject.AbstractModule;
import fr.prodrivers.bukkit.commons.plugin.EConfiguration;

import javax.inject.Inject;

public class ChatModule extends AbstractModule {
	private final Class<? extends MessageSender> messageSenderClass;

	@Inject
	@SuppressWarnings("unchecked")
	public ChatModule(EConfiguration configuration) {
		try {
			this.messageSenderClass = (Class<? extends MessageSender>) Class.forName(configuration.providers_MessageSender);
		} catch(ClassNotFoundException | ClassCastException e) {
			throw new RuntimeException("Invalid section manager provider: " + configuration.providers_MessageSender, e);
		}
	}

	@Override
	protected void configure() {
		bind(MessageSender.class).to(messageSenderClass);
	}
}
