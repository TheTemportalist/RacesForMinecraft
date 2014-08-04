package com.countrygamer.racesforminecraft.common.lib;

import net.minecraft.potion.PotionEffect;

/**
 * @author CountryGamer
 */
public class CasteTrait {

	public final int biomeID;
	public final String objectName;
	public final boolean isBlock;
	public final int distanceY;
	public final PotionEffect effect;

	public CasteTrait(int biomeID, String itemName, PotionEffect effect) {
		this.biomeID = biomeID;
		this.objectName = itemName;
		this.isBlock = false;
		this.distanceY = 0;
		this.effect = effect;
	}

	public CasteTrait(int biomeID, String blockName, int distanceY, PotionEffect effect) {
		this.biomeID = biomeID;
		this.objectName = blockName;
		this.isBlock = true;
		this.distanceY = distanceY;
		this.effect = effect;
	}

}
