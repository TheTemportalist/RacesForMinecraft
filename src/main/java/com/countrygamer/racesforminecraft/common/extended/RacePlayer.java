package com.countrygamer.racesforminecraft.common.extended;

import com.countrygamer.cgo.wrapper.common.extended.ExtendedEntity;
import com.countrygamer.cgo.wrapper.common.extended.ExtendedEntityHandler;
import com.countrygamer.racesforminecraft.api.talent.ICaste;
import com.countrygamer.racesforminecraft.api.talent.IRace;
import com.countrygamer.racesforminecraft.api.talent.ISkill;
import com.countrygamer.racesforminecraft.common.init.Castes;
import com.countrygamer.racesforminecraft.common.init.Races;
import com.countrygamer.racesforminecraft.common.init.Skills;
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

	private IRace race = null;
	private HashSet<ISkill> skills = new HashSet<ISkill>();
	private HashSet<ICaste> castes = new HashSet<ICaste>();

    public RacePlayer(EntityPlayer player)
    {
        super(player);

    }

    @Override
    public void saveNBTData(NBTTagCompound tagCom)
    {

        tagCom.setBoolean("hasLoggedIn", this.hasLoggedInBefore);

        if (this.race != null)
        {
            tagCom.setString("race", this.race.getName());
        }

        NBTTagList skillList = new NBTTagList();
        for (ISkill skill : this.skills)
        {
            NBTTagCompound skillTag = new NBTTagCompound();
            skillTag.setString("name", skill.getName());
            skillList.appendTag(skillTag);
        }
        tagCom.setTag("skills", skillList);

        NBTTagList casteList = new NBTTagList();
        for (ICaste caste : this.castes)
        {
            NBTTagCompound casteTag = new NBTTagCompound();
            casteTag.setString("name", caste.getName());
            casteList.appendTag(casteTag);
        }
        tagCom.setTag("castes", casteList);

    }

    @Override
    public void loadNBTData(NBTTagCompound tagCom)
    {

        this.hasLoggedInBefore = tagCom.getBoolean("hasLoggedIn");

        this.race = null;
        if (tagCom.hasKey("race"))
        {
            this.race = Races.INSTANCE.getRaceFromName(tagCom.getString("race"));
        }
        else
        {
            this.race = Races.INSTANCE.getDefault();
        }

        this.skills = new HashSet<ISkill>();
        NBTTagList skillList = tagCom.getTagList("skills", 10);
        for (int i = 0; i < skillList.tagCount(); i++)
        {
            NBTTagCompound skillTag = skillList.getCompoundTagAt(i);
            ISkill skill = Skills.INSTANCE.getSkillFromName(skillTag.getString("name"));
            if (skill != null) this.skills.add(skill);
        }

        this.castes = new HashSet<ICaste>();
		NBTTagList casteList = tagCom.getTagList("castes", 10);
		for (int i = 0; i < casteList.tagCount(); i++) {
			NBTTagCompound casteTag = casteList.getCompoundTagAt(i);
			ICaste caste = Castes.INSTANCE.getCasteFromName(casteTag.getString("name"));
			if (caste != null)
				this.castes.add(caste);
		}

	}

	public boolean canUseItem(ItemStack itemStack) {
		for (ISkill skill : this.skills) {
			if (skill.hasItem(itemStack)) {
				return skill.canUseItem(this.player(), itemStack);
			}
		}
		if (this.race != null) {
			return this.race.canUseItem(this.player(), itemStack);
		}
		// If no race, then make sure player cannot do anything >:D
		return false;
	}

	public void applyEffectsForBlock() {
		for (ICaste caste : this.castes) {
			caste.runEffectsForBlock(this.player());
		}
	}

	public void applyEffectsForItem(ItemStack heldStack) {
		for (ICaste caste : this.castes) {
			caste.runEffectsForItem(this.player(), heldStack);
		}
	}

	public IRace getRace() {
		return this.race;
	}

	public HashSet<ISkill> getSkills() {
		return this.skills;
	}

	public HashSet<ICaste> getCastes() {
		return this.castes;
	}

	public boolean hasSkill(ISkill skill) {
		return this.skills.contains(skill);
	}

	public boolean hasCaste(ICaste caste) {
		return this.castes.contains(caste);
	}

	public void setRace(IRace race) {
		this.race = race;
		for (String inheritSkill : race.getInheritSkills()) {
            ISkill skill = Skills.INSTANCE.getSkillFromName(inheritSkill);
			if (!this.skills.contains(skill)) {
				this.skills.add(skill);
			}
		}
		for (String inheritCaste : race.getInheritCastes()) {
			ICaste caste = Castes.INSTANCE.getCasteFromName(inheritCaste);
			if (!this.castes.contains(caste)) {
				this.castes.add(caste);
			}
		}
		this.syncEntity();
	}

	public boolean addSkill(ISkill skill) {
		if (!this.skills.contains(skill)) {
			this.skills.add(skill);
			this.syncEntity();
			return true;
		}
		return false;
	}

	public boolean addCaste(ICaste caste) {
		if (!this.castes.contains(caste)) {
			this.castes.add(caste);
			this.syncEntity();
			return true;
		}
		return false;
	}

	public boolean removeSkill(ISkill skill) {
		if (this.skills.contains(skill)) {
			this.skills.remove(skill);
			this.syncEntity();
			return true;
		}
		return false;
	}

	public boolean removeCaste(ICaste caste) {
		if (this.castes.contains(caste)) {
			this.castes.remove(caste);
			this.syncEntity();
			return true;
		}
		return false;
	}

}
