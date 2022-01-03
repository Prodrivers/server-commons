package fr.prodrivers.bukkit.commons.parties;

import java.util.UUID;

public interface DefaultPartyFactory {
	DefaultParty create(UUID ownerUniqueId);
}
