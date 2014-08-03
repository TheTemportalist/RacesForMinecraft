package com.countrygamer.racesforminecraft.common.extended;

import com.countrygamer.cgo.wrapper.common.extended.ExtendedEntity;
import com.countrygamer.cgo.wrapper.common.extended.ExtendedEntityHandler;
import com.countrygamer.racesforminecraft.common.init.Castes;
import com.countrygamer.racesforminecraft.common.init.Races;
import com.countrygamer.racesforminecraft.common.init.Skills;
import com.countrygamer.racesforminecraft.common.talent.Caste;
import com.countrygamer.racesforminecraft.common.talent.Race;
import com.countrygamer.racesforminecraft.common.talent.Skill;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.IExtendedEntityProperties;

import java.util.HashSet;

/**
 * @author CountryGamer
 */
public class RacePlayer extends ExtendedEntity {

	public static RacePlayer get(EntityPlayer player) {
		IExtendedEntityProperties entityProperties = ExtendedEntityHandler
				.getExtended(player, RacePlayer.class);
		if (entityProperties != null) {
			return (RacePlayer) entityProperties;
		}
		else {
			return null;
		}
	}

	private boolean hasLoggedInBefore = false;

	private Race race = null;
	private HashSet<Skill> skills = new HashSet<Skill>();
	private HashSet<Caste> castes = new HashSet<Caste>();

	public RacePlayer(EntityPlayer player) {
		super(player);

	}

	@Override
	public void saveNBTData(NBTTagCompound tagCom) {

		tagCom.setBoolean("hasLoggedIn", this.hasLoggedInBefore);

		if (this.race != null) {
			tagCom.setString("race", this.race.getName());
		}

		NBTTagList skillList = new NBTTagList();
		for (Skill skill : this.skills) {
			NBTTagCompound skillTag = new NBTTagCompound();
			skillTag.setString("name", skill.getName());
			skillList.appendTag(skillTag);
		}
		tagCom.setTag("skills", skillList);

		NBTTagList casteList = new NBTTagList();
		for (Caste caste : this.castes) {
			NBTTagCompound casteTag = new NBTTagCompound();
			casteTag.setString("name", caste.getName());
			casteList.appendTag(casteTag);
		}
		tagCom.setTag("castes", casteList);

	}

	@Override
	public void loadNBTData(NBTTagCompound tagCom) {

		this.hasLoggedInBefore = tagCom.getBoolean("hasLoggedIn");

		this.race = null;
		if (tagCom.hasKey("race")) {
			this.race = Races.INSTANCE.getRaceFromName(tagCom.getString("race"));
		}
		else {
			this.race = Races.INSTANCE.getDefault();
		}

		this.skills = new HashSet<Skill>();
		NBTTagList skillList = tagCom.getTagList("skills", 10);
		for (int i = 0; i < skillList.tagCount(); i++) {
			NBTTagCompound skillTag = skillList.getCompoundTagAt(i);
			Skill skill = Skills.INSTANCE.getSkillFromName(skillTag.getString("name"));
			if (skill != null)
				this.skills.add(skill);
		}

		this.castes = new HashSet<Caste>();
		NBTTagList casteList = tagCom.getTagList("castes", 10);
		for (int i = 0; i < casteList.tagCount(); i++) {
			NBTTagCompound casteTag = casteList.getCompoundTagAt(i);
			Caste caste = Castes.INSTANCE.getCasteFromName(casteTag.getString("name"));
			if (caste != null)
				this.castes.add(caste);
		}

	}

	public boolean canUseItem(ItemStack itemStack) {
		for (Skill skill : this.skills) {
			if (skill.hasItem(itemStack)) {
				return skill.canUseItem(this.player(), this, itemStack);
			}
		}
		if (this.race != null) {
			return this.race.canUseItem(this.player(), this, itemStack);
		}
		// If no race, then make sure player cannot do anything >:D
		return false;
	}

	public void applyEffects() {
		for (Caste caste : this.castes) {
			caste.runEffectsForBlock(this.player(), this);
		}
	}

	public Race getRace() {
		return this.race;
	}

	public HashSet<Skill> getSkills() {
		return this.skills;
	}

	public HashSet<Caste> getCastes() {
		return this.castes;
	}

	public boolean hasSkill(Skill skill) {
		return this.skills.contains(skill);
	}

	public boolean hasCaste(Caste caste) {
		return this.castes.contains(caste);
	}

	public void setRace(Race race) {
		this.race = race;
		this.syncEntity();
	}

	public boolean addSkill(Skill skill) {
		if (!this.skills.contains(skill)) {
			this.skills.add(skill);
			this.syncEntity();
			return true;
		}
		return false;
	}

	public boolean addCaste(Caste caste) {
		if (!this.castes.contains(caste)) {
			this.castes.add(caste);
			this.syncEntity();
			return true;
		}
		return false;
	}

	public boolean removeSkill(Skill skill) {
		if (this.skills.contains(skill)) {
			this.skills.remove(skill);
			this.syncEntity();
			return true;
		}
		return false;
	}

	public boolean removeCaste(Caste caste) {
		if (this.castes.contains(caste)) {
			this.castes.remove(caste);
			this.syncEntity();
			return true;
		}
		return false;
	}

}
