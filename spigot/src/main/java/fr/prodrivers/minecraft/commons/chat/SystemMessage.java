package fr.prodrivers.minecraft.commons.chat;

import fr.prodrivers.minecraft.commons.configuration.Messages;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.UUID;

@Singleton
public class SystemMessage {
	private final MessageSender messageSender;

	private String rawPrefix = "[<name>]";
	private Component prefix = Component.text("[<name>]");
	private String prefixLegacy = "[<name>]";
	private String name;

	@Inject
	public SystemMessage(@NonNull MessageSender messageSender) {
		this.messageSender = messageSender;
	}

	public void setName(@NonNull String name) {
		this.name = name;
		this.prefix = LegacyComponentSerializer.legacySection().deserialize(this.rawPrefix.replaceAll("<name>", this.name));
		this.prefixLegacy = this.rawPrefix.replaceAll("<name>", this.name);
	}

	public void load(@NonNull Messages messages) {
		if(messages != null && messages.prefix != null) {
			this.rawPrefix = messages.prefix;
		}
		this.prefix = LegacyComponentSerializer.legacySection().deserialize(this.rawPrefix.replaceAll("<name>", this.name));
		this.prefixLegacy = this.rawPrefix.replaceAll("<name>", this.name);
	}

	private void send(@NonNull Audience receiver, @NonNull Component message, @NonNull Component prefix) {
		this.messageSender.send(receiver, message, prefix);
	}

	public void success(@NonNull Audience receiver, @NonNull Component message) {
		success(receiver, message, this.prefix);
	}

	public void success(@NonNull Audience receiver, @NonNull Component message, @NonNull Component prefix) {
		send(receiver, Component.text().color(NamedTextColor.GREEN).append(message).build(), prefix);
	}

	public void info(@NonNull Audience receiver, @NonNull Component message) {
		info(receiver, message, this.prefix);
	}

	public void info(@NonNull Audience receiver, @NonNull Component message, @NonNull Component prefix) {
		send(receiver, message, prefix);
	}

	public void error(@NonNull Audience receiver, @NonNull Component message) {
		error(receiver, message, this.prefix);
	}

	public void error(@NonNull Audience receiver, @NonNull Component message, @NonNull Component prefix) {
		send(receiver, Component.text().color(NamedTextColor.RED).append(message).build(), prefix);
	}

	private void send(@NonNull UUID receiverPlayerUniqueId, @NonNull Component message, @NonNull Component prefix) {
		this.messageSender.send(receiverPlayerUniqueId, message, prefix);
	}

	public void success(@NonNull UUID receiverPlayerUniqueId, @NonNull Component message) {
		success(receiverPlayerUniqueId, message, this.prefix);
	}

	public void success(@NonNull UUID receiverPlayerUniqueId, @NonNull Component message, @NonNull Component prefix) {
		send(receiverPlayerUniqueId, Component.text().color(NamedTextColor.GREEN).append(message).build(), prefix);
	}

	public void info(@NonNull UUID receiverPlayerUniqueId, @NonNull Component message) {
		info(receiverPlayerUniqueId, message, this.prefix);
	}

	public void info(@NonNull UUID receiverPlayerUniqueId, @NonNull Component message, @NonNull Component prefix) {
		send(receiverPlayerUniqueId, message, prefix);
	}

	public void error(@NonNull UUID receiverPlayerUniqueId, @NonNull Component message) {
		error(receiverPlayerUniqueId, message, this.prefix);
	}

	public void error(@NonNull UUID receiverPlayerUniqueId, @NonNull Component message, @NonNull Component prefix) {
		send(receiverPlayerUniqueId, Component.text().color(NamedTextColor.RED).append(message).build(), prefix);
	}

	public String getPrefixLegacy() {
		return prefixLegacy;
	}
}
