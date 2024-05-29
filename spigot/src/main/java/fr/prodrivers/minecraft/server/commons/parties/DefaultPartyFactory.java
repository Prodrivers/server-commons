package fr.prodrivers.minecraft.server.commons.parties;

import java.util.UUID;

public interface DefaultPartyFactory {
	DefaultParty create(UUID ownerUniqueId);
}
