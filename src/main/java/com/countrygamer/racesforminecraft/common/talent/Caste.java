package com.countrygamer.racesforminecraft.common.talent;

import com.countrygamer.racesforminecraft.common.extended.RacePlayer;
import com.countrygamer.racesforminecraft.common.lib.CasteTrait;
import com.countrygamer.racesforminecraft.common.lib.NameParser;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.HashSet;
import java.util.Iterator;

/**
 * Tracks biome and block related stats per RacePlayer
 *
 * @author CountryGamer
 */
public class Caste extends AbstractTalent {

	private HashSet<CasteTrait> traits = null;
	private HashSet<String> list = null;

	public Caste(String name, HashSet<CasteTrait> traits) {
		super(name);

		this.traits = traits;

		this.list = new HashSet<String>();
		Iterator<CasteTrait> iterator = traits.iterator();
		while (iterator.hasNext()) {
			this.list.add(iterator.next().blockName);
		}

	}

	public void runEffectsForBlock(EntityPlayer player, RacePlayer racePlayer) {
		int x = MathHelper.floor_double(player.posX);
		int y = MathHelper.floor_double(player.posY);
		int y1 = y;
		int z = MathHelper.floor_double(player.posZ);

		Block block;
		do {
			y1 -= 1;
			block = player.worldObj.getBlock(x, y1, z);
		} while (block == Blocks.air && y1 > 0);

		if (block != null && block != Blocks.air) {

			int distanceY = y - y1;

			ItemStack blockStack = new ItemStack(block, 1,
					player.worldObj.getBlockMetadata(x, y1, z));
			if (NameParser.isInList(blockStack, this.list)) {
				for (CasteTrait trait : this.traits) {
					if (distanceY <= trait.distanceY) {
						BiomeGenBase biome = BiomeGenBase.getBiome(trait.biomeID);
						if (player.worldObj.getBiomeGenForCoords(x, z).equals(biome)) {
							player.addPotionEffect(new PotionEffect(trait.effect));
						}
					}
				}
			}
		}

		/*
		if (this.map != null) {
			ItemStack blockStack = new ItemStack(block, 1, meta);
			if (NameParser.isInList(blockStack, this.list)) {
				HashMap<PotionEffect, Integer> effects = this.map
						.get(NameParser.getName(blockStack));
				Iterator<PotionEffect> iterator = effects.keySet().iterator();
				while (iterator.hasNext()) {
					PotionEffect potionEffect = iterator.next();
					if (distanceY <= effects.get(potionEffect)) {
						player.addPotionEffect(new PotionEffect(potionEffect));
					}
				}
			}
		}
		*/
	}

}
