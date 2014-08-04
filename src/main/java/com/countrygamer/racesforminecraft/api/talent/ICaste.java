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
public interface ICaste extends ITalent
{
    HashSet<CasteTrait> getTraits();

    void runEffectsForItem(EntityPlayer player, ItemStack itemStack);

    void runEffectsForBlock(EntityPlayer player);
}
