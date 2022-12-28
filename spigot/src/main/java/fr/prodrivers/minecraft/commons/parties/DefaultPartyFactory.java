package fr.prodrivers.minecraft.commons.parties;

import java.util.UUID;

public interface DefaultPartyFactory {
	DefaultParty create(UUID ownerUniqueId);
}
