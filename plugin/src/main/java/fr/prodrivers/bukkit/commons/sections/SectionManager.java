package fr.prodrivers.bukkit.commons.sections;

import fr.prodrivers.bukkit.commons.exceptions.*;
import fr.prodrivers.bukkit.commons.parties.Party;
import fr.prodrivers.bukkit.commons.parties.PartyManager;
import fr.prodrivers.bukkit.commons.plugin.Main;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.function.Consumer;
import java.util.logging.Level;

/**
 * Prodrivers Commons Section Manager
 */
public class SectionManager {
	public final static String ROOT_NODE_NAME = "";

	private final static Map<String, Section> sections = new HashMap<>();
	private final static Map<UUID, Section> playersCurrentSection = new HashMap<>();
	private final static Set<UUID> inEnter = new HashSet<>();
	private final static Map<UUID, List<Section>> playersSectionPath = new HashMap<>();

	private static MainHub mainHub;

	public static void init(JavaPlugin plugin) {
		new SectionListener(plugin);

		mainHub = new MainHub();
		register(mainHub);
	}

	public static void reload() {
		mainHub.reload();
	}

	public static void enter(Player player) throws NoCurrentSectionException, NoParentSectionException, IllegalSectionLeavingException, IllegalSectionEnteringException {
		// Get the current player section
		Section currentSection = playersCurrentSection.get(player.getUniqueId());

		// If it is null
		if(currentSection == null) {
			// Stop everything and inform
			throw new NoCurrentSectionException("Player should indicate a section to join");
		}

		Section parentSection = currentSection.getParentSection();

		// If it is null
		if(parentSection == null) {
			// Stop everything and inform
			throw new NoParentSectionException("Current section doesn't have a parent section, player is probably at the root.");
		}

		// Send the player to the parent section
		enter(player, currentSection, parentSection);
	}

	public static void enter(Player player, String sectionName) throws InvalidSectionException, IllegalSectionLeavingException, IllegalSectionEnteringException, NoRegisteredParentSectionException {
		// Get the target section
		Section targetNode = getSection(sectionName);

		// If the section doesn't exist
		if(targetNode == null) {
			// Stop everything and inform
			throw new InvalidSectionException("Invalid section name");
		}

		enter(player, targetNode);
	}

	public static void enter(Player player, Section targetNode) throws InvalidSectionException, IllegalSectionLeavingException, IllegalSectionEnteringException, NoRegisteredParentSectionException {
		// Check that all party players can enter, if necessary
		// If the section handles party by itself
		if(!targetNode.getCapabilities().contains(SectionCapabilities.PARTY_AWARE)) {
			Party party = PartyManager.getParty(player.getUniqueId());

			// If player is in party
			if(party != null) {
				// Check party owner
				if(party.getOwnerUniqueId() != player.getUniqueId()) {
					// Player is not party owner
					if(!targetNode.getCapabilities().contains(SectionCapabilities.HUB)) {
						// Target section is not a hub, forbid moving
						throw new NotPartyOwnerException("Player tried to join a section while not being party owner.");
					}
					// Target section is a hub, proceed normally
				} else {
					// Player is party owner
					// Move all party players, except the owner, to the target section
					for(UUID partyPlayerUUID : party.getPlayers()) {
						// Get party player
						Player partyPlayer = Main.getPlugin().getServer().getPlayer(partyPlayerUUID);
						if(partyPlayer != null) {
							// Get player current section
							Section currentSection = playersCurrentSection.get(player.getUniqueId());
							// Check that the player can go to the section
							if(!canPlayerWalkAlongSectionPath(player, currentSection, targetNode)) {
								// If not, stop everything
								return;
							}
						}
					}
				}
			}
		}

		// Get the left section
		Section currentSection = playersCurrentSection.get(player.getUniqueId());

		// Process player entering
		enter(player, currentSection, targetNode);
	}

	private static void enter(Player player, Section leftNode, Section targetNode) throws IllegalSectionLeavingException, IllegalSectionEnteringException, NoRegisteredParentSectionException {
		// If the player is already in an enter process
		if(inEnter.contains(player.getUniqueId())) {
			// Stop
			return;
		}

		Main.logger.fine(player.getName() + " entering section : " + targetNode);

		// Check that the player can walk along the path
		if(!canPlayerWalkAlongSectionPath(player, leftNode, targetNode)) {
			// If not, stop everything
			return;
		}

		// Get the path that the player have to follow
		List<Section> nodesToVisit = playersSectionPath.get(player.getUniqueId());
		assert nodesToVisit != null;
		assert nodesToVisit.size() > 0;

		// The check pass guarantee us that the player can not walk down to a transitive node. However, he can still
		// walk up to one. In this case, go up until we encounter a non-transitive node.
		while(nodesToVisit.size() > 0 && nodesToVisit.get(nodesToVisit.size() - 1) instanceof TransitiveSection) {
			// Until the path contains transitive node at the end, add their parent node.
			Section parent = nodesToVisit.get(nodesToVisit.size() - 1).getParentSection();
			assert parent != null;
			nodesToVisit.add(parent);
			// Modify target to parent
			targetNode = parent;
		}

		// Register that the player is in an enter process
		inEnter.add(player.getUniqueId());

		Main.logger.finest("Path to travel : " + nodesToVisit);

		Main.logger.finest("Left : " + leftNode);
		Main.logger.finest("Target : " + targetNode);

		// Successively enter and leave sections along the path
		for(Section node : nodesToVisit) {
			// If we are not considering the first node, as the player is already in this node
			if(node != leftNode) {
				if(!node.join(player)) {
					// The node refused the player to enter, stop processing.
					Main.logger.severe("Section " + node + " refused player " + player.getName() + " to enter.");
					break;
				}

				// Register the target section as current section for the player
				playersCurrentSection.put(player.getUniqueId(), node);
			}
			// If we are not considering the last node, as the player should stay in it
			if(node != targetNode) {
				if(!node.leave(player)) {
					// The node refused the player to leave, stop processing.
					Main.logger.severe("Section " + node + " refused player " + player.getName() + " to leave.");
					break;
				}

				// Remove the corresponding section as this player's current section
				playersCurrentSection.remove(player.getUniqueId());
			}
		}

		// Indicate that the player finished its enter process, remove temporary values
		inEnter.remove(player.getUniqueId());
		playersSectionPath.remove(player.getUniqueId());

		// Make entering player party's players move if necessary
		if(!targetNode.getCapabilities().contains(SectionCapabilities.PARTY_AWARE)) {
			Party party = PartyManager.getParty(player.getUniqueId());

			// If player is in party
			if(party != null) {
				// If player is party owner
				if(party.getOwnerUniqueId() == player.getUniqueId()) {
					// Move all party players, except the owner, to the target section
					for(UUID partyPlayerUUID : party.getPlayers()) {
						// Get party player
						Player partyPlayer = Main.getPlugin().getServer().getPlayer(partyPlayerUUID);
						if(partyPlayer != null) {
							// Move player to section
							SectionManager.enter(player, targetNode);
						}
					}
				}
			}
		}
	}

	private static boolean canPlayerWalkAlongSectionPath(Player player, Section leftNode, Section targetNode) {
		// If there is already a temporary path stored
		if(playersSectionPath.containsKey(player.getUniqueId())) {
			// Do not make the computation again
			return true;
		}

		// Initialize array that contains all nodes to traverse, to avoid duplicate computations between check and move
		// passes
		List<Section> nodesToVisit = new ArrayList<>();

		// Set common node, between left and target node, as the root node, in case there is no left node as it will
		// be the start point to go to the target node
		Section commonNode = getRootSection();

		try {
			// If the player is already in a section
			if(leftNode != null) {
				// Find the common node with left node and target node
				commonNode = findCommonNode(leftNode, targetNode);

				// Walk back player to common node with target node
				if(!walkBackward(player, leftNode, commonNode, nodesToVisit)) {
					return false;
				}
			}

			// Walk front player to common node with target node
			if(!walkForward(player, commonNode, targetNode, nodesToVisit)) {
				return false;
			}
		} catch(Exception e) {
			Main.logger.log(Level.SEVERE, "Error encountered during section tree traversal.", e);
		}

		// Store computed path
		playersSectionPath.put(player.getUniqueId(), nodesToVisit);

		return true;
	}

	/**
	 * Go from a section to another section higher in the tree. The last node is either the provided target node or
	 * the top-most node of the left node's branch.
	 * At the end, the player is registered in the final processed node.
	 *
	 * @param player       The player that should walk back nodes.
	 * @param start        The node that we start from.
	 * @param target       The node, higher in the tree, that we want the player to go to at the end of this function.
	 * @param visitedNodes List that should receive all visited nodes
	 * @return @{code true} success of walking back. @{code false} if any section did not authorized the player leaving or entering it.
	 */
	private static boolean walkBackward(Player player, Section start, Section target, List<Section> visitedNodes) {
		// Perform a leave check on the start node, as we consider the starting node of this function to be the node the
		// player is in
		if(!start.preLeave(player)) {
			// The current node does not want us to leave it. Stop going back.
			return false;
		}

		// Add the parent node to the list of visited nodes
		visitedNodes.add(start);

		// Walk up the tree, starting from the parent node (the start node is handled above)
		Section node;
		for(node = start.getParentSection(); node != null; node = node.getParentSection()) {
			if(!node.preJoin(player)) {
				// The current node does not want us to enter it. Stop going back.
				Main.logger.severe("Node " + node + " refused player " + player + " to enter with its enter check.");
				return false;
			}

			// Do a leave check for the parent
			if(!node.preLeave(player)) {
				// The current node does not want us to leave it. Stop going back.
				Main.logger.severe("Node " + node + " refused player " + player + " to leave with its leave check.");
				return false;
			}

			// Add the parent node to the list of visited nodes
			visitedNodes.add(node);

			if(node.equals(target)) {
				// We attained the target node, stop going back.
				return true;
			}

			// Check if there is a parent node
			if(node.getParentSection() == null) {
				// If not, stop everything as the requested path is invalid
				throw new InvalidSectionException("Section " + node + " has no parent, cannot walk back from it when going from " + start + " to " + target);
			}
		}

		return true;
	}

	/**
	 * Go from a section to another section lower in the tree. The last node is either the provided target node or
	 * the bottom-most node of the left node's branch.
	 * At the end, the player is registered in the final processed node.
	 *
	 * @param player       The player that should walk back nodes.
	 * @param start        The node that we start from.
	 * @param target       The node, lower in the tree, that we want the player to go to at the end of this function.
	 * @param visitedNodes List that should receive all visited nodes
	 * @return @{code true} success of walking back. @{code false} if any section did not authorized the player leaving or entering it.
	 */
	private static boolean walkForward(Player player, Section start, Section target, List<Section> visitedNodes) {
		// Get the sections to walk to
		LinkedList<Section> sectionsToWalk = new LinkedList<>();
		// Go up from the target node until we hit the start node, remembering all nodes we passed through
		for(Section currentNode = target; currentNode != start; currentNode = currentNode.parent) {
			if(currentNode == null) {
				// We attained a node with no parent on our path, stop everything as the requested path is invalid
				throw new InvalidSectionException("Section " + (sectionsToWalk.peekLast() != null ? sectionsToWalk.peekLast().getFullName() : null) + " has no parent, cannot walk forward when going to it from " + start + " to " + target);
			}
			sectionsToWalk.add(currentNode);
		}
		// If the start node and the target node are the root node, add it (as it is not added by the previous loop,
		// only happens when logging in)
		if(start == target && start == getRootSection()) {
			sectionsToWalk.add(target);
		}
		// If the first node of the list is a transitive node (meaning that we will walk down to a transitive node)
		if(!sectionsToWalk.isEmpty() && sectionsToWalk.peekFirst() instanceof TransitiveSection) {
			// Stop everything
			throw new InvalidSectionException("End node is a transitive node, which is invalid.");
		}
		// Go through the list in reverse order, as they were inserted finish to start
		for(Iterator<Section> it = sectionsToWalk.descendingIterator(); it.hasNext(); ) {
			Section node = it.next();
			// If we are not at the first iteration (we consider the starting node of this function to be the node the
			// player is in)
			if(node != start) {
				if(!node.preJoin(player)) {
					// The current node does not want us to enter it. Stop going back.
					Main.logger.severe("Node " + node + " refused player " + player + " to enter with its leave check.");
					return false;
				}
			}
			if(!node.preLeave(player)) {
				// The current node does not want us to leave it. Stop going forward.
				Main.logger.severe("Node " + node + " refused player " + player + " to leave with its leave check.");
				return false;
			}
			// Add the parent node to the list of visited nodes
			visitedNodes.add(node);
		}
		// We walked over all sections, consider it a success
		// All sections we have to go through are in the sectionsToWalk list, just pass it
		//visitedNodes.addAll(sectionsToWalk);
		return true;
	}

	private static boolean leaveNode(Player player, Section node) {
		// Inform the corresponding section, to check if the section wants to stop
		try {
			if(!node.leave(player)) {
				// We stop everything
				return false;
			}

			// Remove the corresponding section as this player's current section
			playersCurrentSection.remove(player.getUniqueId());

			// Unregister the player in the section
			removeInSection(node, player);
		} catch(Exception e) {
			Main.logger.log(Level.SEVERE, "Could not execute leave call of section " + node, e);
		}

		return true;
	}

	private static boolean enterNode(Player player, Section node) {
		// Inform the target section that the player wants to join
		// If the section wants to stop
		try {
			if(!node.join(player)) {
				// If this is the root node
				if(node.getFullName().equals(ROOT_NODE_NAME)) {
					// Kick the player as there is probably no safe place to put him
					player.kickPlayer(Main.getMessages().kicked_because_no_safe_place);
					Main.logger.severe("Kicked player " + player.getName() + " as node" + node + " refused it to join.");
					return false;
				} else {
					// Stop everything and inform, put the player to the main lobby
					SectionManager.enter(player, ROOT_NODE_NAME);
					throw new IllegalSectionEnteringException("Player should not go to the selected section");
				}
			}

			// Register the target section as current section for the player
			playersCurrentSection.put(player.getUniqueId(), node);

			// Register the player in the section
			addInSection(node, player);
		} catch(Exception e) {
			Main.logger.log(Level.SEVERE, "Could not execute enter call of section " + node, e);
		}

		return true;
	}

	private static Section findCommonNode(Section left, Section target) {
		// Get tree branches for left and target sections
		List<String> leftNodes = left.getSplitFullName();
		List<String> targetNodes = target.getSplitFullName();

		StringBuilder commonNodeName = new StringBuilder();
		for(int i = 0; i < leftNodes.size() && i < targetNodes.size(); i++) {
			String leftNode = leftNodes.get(i);
			if(leftNode.equals(targetNodes.get(i))) {
				if(commonNodeName.length() > 0) {
					commonNodeName.append(".");
				}
				commonNodeName.append(leftNode);
			}
		}

		return getSection(commonNodeName.toString());
	}

	private static void addInSection(Section targetSection, Player player) {
		for(Section section = targetSection; section != null; section = section.getParentSection()) {
			section.add(player);
		}
	}

	private static void removeInSection(Section leftSection, Player player) {
		for(Section section = leftSection; section != null; section = section.getParentSection()) {
			section.remove(player);
		}
	}

	private static class KeyTreeNode {
		public final String key;
		public final String fullKey;
		public final String parentFullKey;
		public final Map<String, KeyTreeNode> children = new HashMap<>();

		public KeyTreeNode(String key, String fullKey) {
			this.key = key;
			this.fullKey = fullKey;
			List<String> splitFullKey = Section.getSplitName(fullKey);
			if(!splitFullKey.isEmpty()) {
				splitFullKey.remove(splitFullKey.size() - 1);
			}
			this.parentFullKey = String.join(".", splitFullKey);
		}

		public boolean equals(String key) {
			return this.key.equals(key);
		}

		public void explore(Consumer<KeyTreeNode> visitor) {
			// Breath-explore the tree under this node
			Queue<KeyTreeNode> keyNodeQueue = new LinkedList<>();
			// Start with the key root node
			keyNodeQueue.add(this);
			// While there are still key nodes to explore
			while(!keyNodeQueue.isEmpty()) {
				// Get a key node to consider
				KeyTreeNode currentKeyNode = keyNodeQueue.remove();
				// Visit the node
				visitor.accept(currentKeyNode);
				// Add all its children to the queue
				keyNodeQueue.addAll(currentKeyNode.children.values());
			}
		}
	}

	public static void buildSectionTree() {
		// First, go through all registered sections and build a tree from their names, based on the separators in their
		// names
		KeyTreeNode keyTreeRoot = new KeyTreeNode(ROOT_NODE_NAME, ROOT_NODE_NAME);

		// Go through all registered nodes
		for(Section section : sections.values()) {
			// Go to the key tree root node
			KeyTreeNode keyTreeNode = keyTreeRoot;
			StringBuilder nodeFullName = new StringBuilder();
			// For each node indicated in the section's name
			for(int i = 0; i < section.getSplitFullName().size(); i++) {
				final String nodeName = section.getSplitFullName().get(i);
				final String currentNodeFullName = section.getParentsFullName().get(i);
				// Add a corresponding node in the tree if it does not exists already
				keyTreeNode.children.computeIfAbsent(nodeName, k -> new KeyTreeNode(k, currentNodeFullName));
				// Go to the corresponding key node
				keyTreeNode = keyTreeNode.children.get(nodeName);
				// Then test the next node in the name by looping
			}
		}

		// Now that we have a tree of all section nodes name, we can use it to fill all missing nodes : we create
		// transitive sections whenever we have a non-existent section for a key node, because it implies one should
		// exists
		keyTreeRoot.explore(currentKeyNode -> {
			// Get the corresponding section
			Section currentSection = getSection(currentKeyNode.fullKey);
			// Proceed only if the node does not exists
			if(currentSection == null) {
				// Fill in a transitive node
				currentSection = new TransitiveSection(currentKeyNode.fullKey);
				register(currentSection);
			}
		});

		// We now have all sections registered, even the implied transitive ones
		// We can finally link all sections with each other using the section key tree, building the final section tree
		keyTreeRoot.explore(currentKeyNode -> {
			// Get the corresponding section
			Section currentSection = getSection(currentKeyNode.fullKey);
			// Process it only if it exists (we will fill the missing node after)
			if(currentSection != null) {
				// Complete the node
				// Get the parent and add it to the current node
				currentSection.parent = getSection(currentKeyNode.parentFullKey);
				// Go through all children keys, get the sections and add them as children to the section
				currentKeyNode.children.values()
						.stream()
						.map(node -> getSection(node.fullKey))
						.filter(Objects::nonNull)
						.forEach(currentSection::addChildren);
			}
		});
	}

	public static void register(Section section) throws NullPointerException {
		register(section, false);
	}

	public static void register(Section section, boolean silent) throws NullPointerException {
		if(section == null)
			throw new NullPointerException();

		// If section is not already registered
		if(!sections.containsKey(section.getFullName())) {
			// Register section
			sections.put(section.getFullName(), section);

			if(!silent) {
				Main.logger.info("SectionManager registered section " + section + ".");
			}
		} else {
			Main.logger.warning("Section " + section + " tried to be registered for a second time.");
		}
	}

	public static void unregister(OfflinePlayer player) {
		Section section = getCurrentSection(player);

		playersCurrentSection.remove(player.getUniqueId());
		playersSectionPath.remove(player.getUniqueId());

		if(section != null) {
			if(section.preLeave(player)) {
				section.leave(player);
			} else {
				Main.logger.severe("Could not make player " + player.getName() + " leave section " + section);
			}
		}
	}

	public static Section getCurrentSection(OfflinePlayer player) {
		return playersCurrentSection.get(player.getUniqueId());
	}

	public static Section getSection(String name) {
		return sections.get(name);
	}

	public static Section getRootSection() {
		return getSection(ROOT_NODE_NAME);
	}
}
