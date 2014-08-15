package com.countrygamer.racesforminecraft.common.talent;

import com.countrygamer.cgo.common.lib.NameParser;
import com.countrygamer.racesforminecraft.api.talent.CasteTrait;
import com.countrygamer.racesforminecraft.api.talent.ICaste;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.HashSet;
import java.util.Iterator;

/**
 * Tracks biome and block related stats per RacePlayer
 *
 * @author CountryGamer
 */
public class Caste extends AbstractTalent implements ICaste {
	private final HashSet<CasteTrait> traits;
	private final HashSet<String> list;

	public Caste(String name, HashSet<CasteTrait> traits) {
		super(name);

		this.traits = traits;

		this.list = new HashSet<String>();
		Iterator<CasteTrait> iterator = traits.iterator();
		while (iterator.hasNext()) {
			this.list.add(iterator.next().objectName);
		}
	}

	@Override
	public HashSet<CasteTrait> getTraits() {
		return traits;
	}

	private int[] getXYZ(EntityPlayer player) {
		return new int[] {
				(int) Math.floor(player.posX), (int) Math.floor(player.posY - player.getYOffset()),
				(int) Math.floor(player.posZ)
		};
	}

	@Override
	public void runEffectsForItem(EntityPlayer player, ItemStack itemStack) {
		int[] xyz = this.getXYZ(player);
		if (NameParser.isInList(itemStack, this.list)) {
			for (CasteTrait trait : this.traits) {
				if (!trait.isBlock) {
					BiomeGenBase biome = BiomeGenBase.getBiome(trait.biomeID);
					if (player.worldObj.getBiomeGenForCoords(xyz[0], xyz[2]).equals(biome)) {
						player.addPotionEffect(new PotionEffect(trait.effect));
					}
				}
			}
		}
	}

	@Override
	public void runEffectsForBlock(EntityPlayer player) {
		int[] xyz = this.getXYZ(player);
		int y1 = xyz[1];
		Block block;
		do {
			y1 -= 1;
			block = player.worldObj.getBlock(xyz[0], y1, xyz[2]);
		} while (block == Blocks.air && y1 > 0);

		if (block != null && block != Blocks.air) {

			int distanceY = xyz[1] - y1;

			ItemStack blockStack = new ItemStack(block, 1,
					player.worldObj.getBlockMetadata(xyz[0], y1, xyz[2]));
			if (NameParser.isInList(blockStack, this.list)) {
				for (CasteTrait trait : this.traits) {
					if (trait.isBlock && distanceY <= trait.distanceY) {
						BiomeGenBase biome = BiomeGenBase.getBiome(trait.biomeID);
						if (player.worldObj.getBiomeGenForCoords(xyz[0], xyz[2]).equals(biome)) {
							player.addPotionEffect(new PotionEffect(trait.effect));
						}
					}
				}
			}
		}
	}

}
