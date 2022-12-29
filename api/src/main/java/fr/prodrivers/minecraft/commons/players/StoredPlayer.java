package fr.prodrivers.minecraft.commons.players;

import io.ebean.Database;
import io.ebean.Model;
import io.ebean.Transaction;
import io.ebean.annotation.Cache;
import io.ebean.annotation.NotNull;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Stored Player
 * <p>
 * Base model for storing cross-plugin and cross-medium data related to players.
 * Include most commons values across plugins.
 * <p>
 *  This model can be extended in your plugin to add other attributes.
 */
@MappedSuperclass
@Table(name = "players")
@Cache
public class StoredPlayer extends Model {
	@Id
	@Column(nullable = false)
	@NotNull
	private UUID uniqueId;

	@Column(length = 180, nullable = false)
	@NotNull
	private String name;

	@Column(nullable = false)
	@NotNull
	private Date lastSeen = new Date();

	@Column(nullable = false)
	@NotNull
	private boolean connected = false;

	@Column(nullable = false)
	@NotNull
	private double money;

	@Column(length = 7, nullable = false)
	@NotNull
	private String language = "en-US";

	private List<PlayerDisciplinaryAction> disciplinaryActions = new ArrayList<>();

	/**
	 * Create a new stored player for player's provided Unique ID
	 *
	 * @param uniqueId Player's unique ID
	 */
	public StoredPlayer(UUID uniqueId) {
		this.uniqueId = uniqueId;
	}

	@Override
	public Database db() {
		throw new UnsupportedOperationException("To avoid conflicting Ebean databases, use Database.save() instead.");
	}

	/**
	 * Disable method to avoid conflicting Ebean databases, as this method may use the first registered Ebean instance.
	 */
	@Override
	public void save() {
		throw new UnsupportedOperationException("To avoid conflicting Ebean databases, use Database.save() instead.");
	}

	/**
	 * Disable method to avoid conflicting Ebean databases, as this method may use the first registered Ebean instance.
	 *
	 * @param transaction Unused
	 */
	@Override
	public void save(Transaction transaction) {
		throw new UnsupportedOperationException("To avoid conflicting Ebean databases, use Database.save() instead.");
	}

	/**
	 * Disable method to avoid conflicting Ebean databases, as this method may use the first registered Ebean instance.
	 */
	@Override
	public void flush() {
		throw new UnsupportedOperationException("To avoid conflicting Ebean databases, use Database.save() instead.");
	}

	/**
	 * Disable method to avoid conflicting Ebean databases, as this method may use the first registered Ebean instance.
	 */
	@Override
	public void update() {
		throw new UnsupportedOperationException("To avoid conflicting Ebean databases, use Database.save() instead.");
	}

	/**
	 * Disable method to avoid conflicting Ebean databases, as this method may use the first registered Ebean instance.
	 *
	 * @param transaction Unused
	 */
	@Override
	public void update(Transaction transaction) {
		throw new UnsupportedOperationException("To avoid conflicting Ebean databases, use Database.save() instead.");
	}

	/**
	 * Disable method to avoid conflicting Ebean databases, as this method may use the first registered Ebean instance.
	 */
	@Override
	public void insert() {
		throw new UnsupportedOperationException("To avoid conflicting Ebean databases, use Database.save() instead.");
	}

	/**
	 * Disable method to avoid conflicting Ebean databases, as this method may use the first registered Ebean instance.
	 *
	 * @param transaction Unused
	 */
	@Override
	public void insert(Transaction transaction) {
		throw new UnsupportedOperationException("To avoid conflicting Ebean databases, use Database.save() instead.");
	}

	/**
	 * Disable method to avoid conflicting Ebean databases, as this method may use the first registered Ebean instance.
	 */
	@Override
	public boolean delete() {
		throw new UnsupportedOperationException("To avoid conflicting Ebean databases, use Database.save() instead.");
	}

	/**
	 * Disable method to avoid conflicting Ebean databases, as this method may use the first registered Ebean instance.
	 *
	 * @param transaction Unused
	 */
	@Override
	public boolean delete(Transaction transaction) {
		throw new UnsupportedOperationException("To avoid conflicting Ebean databases, use Database.save() instead.");
	}

	/**
	 * Disable method to avoid conflicting Ebean databases, as this method may use the first registered Ebean instance.
	 */
	@Override
	public boolean deletePermanent() {
		throw new UnsupportedOperationException("To avoid conflicting Ebean databases, use Database.save() instead.");
	}

	/**
	 * Disable method to avoid conflicting Ebean databases, as this method may use the first registered Ebean instance.
	 *
	 * @param transaction Unused
	 */
	@Override
	public boolean deletePermanent(Transaction transaction) {
		throw new UnsupportedOperationException("To avoid conflicting Ebean databases, use Database.save() instead.");
	}

	/**
	 * Disable method to avoid conflicting Ebean databases, as this method may use the first registered Ebean instance.
	 */
	@Override
	public void refresh() {
		throw new UnsupportedOperationException("To avoid conflicting Ebean databases, use Database.save() instead.");
	}

	/**
	 * Get stored player's unique ID
	 *
	 * @return Stored player's unique ID
	 */
	public UUID getUniqueId() {
		return uniqueId;
	}

	/**
	 * Get stored player's name
	 *
	 * @return Stored player's name
	 */
	public @NonNull String getName() {
		return name;
	}

	/**
	 * Set stored player's name
	 *
	 * @param name Stored player's name
	 */
	public void setName(@NonNull String name) {
		this.name = name;
	}

	/**
	 * Get stored player's last seen date
	 *
	 * @return Stored player's last seen date
	 */
	public @NonNull Date getLastSeen() {
		return lastSeen;
	}

	/**
	 * Set stored player's last seen date
	 *
	 * @param lastSeen Stored player's last seen date
	 */
	public void setLastSeen(@NonNull Date lastSeen) {
		this.lastSeen = lastSeen;
	}

	/**
	 * Get stored player's connection status
	 *
	 * @return Stored player's connection status
	 */
	public boolean isConnected() {
		return connected;
	}

	/**
	 * Set stored player's connection status
	 *
	 * @param connected Stored player's connection status
	 */
	public void setConnected(boolean connected) {
		this.connected = connected;
	}

	/**
	 * Get stored player's amount of money
	 *
	 * @return Stored player's amount of money
	 */
	public double getMoney() {
		return money;
	}

	/**
	 * Set stored player's amount of money
	 *
	 * @param money Stored player's amount of money
	 */
	public void setMoney(double money) {
		this.money = money;
	}

	/**
	 * Get stored player's language
	 *
	 * @return Stored player's language
	 */
	public @NonNull String getLanguage() {
		return language;
	}

	/**
	 * Set stored player's language
	 *
	 * @param language Stored player's language
	 */
	public void setLanguage(@NonNull String language) {
		this.language = language;
	}

	/**
	 * Get disciplinary actions made against player
	 *
	 * @return Player's disciplinary actions
	 */
	public List<PlayerDisciplinaryAction> getDisciplinaryActions() {
		return disciplinaryActions;
	}

	/**
	 * Add a disciplinary actions to a player
	 *
	 * @param disciplinaryAction Player's disciplinary actions
	 */
	public void addDisciplinaryAction(PlayerDisciplinaryAction disciplinaryAction) {
		this.disciplinaryActions.add(disciplinaryAction);
	}

	/**
	 * Remove a disciplinary actions from a player
	 *
	 * @param disciplinaryAction Player's disciplinary actions
	 */
	public void removeDisciplinaryAction(PlayerDisciplinaryAction disciplinaryAction) {
		this.disciplinaryActions.remove(disciplinaryAction);
	}

	/**
	 * Remove expired disciplinary actions from a player
	 */
	public void removeExpiredDisciplinaryActions() {
		this.disciplinaryActions = this.disciplinaryActions.stream()
				.filter(PlayerDisciplinaryAction::isApplicable)
				.collect(Collectors.toList());
	}
}
