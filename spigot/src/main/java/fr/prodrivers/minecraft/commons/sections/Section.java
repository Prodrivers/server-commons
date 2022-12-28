package fr.prodrivers.minecraft.commons.sections;

import com.google.common.collect.Lists;
import fr.prodrivers.minecraft.commons.exceptions.InvalidSectionException;
import fr.prodrivers.minecraft.commons.ui.section.SelectionUI;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.*;

@SuppressWarnings({"CanBeFinal", "SameReturnValue"})
public abstract class Section {
	protected Set<OfflinePlayer> players = new HashSet<>();
	protected Map<String, Section> children = new HashMap<>();
	protected Section parent = null;

	protected String name;
	protected String fullName;
	protected List<String> splitFullName;
	protected List<String> parentsFullName;

	public Section(@NonNull String fullName) {
		this.fullName = fullName;
		this.splitFullName = getSplitName(fullName);
		this.parentsFullName = getIntermediateNodeNamesList(fullName);
		this.name = splitFullName.size() > 0 ? splitFullName.get(splitFullName.size() - 1) : "";
	}

	public @NonNull String getName() {
		return name;
	}

	public @NonNull String getFullName() {
		return fullName;
	}

	public @NonNull List<String> getSplitFullName() {
		return splitFullName;
	}

	public @NonNull List<String> getParentsFullName() {
		return parentsFullName;
	}

	public abstract @NonNull Set<SectionCapabilities> getCapabilities();

	public abstract boolean preJoin(@NonNull Player player, Section targetSection, boolean fromParty);

	public abstract boolean join(@NonNull Player player);

	public abstract boolean preLeave(@NonNull OfflinePlayer player, Section targetSection, boolean fromParty);

	public abstract boolean leave(@NonNull OfflinePlayer player);

	public @NonNull Collection<OfflinePlayer> getPlayers() {
		return players;
	}

	public boolean contains(@NonNull OfflinePlayer player) {
		return players.contains(player);
	}

	public @Nullable Section getParentSection() {
		return parent;
	}

	public @Nullable Section getFirstNonTransitiveSection() {
		Section nonTransitiveParent;
		for(nonTransitiveParent = this; nonTransitiveParent != null && nonTransitiveParent.getCapabilities().contains(SectionCapabilities.TRANSITIVE); ) {
			nonTransitiveParent = nonTransitiveParent.getParentSection();
		}

		return nonTransitiveParent;
	}

	public @NonNull Collection<Section> getChildSections() {
		return children.values();
	}

	public boolean contains(@NonNull Section child) {
		return children.containsKey(child.getFullName());
	}

	protected boolean add(@NonNull OfflinePlayer player) {
		return players.add(player);
	}

	protected boolean remove(@NonNull OfflinePlayer player) {
		return players.remove(player);
	}

	protected void addChildren(@NonNull Section section) {
		children.put(section.getFullName(), section);
	}

	protected void removeChildren(@NonNull Section section) {
		children.remove(section.getFullName());
	}

	protected void addParent(@NonNull Section section) {
		parent = section;
	}

	protected void removeParent() {
		parent = null;
	}

	static @NonNull List<String> getSplitName(String fullName) {
		// If name is empty, return empty array
		if(fullName.isEmpty()) {
			return new ArrayList<>();
		}
		return Lists.newArrayList(fullName.split("\\."));
	}

	static @NonNull List<String> getIntermediateNodeNamesList(String fullName) throws InvalidSectionException {
		return Lists.newArrayList(getIntermediateNodeNames(fullName));
	}

	static @NonNull String[] getIntermediateNodeNames(String sectionName) throws InvalidSectionException {
		// If name is empty, return empty array
		if(sectionName.isEmpty()) {
			return new String[]{};
		}

		// Get all individual subsection names
		String[] subsectionIndividualNames = sectionName.split("\\.");

		StringBuilder subsectionName = new StringBuilder();
		String[] intermediateSubsectionNames = new String[subsectionIndividualNames.length];

		// Go through all subsections and compile all intermediate subsection names
		for(int i = 0; i < subsectionIndividualNames.length; i++) {
			// If we are not at the first iteration
			if(subsectionName.length() > 0) {
				// subsectionName is already a valid subsection name, add a separator
				subsectionName.append(".");
			}
			subsectionName.append(subsectionIndividualNames[i]);
			intermediateSubsectionNames[i] = subsectionName.toString();
		}

		return intermediateSubsectionNames;
	}

	@Override
	public String toString() {
		return this.getClass().getName() + "{" +
				"fullName='" + fullName + '\'' +
				'}';
	}

	protected SelectionUI getSelectionUI() {
		return null;
	}
}
