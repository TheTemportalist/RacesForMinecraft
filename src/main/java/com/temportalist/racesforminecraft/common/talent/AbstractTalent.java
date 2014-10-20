package com.temportalist.racesforminecraft.common.talent;

import com.temportalist.racesforminecraft.api.talent.ITalent;

/**
 * @author TheTemportalist
 */
public class AbstractTalent implements ITalent {
	private final String name;

	public AbstractTalent(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}
}
