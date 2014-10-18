package com.countrygamer.racesforminecraft.common.talent;

import com.countrygamer.cgo.library.common.lib.NameParser;
import com.countrygamer.racesforminecraft.api.talent.IRace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.HashSet;

/**
 * Tracks the type of entity the RacePlayer is
 * Blacklists all items/blocks
 *
 * @author CountryGamer
 */
public class Race extends AbstractTalent implements IRace {
	private final boolean isBlacklist;
	private final HashSet<String> usableList;
	private final HashSet<String> inheritSkills;
	private final HashSet<String> inheritCastes;

	/**
	 * Expecting variables for this race
	 *
	 * @param isBlacklist   Whether this race is using white or black usableList
	 * @param usableList    A usableList of names (item or block names)
	 *                      EX: "modid:itemname", "modid:blockname",
	 *                      EX: "modid:itemname:metadata"
	 * @param inheritSkills
	 * @param inheritCastes
	 */
	public Race(String name, boolean isBlacklist, HashSet<String> usableList,
			HashSet<String> inheritSkills, HashSet<String> inheritCastes) {
		super(name);
		this.isBlacklist = isBlacklist;
		this.usableList = usableList;
		this.inheritSkills = inheritSkills;
		this.inheritCastes = inheritCastes;

	}

	@Override
	public boolean canUseItem(EntityPlayer player, ItemStack itemStack) {
		boolean item_is_in_list = NameParser.isInCollection(itemStack, this.usableList);
		return this.isBlacklist ? !item_is_in_list : item_is_in_list;
	}

	@Override
	public boolean isBlacklist() {
		return isBlacklist;
	}

	@Override
	public HashSet<String> getUsableList() {
		return usableList;
	}

	@Override
	public HashSet<String> getInheritSkills() {
		return inheritSkills;
	}

	@Override
	public HashSet<String> getInheritCastes() {
		return inheritCastes;
	}
}
