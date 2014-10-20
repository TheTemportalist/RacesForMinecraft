package com.temportalist.racesforminecraft.common.init;

import com.temportalist.racesforminecraft.api.talent.ISkill;
import com.temportalist.racesforminecraft.api.talent.ITalent;

/**
 * @author TheTemportalist
 */
public class Skills extends Talents {

	public static final Skills INSTANCE = new Skills();

	public ISkill getSkillFromName(String name) {
		ITalent talent = super.getTalentFromName(name);
		if (talent != null) {
			return (ISkill) talent;
		}
		return null;
	}

}
