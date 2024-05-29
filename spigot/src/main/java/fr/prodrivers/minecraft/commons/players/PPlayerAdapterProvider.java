package fr.prodrivers.minecraft.commons.players;

import io.ebean.Database;

import javax.inject.Inject;
import javax.inject.Provider;

public class PPlayerAdapterProvider implements Provider<PPlayerAdapter> {
	private final PPlayerAdapter adapter;

	@Inject
	public PPlayerAdapterProvider(Database database) {
		this.adapter = new PaperPPlayerAdapter(database);
	}

	@Override
	public PPlayerAdapter get() {
		return adapter;
	}
}
