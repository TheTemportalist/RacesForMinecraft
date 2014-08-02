package com.countrygamer.racesforminecraft.common.talent;

import com.countrygamer.racesforminecraft.common.extended.RacePlayer;
import com.countrygamer.racesforminecraft.common.lib.NameParser;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Tracks biome and block related stats per RacePlayer
 *
 * @author CountryGamer
 */
public class Caste extends AbstractTalent {

	private HashMap<String, HashMap<PotionEffect, Integer>> map = null;
	private HashSet<String> list = null;

	public Caste(String name, HashMap<String, HashMap<PotionEffect, Integer>> map) {
		super(name);
		this.map = map;
		this.list = new HashSet<String>();
		Iterator<String> iterator = this.map.keySet().iterator();
		while (iterator.hasNext()) {
			this.list.add(iterator.next());
		}
	}

	public void runEffectsForBlock(EntityPlayer player, RacePlayer racePlayer, Block block,
			int meta, int distanceY) {
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
	}

}
