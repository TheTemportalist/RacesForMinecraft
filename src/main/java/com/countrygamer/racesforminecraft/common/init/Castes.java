package com.countrygamer.racesforminecraft.common.init;

import com.countrygamer.racesforminecraft.api.talent.ICaste;
import com.countrygamer.racesforminecraft.api.talent.ITalent;

/**
 * @author CountryGamer
 */
public class Castes extends Talents {

	public static final Castes INSTANCE = new Castes();

	public ICaste getCasteFromName(String name) {
        ITalent talent = super.getTalentFromName(name);
		if (talent != null) {
			return (ICaste)talent;
		}
		return null;
	}

}
