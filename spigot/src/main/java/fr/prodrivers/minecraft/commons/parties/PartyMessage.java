package fr.prodrivers.minecraft.commons.parties;

/**
 * Describe a list of possible party messages
 * <p>
 * Party messages are triggered by the party manager, the party implementation is responsible for sending the actual
 * message to the players.
 * <p>
 * Messages
 */
public enum PartyMessage {
	/**
	 * Party was disbanded
	 */
	DISBANDED,
	/**
	 * You joined a party
	 */
	JOINED_YOU,
	/**
	 * A player joined your party
	 */
	JOINED_OTHERS,
	/**
	 * You left a party
	 */
	LEFT_YOU,
	/**
	 * A player left your party
	 */
	LEFT_OTHERS,
	/**
	 * You cannot invite yourself
	 */
	CANNOT_INVITE_YOURSELF,
	/**
	 * You are not a party owner
	 */
	NOT_PARTY_OWNER_YOU,
	/**
	 * You invited a player to your party
	 */
	PLAYER_INVITED_INVITER,
	/**
	 * You were invited to a party
	 */
	PLAYER_INVITED_YOU,
	/**
	 * Player is already in a party
	 */
	PLAYER_IS_IN_PARTY,
	/**
	 * You must leave your party before joining another
	 */
	PARTY_LEAVE_BEFORE_JOINING_ANOTHER,
	/**
	 * You were not invited to this party
	 */
	PARTY_NOT_INVITED_YOU,
	/**
	 * You were elected as new party leader
	 */
	PARTY_LEADER_ASSIGNED_YOU,
	/**
	 * A player was elected as new party leader
	 */
	PARTY_LEADER_ASSIGNED_OTHERS
}
