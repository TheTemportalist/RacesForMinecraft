package com.countrygamer.racesforminecraft.common.lib;

import net.minecraft.potion.PotionEffect;

/**
 * @author CountryGamer
 */
public class CasteTrait {

	public final int biomeID;
	public final String blockName;
	public final int distanceY;
	public final PotionEffect effect;

	public CasteTrait(int biomeID, String blockName, int distanceY, PotionEffect effect) {
		this.biomeID = biomeID;
		this.blockName = blockName;
		this.distanceY = distanceY;
		this.effect = effect;
	}

}
