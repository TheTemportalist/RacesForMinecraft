package com.temportalist.racesforminecraft.api;

import com.temportalist.racesforminecraft.api.talent.ICaste;
import com.temportalist.racesforminecraft.api.talent.IRace;
import com.temportalist.racesforminecraft.api.talent.ISkill;
import com.temportalist.racesforminecraft.api.talent.ITalent;
import cpw.mods.fml.common.Loader;
import net.minecraft.entity.player.EntityPlayer;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

/**
 * Use this class when interfacing with Races For Minecraft.
 * Abstraction layer provided.
 *
 * @author TheTemportalist
 * @author Dries007
 * @see com.temportalist.racesforminecraft.common.APIHelper
 */
@SuppressWarnings({ "unchecked", "UnusedDeclaration" })
public class API {
	private static final String pluginID = "racesforminecraft";
	private static final String APIHelperClass = "com.countrygamer.racesforminecraft.common.APIHelper";

	/**
	 * Check too see if Races For Minecraft if loaded
	 *
	 * @see cpw.mods.fml.common.Loader#isModLoaded(String)
	 */
	public static boolean isModLoaded() {
		return Loader.isModLoaded(pluginID);
	}

	/**
	 * Talent must be IRace, ISkill or ICaste
	 * Name must be unique!
	 *
	 * @see com.temportalist.racesforminecraft.common.APIHelper#register(com.temportalist.racesforminecraft.api.talent.ITalent)
	 */
	public static void register(ITalent talent) {
		if (isModLoaded()) {
			try {
				Class.forName(APIHelperClass).getMethod("register", ITalent.class).invoke(null);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Can return null
	 *
	 * @see com.temportalist.racesforminecraft.common.APIHelper#getAllRaceNames()
	 */
	public static Set<String> getAllRaceNames() {
		if (isModLoaded()) {
			try {
				return (Set<String>) Class.forName(APIHelperClass).getMethod("getAllRaceNames")
						.invoke(null);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * Can return null
	 *
	 * @see com.temportalist.racesforminecraft.common.APIHelper#getAllSkillNames()
	 */
	public static Set<String> getAllSkillNames() {
		if (isModLoaded()) {
			try {
				return (Set<String>) Class.forName(APIHelperClass).getMethod("getAllSkillNames")
						.invoke(null);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * Can return null
	 *
	 * @see com.temportalist.racesforminecraft.common.APIHelper#getAllCasteNames()
	 */
	public static Set<String> getAllCasteNames() {
		if (isModLoaded()) {
			try {
				return (Set<String>) Class.forName(APIHelperClass).getMethod("getAllCasteNames")
						.invoke(null);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * Can return null
	 *
	 * @see com.temportalist.racesforminecraft.common.APIHelper#getRace(String)
	 */
	public IRace getRace(String name) {
		if (isModLoaded()) {
			try {
				return (IRace) Class.forName(APIHelperClass).getMethod("getRace", String.class)
						.invoke(null, name);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * Can return null
	 *
	 * @see com.temportalist.racesforminecraft.common.APIHelper#getSkill(String)
	 */
	public ISkill getSkill(String name) {
		if (isModLoaded()) {
			try {
				return (ISkill) Class.forName(APIHelperClass).getMethod("getSkill", String.class)
						.invoke(null, name);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * Can return null
	 *
	 * @see com.temportalist.racesforminecraft.common.APIHelper#getCaste(String)
	 */
	public ICaste getCaste(String name) {
		if (isModLoaded()) {
			try {
				return (ICaste) Class.forName(APIHelperClass).getMethod("getCaste", String.class)
						.invoke(null, name);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * Can return null
	 *
	 * @see com.temportalist.racesforminecraft.common.APIHelper#getRace(net.minecraft.entity.player.EntityPlayer)
	 */
	public static IRace getRace(EntityPlayer player) {
		if (isModLoaded()) {
			try {
				return (IRace) Class.forName(APIHelperClass)
						.getMethod("getRace", EntityPlayer.class).invoke(null, player);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * Can return null
	 *
	 * @see com.temportalist.racesforminecraft.common.APIHelper#getSkills(net.minecraft.entity.player.EntityPlayer)
	 */
	public static HashSet<ISkill> getSkills(EntityPlayer player) {
		if (isModLoaded()) {
			try {
				return (HashSet<ISkill>) Class.forName(APIHelperClass)
						.getMethod("getSkills", EntityPlayer.class).invoke(null, player);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * Can return null
	 *
	 * @see com.temportalist.racesforminecraft.common.APIHelper#getCastes(net.minecraft.entity.player.EntityPlayer)
	 */
	public static HashSet<ICaste> getCastes(EntityPlayer player) {
		if (isModLoaded()) {
			try {
				return (HashSet<ICaste>) Class.forName(APIHelperClass)
						.getMethod("getCastes", EntityPlayer.class).invoke(null, player);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * @see com.temportalist.racesforminecraft.common.APIHelper#applyTalent(net.minecraft.entity.player.EntityPlayer, com.temportalist.racesforminecraft.api.talent.ITalent)
	 */
	public static void applyTalent(EntityPlayer player, ITalent talent) {
		if (isModLoaded()) {
			try {
				Class.forName(APIHelperClass)
						.getMethod("applyTalent", EntityPlayer.class, ITalent.class)
						.invoke(null, player, talent);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @see com.temportalist.racesforminecraft.common.APIHelper#revokeTalent(net.minecraft.entity.player.EntityPlayer, com.temportalist.racesforminecraft.api.talent.ITalent)
	 */
	public static void revokeTalent(EntityPlayer player, ITalent talent) {
		if (isModLoaded()) {
			try {
				Class.forName(APIHelperClass)
						.getMethod("revokeTalent", EntityPlayer.class, ITalent.class)
						.invoke(null, player, talent);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}
