package com.temportalist.racesforminecraft.api.talent;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.HashSet;

/**
 * Abstraction layer for soft dependencies.
 * All sets are mutable.
 *
 * @author Dries007
 */
@SuppressWarnings("UnusedDeclaration")
public interface IRace extends ITalent {
	boolean canUseItem(EntityPlayer player, ItemStack itemStack);

	public boolean isBlacklist();

	public HashSet<String> getUsableList();

	public HashSet<String> getInheritSkills();

	public HashSet<String> getInheritCastes();
}
