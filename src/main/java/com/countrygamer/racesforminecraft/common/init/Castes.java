package com.countrygamer.racesforminecraft.common.init;

import com.countrygamer.racesforminecraft.common.talent.AbstractTalent;
import com.countrygamer.racesforminecraft.common.talent.Caste;

/**
 * @author CountryGamer
 */
public class Castes extends Talents {

	public static final Castes INSTANCE = new Castes();

	public Caste getCasteFromName(String name) {
		AbstractTalent talent = super.getTalentFromName(name);
		if (talent != null) {
			return (Caste)talent;
		}
		return null;
	}

}
