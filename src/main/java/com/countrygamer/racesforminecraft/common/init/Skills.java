package com.countrygamer.racesforminecraft.common.init;

import com.countrygamer.racesforminecraft.common.talent.AbstractTalent;
import com.countrygamer.racesforminecraft.common.talent.Skill;

/**
 * @author CountryGamer
 */
public class Skills extends Talents {

	public static final Skills INSTANCE = new Skills();

	public Skill getSkillFromName(String name) {
		AbstractTalent talent = super.getTalentFromName(name);
		if (talent != null) {
			return (Skill)talent;
		}
		return null;
	}

}
