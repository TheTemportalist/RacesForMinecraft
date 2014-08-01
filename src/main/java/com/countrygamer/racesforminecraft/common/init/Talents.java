package com.countrygamer.racesforminecraft.common.init;

import com.countrygamer.racesforminecraft.common.talent.AbstractTalent;

import java.util.HashMap;

/**
 * @author CountryGamer
 */
public abstract class Talents {

	protected HashMap<String, AbstractTalent> talents = new HashMap<String, AbstractTalent>();

	public void registerTalent(AbstractTalent talent) {
		if (this.talents.isEmpty()) {
			this.talents.put("default", talent);
		}
		this.talents.put(talent.getName(), talent);
	}

	public AbstractTalent getTalentFromName(String name) {
		if (this.talents.containsKey(name)) {
			return this.talents.get(name);
		}
		return null;
	}

}
