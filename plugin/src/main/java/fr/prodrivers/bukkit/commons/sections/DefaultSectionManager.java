package fr.prodrivers.bukkit.commons.sections;

import fr.prodrivers.bukkit.commons.Log;
import fr.prodrivers.bukkit.commons.events.PlayerChangeSectionEvent;
import fr.prodrivers.bukkit.commons.exceptions.*;
import fr.prodrivers.bukkit.commons.parties.Party;
import fr.prodrivers.bukkit.commons.parties.PartyManager;
import fr.prodrivers.bukkit.commons.plugin.EMessages;
import fr.prodrivers.bukkit.commons.ui.section.SelectionUI;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.*;
import java.util.function.Consumer;

@Singleton
public class DefaultSectionManager implements SectionManager {

	private final Map<String, Section> sections = new HashMap<>();
	private final Map<UUID, Section> playersCurrentSection = new HashMap<>();
	private final Set<UUID> inEnter = new HashSet<>();
	private final Map<UUID, List<Section>> playersSectionPath = new HashMap<>();

	private final JavaPlugin plugin;
	private final EMessages messages;
	private final PartyManager partyManager;
	private final SelectionUI defaultSelectionUi;

	@Inject
	public DefaultSectionManager(JavaPlugin plugin, EMessages messages, PartyManager partyManager, SelectionUI defaultSelectionUi) {
		this.plugin = plugin;
		this.messages = messages;
		this.partyManager = partyManager;
		this.defaultSelectionUi = defaultSelectionUi;

		new SectionListener(plugin, this);
	}

	public boolean enter(Player player) throws NoCurrentSectionException, NoParentSectionException, IllegalSectionLeavingException, IllegalSectionEnteringException {
		// If the player is already in an enter process
		if(inEnter.contains(player.getUniqueId())) {
			// Stop
			return false;
		}

		// Get the current player section
		Section currentSection = playersCurrentSection.get(player.getUniqueId());

		// If it is null
		if(currentSection == null) {
			// Stop everything and inform
			throw new NoCurrentSectionException("Player should be registered before using this method.");
		}

		Section parentSection = currentSection.getParentSection();

		// If it is null and we are not at the root section (we allow it on root section so that custom logic can be
		// used to exit server)
		if(parentSection == null && !SectionManager.ROOT_NODE_NAME.equals(currentSection.getFullName())) {
			// Stop everything and inform
			throw new NoParentSectionException("Current section doesn't have a parent section, player is probably at the root.");
		}

		// Send the player to the parent section
		return enter(player, parentSection);
	}

	public boolean enter(Player player, String sectionName) throws InvalidSectionException, IllegalSectionLeavingException, IllegalSectionEnteringException, NoParentSectionException {
		// Get the target section
		Section targetNode = getSection(sectionName);

		// If the section doesn't exist
		if(targetNode == null) {
			// Stop everything and inform
			throw new InvalidSectionException("Invalid section name");
		}

		return enter(player, targetNode);
	}

	public boolean enter(Player player, Section targetNode) throws InvalidSectionException, IllegalSectionLeavingException, IllegalSectionEnteringException, NoParentSectionException {
		return enter(player, targetNode, false);
	}

	public boolean enter(Player player, Section targetNode, boolean fromParty) throws InvalidSectionException, IllegalSectionLeavingException, IllegalSectionEnteringException, NoParentSectionException {
		// If the player is already in an enter process
		if(inEnter.contains(player.getUniqueId())) {
			// Stop
			return false;
		}

		// Get first non-transitive target section, either target itself or one of its parents, as those
		// will automatically be handled by section manager afterwards (blocked if walking down to it, going to
		// parent if walking up to it)
		Section nonTransitiveTarget = null;
		if(targetNode != null) {
			nonTransitiveTarget = targetNode.getFirstNonTransitiveSection();
			if(nonTransitiveTarget == null) {
				Log.severe("Party player " + player.getUniqueId() + " asked to join section " + targetNode + ", bu it is transitive and it has no non-transitive parent.");
				// If not, stop everything
				return false;
			}
		}

		// Check that all party players can enter, if necessary
		// If the section does not handle party by itself and the player is not sent by the party owner
		if(!fromParty && targetNode != null && !nonTransitiveTarget.getCapabilities().contains(SectionCapabilities.PARTY_AWARE)) {
			Party party = this.partyManager.getParty(player.getUniqueId());

			// If player is in party
			if(party != null) {
				// Check party owner
				if(party.getOwnerUniqueId() != player.getUniqueId()) {
					// Player is not party owner
					if(!nonTransitiveTarget.getCapabilities().contains(SectionCapabilities.HUB)) {
						// Target section is not a hub, forbid moving
						throw new NotPartyOwnerException("Player tried to join a section while not being party owner.");
					}
					// Target section is a hub, proceed normally
				} else {
					// Player is party owner
					// Move all party players, except the owner, to the target section, except if it is a hub
					if(!nonTransitiveTarget.getCapabilities().contains(SectionCapabilities.HUB)) {
						for(UUID partyPlayerUUID : party.getPlayers()) {
							if(partyPlayerUUID != party.getOwnerUniqueId()) {
								// Get party player
								Player partyPlayer = this.plugin.getServer().getPlayer(partyPlayerUUID);
								if(partyPlayer != null) {
									// Get player current section
									Section currentSection = playersCurrentSection.get(partyPlayer.getUniqueId());

									Log.fine("Checking section walk for party player " + partyPlayer + " from " + currentSection + " to " + targetNode);

									// Check that the player can go to the section
									if(!canPlayerWalkAlongSectionPath(partyPlayer, currentSection, targetNode, true)) {
										Log.severe("Party player " + partyPlayer + " cannot join or leave, stopping everything.");
										// If not, stop everything
										return false;
									}
								}
							}
						}
					}
				}
			}
		}

		// Get the left section
		Section currentSection = playersCurrentSection.get(player.getUniqueId());

		// Process player entering
		return enter(player, currentSection, targetNode, fromParty);
	}

	protected boolean enter(final Player player, final Section leftNode, Section targetNode, boolean fromParty) throws IllegalSectionLeavingException, IllegalSectionEnteringException, NoParentSectionException {
		// If the player is already in an enter process
		if(inEnter.contains(player.getUniqueId())) {
			// Stop
			Log.fine( player + " is already in enter process");
			return false;
		}

		Log.fine(player + " entering section : " + targetNode);

		// Check that the player can walk along the path
		if(!canPlayerWalkAlongSectionPath(player, leftNode, targetNode, fromParty)) {
			// Logging is already done for here
			// If not, stop everything
			return false;
		}

		// Trigger event and wait for result
		PlayerChangeSectionEvent event = new PlayerChangeSectionEvent(player, leftNode, targetNode);
		Bukkit.getPluginManager().callEvent(event);
		if(event.isCancelled()) {
			Log.warning("Section move of player " + player + " from " + leftNode + " to " + targetNode + " was canceled by event.");
			return false;
		}

		// Get the path that the player have to follow
		List<Section> nodesToVisit = playersSectionPath.get(player.getUniqueId());
		assert nodesToVisit != null;

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

		try {
			Log.finest("Player " + player + ": path to travel : " + nodesToVisit);

			Log.finest("Player " + player + ": left : " + leftNode);
			Log.finest("Player " + player + ": target : " + targetNode);

			// Special case for root node exit
			// Path traversal will be skipped as it is empty
			if(leftNode != null && leftNode.getFullName().equals(SectionManager.ROOT_NODE_NAME) && targetNode == null) {
				if(!leftNode.leave(player)) {
					// The node refused the player to leave, stop processing.
					Log.severe("Section " + leftNode + " refused player " + player + " to leave.");
					return false;
				} else {
					// Remove the corresponding section as this player's current section
					playersCurrentSection.remove(player.getUniqueId());
				}
			}

			// Successively enter and leave sections along the path
			for(Section node : nodesToVisit) {
				// If we are not considering the first node, as the player is already in this node
				if(node != leftNode) {
					Log.finest("Player " + player + " joining node : " + node);

					if(!node.join(player)) {
						// The node refused the player to enter, stop processing.
						Log.severe("Section " + node + " refused player " + player + " to join.");
						// If this is the root node
						if(ROOT_NODE_NAME.equals(node.getFullName())) {
							// Kick the player
							player.kickPlayer("An internal error occurred.");
						}
						return false;
					}

					// Register the target section as current section for the player
					playersCurrentSection.put(player.getUniqueId(), node);
					Log.finest("Player " + player + " joined node : " + node);
				}
				// If we are not considering the last node, as the player should stay in it
				if(node != targetNode) {
					Log.finest("Player " + player + " leaving node : " + node);

					if(!node.leave(player)) {
						// The node refused the player to leave, stop processing.
						Log.severe("Section " + node + " refused player " + player + " to leave.");
						return false;
					}

					// Remove the corresponding section as this player's current section
					playersCurrentSection.remove(player.getUniqueId());
					Log.finest("Player " + player + " left node : " + node);
				}
			}
		} catch(Throwable e) {
			Log.severe("Error when moving player " + player + " along path from " + leftNode + " to " + targetNode + ".", e);
			return false;
		} finally {
			// Indicate that the player finished its enter process, remove temporary values
			inEnter.remove(player.getUniqueId());
			playersSectionPath.remove(player.getUniqueId());
		}

		// Make entering player party's players move if necessary
		// Do not do this when exiting root node
		if(targetNode != null && !targetNode.getCapabilities().contains(SectionCapabilities.PARTY_AWARE)) {
			Party party = this.partyManager.getParty(player.getUniqueId());

			// If player is in party
			if(party != null) {
				// If player is party owner
				if(party.getOwnerUniqueId() == player.getUniqueId()) {
					// Move all party players, except the owner, to the target section
					for(UUID partyPlayerUUID : party.getPlayers()) {
						if(partyPlayerUUID != party.getOwnerUniqueId()) {
							Log.fine("Moving party player " + partyPlayerUUID + " to " + targetNode);
							// Get party player
							Player partyPlayer = this.plugin.getServer().getPlayer(partyPlayerUUID);
							if(partyPlayer != null) {
								Log.fine("Moving party player " + partyPlayer + " to " + targetNode);
								// Move player to section
								enter(partyPlayer, targetNode, true);
							}
						}
					}
				}
			}
		}

		Log.finest("===== Movement ended for player " + player + " ==");

		return true;
	}

	public void register(Section section) throws NullPointerException {
		register(section, false);
	}

	public void register(Section section, boolean silent) throws NullPointerException {
		if(section == null)
			throw new NullPointerException();

		// If section is not already registered
		if(!sections.containsKey(section.getFullName())) {
			// Register section
			sections.put(section.getFullName(), section);

			if(!silent) {
				Log.info("SectionManager registered section " + section + ".");
			}
		} else {
			Log.warning("Section " + section + " tried to be registered for a second time.");
		}
	}

	public boolean register(Player player) {
		return enter(player, SectionManager.ROOT_NODE_NAME);
	}

	public void unregister(OfflinePlayer player) {
		Section section = getCurrentSection(player);

		playersCurrentSection.remove(player.getUniqueId());
		playersSectionPath.remove(player.getUniqueId());

		if(section != null) {
			if(section.preLeave(player, null, true)) {
				section.leave(player);
			} else {
				Log.severe("Could not make player " + player + " leave section " + section);
			}
		}
	}

	@Override
	public Section getCurrentSection(OfflinePlayer player) {
		return playersCurrentSection.get(player.getUniqueId());
	}

	@Override
	public Section getSection(String name) {
		return sections.get(name);
	}

	@Override
	public Iterable<Section> getSections() {
		return sections.values();
	}

	@Override
	public Section getRootSection() {
		return getSection(ROOT_NODE_NAME);
	}

	protected boolean canPlayerWalkAlongSectionPath(Player player, Section leftNode, Section targetNode, boolean fromParty) throws InvalidSectionException, NoParentSectionException {
		// If there is already a temporary path stored
		if(playersSectionPath.containsKey(player.getUniqueId())) {
			// Do not make the computation again
			Log.fine("Path already computed for " + player + ": " + playersSectionPath.get(player.getUniqueId()));
			return true;
		}
		Log.fine("Computing path for " + player +   " from " + leftNode + " to " + targetNode);

		// Special case for root node exit
		if(leftNode != null && leftNode.getFullName().equals(SectionManager.ROOT_NODE_NAME) && targetNode == null) {
			// Check preLeave for root node
			// Do a leave check for the parent
			if(!leftNode.preLeave(player, null, fromParty)) {
				// The current node does not want us to leave it. Stop going back.
				Log.severe("Node " + leftNode + " refused player " + player + " to leave with its leave check.");
				return false;
			}

			// Put an empty path
			playersSectionPath.put(player.getUniqueId(), Collections.emptyList());

			return true;
		}

		// If target node is null (and we are not in an root node exit case), then we are improperly using this method
		if(targetNode == null) {
			throw new InvalidSectionException("Target node is null in a case where it should not be. Left node is " + leftNode);
		}

		// If the target node is the same as the left node
		if(leftNode != null && leftNode.getFullName().equals(targetNode.getFullName())) {
			// The path is technically possible as no movement is needed and no checks need to be performed
			// Put an empty path as it is realistically what it is, while still allowing party processing to happen (for
			// example, for players in a different section)
			playersSectionPath.put(player.getUniqueId(), Collections.emptyList());
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
				Log.finest("For player " + player + ", common node is " + commonNode);

				// Walk back player to common node with target node
				if(!walkBackward(player, leftNode, commonNode, nodesToVisit, fromParty, targetNode)) {
					return false;
				}
			}

			// Walk front player to common node with target node
			if(!walkForward(player, commonNode, targetNode, nodesToVisit, fromParty, targetNode)) {
				return false;
			}
		} catch(Exception e) {
			Log.severe("Error encountered during section tree traversal.", e);
			return false;
		}

		// Store computed path
		playersSectionPath.put(player.getUniqueId(), nodesToVisit);

		return true;
	}

	protected boolean walkBackward(Player player, Section start, Section target, List<Section> visitedNodes, boolean fromParty, Section finalNode) throws NoParentSectionException {
		// Perform a leave check on the start node, as we consider the starting node of this function to be the node the
		// player is in
		if(!start.preLeave(player, finalNode, fromParty)) {
			// The current node does not want us to leave it. Stop going back.
			return false;
		}

		// Add the parent node to the list of visited nodes
		visitedNodes.add(start);

		// Walk up the tree, starting from the parent node (the start node is handled above)
		Section node;
		for(node = start.getParentSection(); node != null; node = node.getParentSection()) {
			if(!node.preJoin(player, finalNode, fromParty)) {
				// The current node does not want us to enter it. Stop going back.
				Log.severe("Node " + node + " refused player " + player + " to enter with its join check.");
				return false;
			}

			// Do a leave check for the parent
			if(!node.preLeave(player, finalNode, fromParty)) {
				// The current node does not want us to leave it. Stop going back.
				Log.severe("Node " + node + " refused player " + player + " to leave with its leave check.");
				return false;
			}

			// Add the parent node to the list of visited nodes
			visitedNodes.add(node);

			if(node.equals(target)) {
				// We attained the target node, stop going back.
				return true;
			}

			// Check if there is a parent node
			// Only the root node can have no parent, but we should not encounter the root node at this point.
			if(node.getParentSection() == null) {
				// If not, stop everything as the requested path is invalid
				throw new NoParentSectionException("Section " + node + " has no parent, cannot walk back from it when going from " + start + " to " + target);
			}
		}

		return true;
	}

	protected boolean walkForward(Player player, Section start, Section target, List<Section> visitedNodes, boolean fromParty, Section finalNode) throws InvalidSectionException {
		// Get the sections to walk to
		LinkedList<Section> sectionsToWalk = new LinkedList<>();
		// Go up from the target node until we hit the start node, remembering all nodes we passed through
		for(Section currentNode = target; currentNode != start; currentNode = currentNode.getParentSection()) {
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
				if(!node.preJoin(player, finalNode, fromParty)) {
					// The current node does not want us to enter it. Stop going back.
					Log.severe("Node " + node + " refused player " + player + " to enter with its join check.");
					return false;
				}
			}
			if(!node.preLeave(player, finalNode, fromParty)) {
				// The current node does not want us to leave it. Stop going forward.
				Log.severe("Node " + node + " refused player " + player + " to leave with its leave check.");
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

	protected Section findCommonNode(Section left, Section target) {
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

	private static class KeyTreeNode {
		public final String key;
		public final String fullKey;
		public String parentFullKey;
		public final Map<String, KeyTreeNode> children = new HashMap<>();

		public KeyTreeNode(String key, String fullKey) {
			this.key = key;
			this.fullKey = fullKey;
			List<String> splitFullKey = Section.getSplitName(fullKey);
			if(!splitFullKey.isEmpty()) {
				splitFullKey.remove(splitFullKey.size() - 1);
			}
			this.parentFullKey = String.join(".", splitFullKey);
			if(this.parentFullKey.equals(this.fullKey)) {
				this.parentFullKey = null;
			}
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

	/**
	 * Build the section tree.
	 * <p>
	 * Fills, for each node, its parent and children. Creates intermediary nodes whenever needed.
	 */
	public void buildSectionTree() {
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
				currentSection.addParent(getSection(currentKeyNode.parentFullKey));
				if(currentSection == currentSection.getParentSection()) {
					throw new RuntimeException("Unexpected situation : set node's parent as itself.");
				}
				// Go through all children keys, get the sections and add them as children to the section
				currentKeyNode.children.values()
						.stream()
						.map(node -> getSection(node.fullKey))
						.filter(Objects::nonNull)
						.forEach(currentSection::addChildren);
			}
		});
	}

	@Override
	public void ui(String sectionName, Player player) throws InvalidSectionException, InvalidUIException {
		// Get section
		Section section = getSection(sectionName);

		// If the section doesn't exist
		if(section == null) {
			// Stop everything and inform
			throw new InvalidSectionException("Invalid section name");
		}

		// If section has custom UI
		if(section.getCapabilities().contains(SectionCapabilities.CUSTOM_SELECTION_UI)) {
			SelectionUI ui = section.getSelectionUI();
			// If selection UI is null
			if(ui == null) {
				// Stop everything and inform
				throw new InvalidUIException("Section " + section + " reports to have custom UI, but it is null.");
			}
			// Open it
			ui.ui(section, player);
		} else {
			// Open default UI
			this.defaultSelectionUi.ui(section, player);
		}
	}
}
