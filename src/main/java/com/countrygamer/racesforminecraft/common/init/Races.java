package com.countrygamer.racesforminecraft.common.init;

import com.countrygamer.racesforminecraft.common.talent.AbstractTalent;
import com.countrygamer.racesforminecraft.common.talent.Race;

/**
 * @author CountryGamer
 */
public class Races extends Talents {

	public static final Races INSTANCE = new Races();

	public Race getRaceFromName(String name) {
		AbstractTalent talent = super.getTalentFromName(name);
		if (talent != null) {
			return (Race)talent;
		}
		return null;
	}

	public Race getDefault() {
		return (Race)this.talents.get("default");
	}

}
