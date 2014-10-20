package com.temportalist.racesforminecraft.common.init;

import com.temportalist.racesforminecraft.api.talent.IRace;
import com.temportalist.racesforminecraft.api.talent.ITalent;

/**
 * @author TheTemportalist
 */
public class Races extends Talents {

	public static final Races INSTANCE = new Races();

	public IRace getRaceFromName(String name) {
		ITalent talent = super.getTalentFromName(name);
		if (talent != null) {
			return (IRace) talent;
		}
		return null;
	}

	public IRace getDefault() {
		return (IRace) this.talents.get("default");
	}
}
