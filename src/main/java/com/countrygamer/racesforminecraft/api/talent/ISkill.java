package com.countrygamer.racesforminecraft.api.talent;

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
public interface ISkill extends ITalent
{
    public HashSet<String> getBlacklist();

    public HashSet<String> getWhitelist();

    boolean hasItem(ItemStack itemStack);

    public boolean canUseItem(EntityPlayer player, ItemStack itemStack);
}
