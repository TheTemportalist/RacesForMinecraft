package com.countrygamer.racesforminecraft;

import com.countrygamer.racesforminecraft.common.extended.RacePlayer;
import com.countrygamer.racesforminecraft.common.talent.AbstractTalent;
import com.countrygamer.racesforminecraft.common.talent.Caste;
import com.countrygamer.racesforminecraft.common.talent.Race;
import com.countrygamer.racesforminecraft.common.talent.Skill;
import net.minecraft.entity.player.EntityPlayer;

import java.util.HashSet;

/**
 * TODO This is a horrible api..... Needs to be changed - CG
 *
 * @author CountryGamer
 */
@Deprecated
public class RfMAPI {

	public static Race getRace(EntityPlayer player) {
		return RacePlayer.get(player).getRace();
	}

	public static HashSet<Skill> getSkills(EntityPlayer player) {
		return RacePlayer.get(player).getSkills();
	}

	public static HashSet<Caste> getCastes(EntityPlayer player) {
		return RacePlayer.get(player).getCastes();
	}

	public static boolean applyTalent(EntityPlayer player, AbstractTalent talent) {

		RacePlayer racePlayer = RacePlayer.get(player);

		if (talent instanceof Race) {
			racePlayer.setRace((Race) talent);
			return true;
		}
		else if (talent instanceof Skill) {
			return racePlayer.addSkill((Skill) talent);
		}
		else if (talent instanceof Caste) {
			return racePlayer.addCaste((Caste) talent);
		}

		return false;

	}

	public static boolean revokeTalent(EntityPlayer player, AbstractTalent talent) {

		RacePlayer racePlayer = RacePlayer.get(player);

		if (talent instanceof Skill) {
			return racePlayer.removeSkill((Skill) talent);
		}
		else if (talent instanceof Caste) {
			return racePlayer.removeCaste((Caste) talent);
		}

		return false;

	}

}
