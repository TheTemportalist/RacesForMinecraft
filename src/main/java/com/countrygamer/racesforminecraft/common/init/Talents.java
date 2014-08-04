package com.countrygamer.racesforminecraft.common.init;

import com.countrygamer.racesforminecraft.api.talent.ITalent;

import java.util.HashMap;
import java.util.Set;

/**
 * @author CountryGamer
 */
public abstract class Talents {

	protected HashMap<String, ITalent> talents = new HashMap<String, ITalent>();

    public void registerTalent(ITalent talent)
    {
        if (talents.containsKey(talent.getName())) throw new IllegalArgumentException("Name already present!");
        if (this.talents.isEmpty())
        {
            this.talents.put("default", talent);
        }
        this.talents.put(talent.getName(), talent);
    }

    public Set<String> getAllNames()
    {
        return talents.keySet();
    }

    public ITalent getTalentFromName(String name)
    {
        if (this.talents.containsKey(name))
        {
            return this.talents.get(name);
        }
		return null;
	}

}
