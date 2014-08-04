package com.countrygamer.racesforminecraft.common.init;

import com.countrygamer.racesforminecraft.api.talent.ISkill;
import com.countrygamer.racesforminecraft.api.talent.ITalent;

/**
 * @author CountryGamer
 */
public class Skills extends Talents {

	public static final Skills INSTANCE = new Skills();

	public ISkill getSkillFromName(String name) {
		ITalent talent = super.getTalentFromName(name);
		if (talent != null) {
			return (ISkill)talent;
		}
		return null;
	}

}
