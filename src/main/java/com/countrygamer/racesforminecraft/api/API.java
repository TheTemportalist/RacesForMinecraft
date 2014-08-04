package com.countrygamer.racesforminecraft.api;

import com.countrygamer.racesforminecraft.common.talent.AbstractTalent;
import com.countrygamer.racesforminecraft.common.talent.Caste;
import com.countrygamer.racesforminecraft.common.talent.Race;
import com.countrygamer.racesforminecraft.common.talent.Skill;
import net.minecraft.entity.player.EntityPlayer;

import java.util.HashSet;

/**
 * @author CountryGamer
 */
public class API {

	public static Race getRace(EntityPlayer player) {
		return null;
	}

	public static HashSet<Skill> getSkills(EntityPlayer player) {
		return null;
	}

	public static HashSet<Caste> getCastes(EntityPlayer player) {
		return null;
	}

	public static void applyTalent(EntityPlayer player, AbstractTalent talent) {

	}

	public static void applyTalent(EntityPlayer player, String talentName) {

	}

	public static void revokeTalent(EntityPlayer player, AbstractTalent talent) {

	}

	public static void revokeTalent(EntityPlayer player, String talentName) {

	}

}
