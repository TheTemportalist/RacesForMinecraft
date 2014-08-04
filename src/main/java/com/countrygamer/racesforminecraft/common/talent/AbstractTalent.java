package com.countrygamer.racesforminecraft.common.talent;

import com.countrygamer.racesforminecraft.api.talent.ITalent;

/**
 * @author CountryGamer
 */
public class AbstractTalent implements ITalent
{
	private final String name;

	public AbstractTalent(String name) {
		this.name = name;
	}

    @Override
	public String getName() {
		return name;
	}
}
