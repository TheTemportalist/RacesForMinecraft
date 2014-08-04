package com.countrygamer.racesforminecraft.common.talent;

import com.countrygamer.racesforminecraft.common.extended.RacePlayer;
import com.countrygamer.racesforminecraft.common.lib.NameParser;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.HashSet;

/**
 * Tracks the type of entity the RacePlayer is
 * Blacklists all items/blocks
 *
 * @author CountryGamer
 */
public class Race extends AbstractTalent {

	private final boolean isBlacklist;
	private final HashSet<String> usableList;
	public final HashSet<String> inheritSkills;
	public final HashSet<String> inheritCastes;

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

	public boolean canUseItem(EntityPlayer player, RacePlayer racePlayer, ItemStack itemStack) {
		boolean item_is_in_list = NameParser.isInList(itemStack, this.usableList);
		return this.isBlacklist ? !item_is_in_list : item_is_in_list;
	}

}
