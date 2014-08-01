package com.countrygamer.racesforminecraft.common.talent;

import com.countrygamer.racesforminecraft.common.extended.RacePlayer;
import com.countrygamer.racesforminecraft.common.lib.NameParser;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.HashSet;

/**
 * Tracks what items/blocks the RacePlayer CAN use (whitelist)
 *
 * @author CountryGamer
 */
public class Skill extends AbstractTalent {

	private HashSet<String> blackList = null;
	private HashSet<String> whiteList = null;

	public Skill(String name) {
		super(name);

	}
	/**
	 * Sets the respective list. Overwrites the corresponding list.
	 *
	 * @param isBlackList What type of list
	 * @param list        The list of names
	 */
	public void addList(boolean isBlackList, HashSet<String> list) {
		if (isBlackList) {
			this.blackList = list;
		}
		else {
			this.whiteList = list;
		}
	}

	public boolean hasItem(ItemStack itemStack) {
		return this.hasItem(itemStack, true) || this.hasItem(itemStack, false);
	}

	private boolean hasItem(ItemStack itemStack, Boolean checkBlackList) {
		if (checkBlackList) {
			if (this.blackList != null && NameParser.isInList(itemStack, this.blackList)) {
				return true;
			}
		}
		else {
			if (this.whiteList != null && NameParser.isInList(itemStack, this.whiteList)) {
				return true;
			}
		}
		return false;
	}

	public boolean canUseItem(EntityPlayer player, RacePlayer racePlayer, ItemStack itemStack) {
		if (this.hasItem(itemStack, true)) {
			return true;
		}
		return false;
	}

}
