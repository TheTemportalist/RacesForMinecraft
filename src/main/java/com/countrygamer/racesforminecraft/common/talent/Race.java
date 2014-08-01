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

	private boolean isBlacklist = false;
	private HashSet<String> list = null;

	/**
	 * Expecting variables for this race
	 *
	 * @param isBlacklist Whether this race is using white or black list
	 * @param list        A list of names (item or block names)
	 *                    EX: "modid:itemname", "modid:blockname",
	 *                    EX: "modid:itemname:metadata"
	 */
	public Race(String name, boolean isBlacklist, HashSet<String> list) {
		super(name);
		this.isBlacklist = isBlacklist;
		this.list = list;

	}

	public boolean canUseItem(EntityPlayer player, RacePlayer racePlayer, ItemStack itemStack) {
		boolean item_is_in_list = NameParser.isInList(itemStack, this.list);
		return this.isBlacklist ? !item_is_in_list : item_is_in_list;
	}

}
