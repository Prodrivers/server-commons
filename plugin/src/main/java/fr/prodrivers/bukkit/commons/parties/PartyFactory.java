package fr.prodrivers.bukkit.commons.parties;

import java.util.UUID;

public interface PartyFactory {
	Party create(UUID ownerUniqueId);
}
