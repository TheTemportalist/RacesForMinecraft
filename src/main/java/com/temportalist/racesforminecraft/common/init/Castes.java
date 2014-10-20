package com.temportalist.racesforminecraft.common.init;

import com.temportalist.racesforminecraft.api.talent.ICaste;
import com.temportalist.racesforminecraft.api.talent.ITalent;

/**
 * @author TheTemportalist
 */
public class Castes extends Talents {

	public static final Castes INSTANCE = new Castes();

	public ICaste getCasteFromName(String name) {
		ITalent talent = super.getTalentFromName(name);
		if (talent != null) {
			return (ICaste) talent;
		}
		return null;
	}

}
