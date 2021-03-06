package com.temportalist.racesforminecraft.common;

import com.temportalist.racesforminecraft.api.talent.ICaste;
import com.temportalist.racesforminecraft.api.talent.IRace;
import com.temportalist.racesforminecraft.api.talent.ISkill;
import com.temportalist.racesforminecraft.api.talent.ITalent;
import com.temportalist.racesforminecraft.common.extended.RacePlayer;
import com.temportalist.racesforminecraft.common.init.Castes;
import com.temportalist.racesforminecraft.common.init.Races;
import com.temportalist.racesforminecraft.common.init.Skills;
import net.minecraft.entity.player.EntityPlayer;

import java.util.HashSet;
import java.util.Set;

/**
 * Only expose interfaces to avoid putting all talent related things in the api!
 *
 * @author TheTemportalist
 * @author Dries007
 */
public class APIHelper {

	/**
	 * @see com.temportalist.racesforminecraft.api.API#register(com.temportalist.racesforminecraft.api.talent.ITalent)
	 */
	public static void register(ITalent talent) {
		if (talent instanceof IRace) {
			Races.INSTANCE.registerTalent(talent);
		}
		else if (talent instanceof ISkill) {
			Skills.INSTANCE.registerTalent(talent);
		}
		else if (talent instanceof ICaste) {
			Castes.INSTANCE.registerTalent(talent);
		}
		else
			throw new UnsupportedOperationException("Talent must be IRace, ISkill or ICaste.");
	}

	/**
	 * @see com.temportalist.racesforminecraft.api.API#getAllRaceNames()
	 */
	public static Set<String> getAllRaceNames() {
		return Races.INSTANCE.getAllNames();
	}

	/**
	 * @see com.temportalist.racesforminecraft.api.API#getAllSkillNames()
	 */
	public static Set<String> getAllSkillNames() {
		return Skills.INSTANCE.getAllNames();
	}

	/**
	 * @see com.temportalist.racesforminecraft.api.API#getAllCasteNames()
	 */
	public static Set<String> getAllCasteNames() {
		return Castes.INSTANCE.getAllNames();
	}

	/**
	 * @see com.temportalist.racesforminecraft.api.API#getRace(String)
	 */
	public IRace getRace(String name) {
		return Races.INSTANCE.getRaceFromName(name);
	}

	/**
	 * @see com.temportalist.racesforminecraft.api.API#getSkill(String)
	 */
	public ISkill getSkill(String name) {
		return Skills.INSTANCE.getSkillFromName(name);
	}

	/**
	 * @see com.temportalist.racesforminecraft.api.API#getCaste(String)
	 */
	public ICaste getCaste(String name) {
		return Castes.INSTANCE.getCasteFromName(name);
	}

	/**
	 * @see com.temportalist.racesforminecraft.api.API#getRace(net.minecraft.entity.player.EntityPlayer)
	 */
	public static IRace getRace(EntityPlayer player) {
		return RacePlayer.get(player).getRace();
	}

	/**
	 * @see com.temportalist.racesforminecraft.api.API#getSkills(net.minecraft.entity.player.EntityPlayer)
	 */
	public static HashSet<ISkill> getSkills(EntityPlayer player) {
		return RacePlayer.get(player).getSkills();
	}

	/**
	 * @see com.temportalist.racesforminecraft.api.API#getCastes(net.minecraft.entity.player.EntityPlayer)
	 */
	public static HashSet<ICaste> getCastes(EntityPlayer player) {
		return RacePlayer.get(player).getCastes();
	}

	/**
	 * @see com.temportalist.racesforminecraft.api.API#applyTalent(net.minecraft.entity.player.EntityPlayer, com.temportalist.racesforminecraft.api.talent.ITalent)
	 */
	public static void applyTalent(EntityPlayer player, ITalent talent) {
		RacePlayer racePlayer = RacePlayer.get(player);

		if (talent instanceof IRace) {
			racePlayer.setRace((IRace) talent);
		}
		else if (talent instanceof ISkill) {
			racePlayer.addSkill((ISkill) talent);
		}
		else if (talent instanceof ICaste) {
			racePlayer.addCaste((ICaste) talent);
		}
		else
			throw new UnsupportedOperationException("Talent must be IRace, ISkill or ICaste.");
	}

	/**
	 * @see com.temportalist.racesforminecraft.api.API#revokeTalent(net.minecraft.entity.player.EntityPlayer, com.temportalist.racesforminecraft.api.talent.ITalent)
	 */
	public static void revokeTalent(EntityPlayer player, ITalent talent) {
		RacePlayer racePlayer = RacePlayer.get(player);

		if (talent instanceof ISkill) {
			racePlayer.removeSkill((ISkill) talent);
		}
		else if (talent instanceof ICaste) {
			racePlayer.removeCaste((ICaste) talent);
		}
		else
			throw new UnsupportedOperationException("Talent must be ISkill or ICaste.");
	}
}
