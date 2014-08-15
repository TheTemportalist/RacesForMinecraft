package com.countrygamer.racesforminecraft.common;

import com.countrygamer.cgo.common.lib.LogHelper;
import com.countrygamer.cgo.common.lib.NameParser;
import com.countrygamer.cgo.wrapper.common.registries.OptionRegister;
import com.countrygamer.racesforminecraft.api.talent.CasteTrait;
import com.countrygamer.racesforminecraft.common.init.Castes;
import com.countrygamer.racesforminecraft.common.init.Races;
import com.countrygamer.racesforminecraft.common.init.Skills;
import com.countrygamer.racesforminecraft.common.talent.Caste;
import com.countrygamer.racesforminecraft.common.talent.Race;
import com.countrygamer.racesforminecraft.common.talent.Skill;
import com.google.gson.*;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameData;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.config.Configuration;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/**
 * NOTE: Regarding config files in the RacesForMinecraft config directory:
 * Naming.txt is genned per run
 * RacesForMinecraft.cfg is the normal configuration file and CAN be eddited using GuiConfig
 * Races.json, Skills.json, and Castes.json all NEED an mc restart to take effect
 *
 * @author CountryGamer
 */
public class RfMOptions extends OptionRegister {

	@Override
	public boolean hasCustomConfiguration() {
		return true;
	}

	private final Gson GSON = new Gson();
	private final JsonParser JSONPARSER = new JsonParser();

	private File cfgFolder = null;
	private File namesFile = null;
	private File racesFile = null, skillsFile = null, castesFile = null;

	@Override
	public void customizeConfiguration(FMLPreInitializationEvent event) {
		// Create overhead folder
		this.createConfigDir(event);

		// Create natural config file
		File cfgFile = new File(this.cfgFolder, RfM.pluginName + ".cfg");
		this.config_$eq(new Configuration(cfgFile, true));

	}

	public void registerPostInit() {
		// Create naming cfg file
		this.createNamingFile();

		// Create Races.json
		this.racesFile = new File(this.cfgFolder, "Races.json");
		this.createTalentFile(this.racesFile);
		this.loadRaceFile();

		// Create Skills.json
		this.skillsFile = new File(this.cfgFolder, "Skills.json");
		this.createTalentFile(this.skillsFile);
		this.loadSkillFile();

		// Create Castes.json
		this.castesFile = new File(this.cfgFolder, "Castes.json");
		this.createTalentFile(this.castesFile);
		this.loadCasteFile();

	}

	private void createConfigDir(FMLPreInitializationEvent event) {
		this.cfgFolder = new File(event.getModConfigurationDirectory(), "RacesForMinecraft");
		if (!this.cfgFolder.exists()) {
			LogHelper.debug(RfM.pluginName,
					"Creating " + RfM.pluginName + " configuration directory.");
			LogHelper.debug(RfM.pluginName,
					"Created " + RfM.pluginName + " directory: " + this.cfgFolder.mkdir());
		}

	}

	private void createNamingFile() {
		this.namesFile = new File(this.cfgFolder.toString(), "Naming.txt");

		String content = "Blocks\n";
		Iterator iterator = GameData.getBlockRegistry().getKeys().iterator();
		while (iterator.hasNext()) {
			content += "\t" + iterator.next() + "\n";
		}
		content += "Items\n";
		iterator = GameData.getItemRegistry().getKeys().iterator();
		while (iterator.hasNext()) {
			content += "\t" + iterator.next() + "\n";
		}
		content += "Potions\n";
		for (int i = 0; i < Potion.potionTypes.length; i++) {
			if (Potion.potionTypes[i] != null)
				content += "\t" + Potion.potionTypes[i].getName() + "\n";
		}
		content += "Biomes\n";
		for (int i = 0; i < BiomeGenBase.getBiomeGenArray().length; i++) {
			if (BiomeGenBase.getBiomeGenArray()[i] != null)
				content += "\t" + BiomeGenBase.getBiomeGenArray()[i].biomeName + "\n";
		}

		byte[] data = content.getBytes();
		try {
			OutputStream out = new BufferedOutputStream(
					Files.newOutputStream(this.namesFile.toPath(), StandardOpenOption.CREATE,
							StandardOpenOption.WRITE));
			out.write(data, 0, data.length);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void createTalentFile(File file) {
		if (!file.exists()) { // Create
			try {
				com.google.common.io.Files
						.write(GSON.toJson(new JsonArray()), file, Charset.defaultCharset());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private JsonArray loadTalentFile(File file) {
		JsonArray jsonArray = new JsonArray();
		try {
			jsonArray = this.JSONPARSER.parse(new FileReader(file)).getAsJsonArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return jsonArray;
	}

	private void loadRaceFile() {
		if (this.racesFile.exists()) {
			JsonArray jsonArray = this.loadTalentFile(this.racesFile);
			for (JsonElement element : jsonArray) {
				if (element.isJsonObject()) {
					JsonObject raceObject = element.getAsJsonObject();

					HashSet<String> list = new HashSet<String>();
					for (JsonElement listElement : raceObject.getAsJsonArray("list")) {
						list.add(listElement.getAsString());
					}

					HashSet<String> inheritSkills = new HashSet<String>();
					if (raceObject.has("inheritSkills")) {
						for (JsonElement skillElement : raceObject
								.getAsJsonArray("inheritSkills")) {
							inheritSkills.add(skillElement.getAsString());
						}
					}

					HashSet<String> inheritCastes = new HashSet<String>();
					if (raceObject.has("inheritCastes")) {
						for (JsonElement casteElement : raceObject
								.getAsJsonArray("inheritCastes")) {
							inheritCastes.add(casteElement.getAsString());
						}
					}

					Races.INSTANCE.registerTalent(new Race(
							raceObject.get("name").getAsString(),
							raceObject.get("listIsBlacklist").getAsBoolean(),
							list, inheritSkills, inheritCastes
					));
				}
			}
		}
	}

	private void loadSkillFile() {
		if (this.skillsFile.exists()) {
			// Get the overhead array
			JsonArray jsonArray = this.loadTalentFile(this.skillsFile);
			// iterate for each skill
			for (JsonElement element : jsonArray) {
				if (element.isJsonObject()) {
					// get the dictionary
					JsonObject jsonObject = element.getAsJsonObject();
					// get the skill name
					String name = jsonObject.get("name").getAsString();
					// create a new hashset for the blacklist
					HashSet<String> blackList = new HashSet<String>();
					// iterate and get each string name from the blacklist json array
					for (JsonElement listElement : jsonObject.get("blacklist").getAsJsonArray()) {
						blackList.add(listElement.getAsString());
					}
					// create a new hashset for the whitelist
					HashSet<String> whitelist = new HashSet<String>();
					// iterate and get each string name from the whitelist json array
					for (JsonElement listElement : jsonObject.get("whitelist").getAsJsonArray()) {
						whitelist.add(listElement.getAsString());
					}
					// Create the skill with its name
					Skill skill = new Skill(name);
					// add the blacklist
					skill.getBlacklist().addAll(blackList);
					// add the whitelist
					skill.getWhitelist().addAll(whitelist);
					// register the skill
					Skills.INSTANCE.registerTalent(skill);
				}
			}
		}
	}

	private void loadCasteFile() {
		if (this.castesFile.exists()) {
			// Get the overhead array
			JsonArray jsonArray = this.loadTalentFile(this.castesFile);
			// iterate for each skill
			for (JsonElement element : jsonArray) {
				if (element.isJsonObject()) {
					// get the dictionary
					JsonObject jsonObject = element.getAsJsonObject();
					// get the skill name
					String name = jsonObject.get("name").getAsString();

					HashSet<CasteTrait> traits = new HashSet<CasteTrait>();

					for (JsonElement effectsElement : jsonObject.getAsJsonArray("effects")) {
						if (!effectsElement.isJsonObject())
							continue;
						JsonObject effectsObj = effectsElement.getAsJsonObject();

						boolean biomeIsBlacklist = true;
						if (effectsObj.has("biomeIsBlacklist")) {
							biomeIsBlacklist = effectsObj.get("biomeIsBlacklist").getAsBoolean();
						}

						HashSet<Integer> biomes = new HashSet<Integer>();

						if (effectsObj.has("biome")) {

							HashSet<Integer> parsableBiomes = new HashSet<Integer>();

							for (JsonElement biomeElement : effectsObj.getAsJsonArray("biome")) {
								String biomeName = biomeElement.getAsString();
								int biomeID = this.getBiomeID(biomeName);
								if (biomeID < 0) {
									LogHelper.error(RfM.pluginName,
											"Fatal error, biome with name " + biomeName
													+ " does not exist");
									continue;
								}
								parsableBiomes.add(biomeID);
							}

							for (int i = 0; i < BiomeGenBase.getBiomeGenArray().length; i++) {

								if (BiomeGenBase.getBiomeGenArray()[i] == null)
									continue;

								boolean biomeIsInList =
										parsableBiomes
												.contains(
														BiomeGenBase.getBiomeGenArray()[i].biomeID);

								boolean isApplicable = (biomeIsBlacklist && !biomeIsInList)
										|| (!biomeIsBlacklist && biomeIsInList);

								if (isApplicable)
									biomes.add(BiomeGenBase.getBiomeGenArray()[i].biomeID);

							}

						}

						if (!effectsObj.has("block") && !effectsObj.has("item")) {
							LogHelper.error(RfM.pluginName,
									"ERROR PARSING Castes.json! Missing both \'block\' AND"
											+ " \'item\' lists. Must have at least one!");
							return;
						}

						HashSet<String> blocks = new HashSet<String>();
						if (effectsObj.has("block")) {
							for (JsonElement blockElement : effectsObj.getAsJsonArray("block")) {
								blocks.add(blockElement.getAsString());
							}
						}

						HashSet<String> items = new HashSet<String>();
						if (effectsObj.has("item")) {
							for (JsonElement itemElement : effectsObj.getAsJsonArray("item")) {
								items.add(itemElement.getAsString());
							}
						}

						HashMap<PotionEffect, Integer> potionEffects =
								new HashMap<PotionEffect, Integer>();
						for (JsonElement potionEffectsElement : effectsObj.getAsJsonArray(
								"potioneffects")) {
							if (!potionEffectsElement.isJsonObject())
								continue;
							JsonObject potionEffectsObj = potionEffectsElement.getAsJsonObject();

							String effectName = potionEffectsObj.get("name").getAsString();
							int effectID = this.getPotionID(effectName);

							if (effectID < 0) {
								LogHelper.error(RfM.pluginName,
										"Fatal error, potion effect with name " + effectName
												+ " does not exist");
								continue;
							}

							int distanceY = 1;
							if (potionEffectsObj.has("distanceY")) {
								distanceY = potionEffectsObj.get("distanceY").getAsInt();
							}
							int duration = potionEffectsObj.get("duration").getAsInt();
							int amplifier = potionEffectsObj.get("amplifier").getAsInt();

							PotionEffect potionEffect = new PotionEffect(effectID, duration,
									amplifier);
							if (potionEffectsObj.has("hasDefaultCurativeItems") && !potionEffectsObj
									.get("hasDefaultCurativeItems").getAsBoolean()) {
								potionEffect.setCurativeItems(new ArrayList<ItemStack>());
							}

							if (potionEffectsObj.has("curativeItems")) {
								for (JsonElement curativeItemsElement : potionEffectsObj
										.getAsJsonArray("curativeItems")) {
									potionEffect.addCurativeItem(NameParser
											.getItemStack(curativeItemsElement.getAsString()));
								}
							}

							potionEffects.put(potionEffect, distanceY);

						}

						for (int biomeID : biomes) {
							for (String blockName : blocks) {
								Iterator<PotionEffect> iterator = potionEffects.keySet().iterator();
								while (iterator.hasNext()) {
									PotionEffect potionEffect = iterator.next();
									traits.add(new CasteTrait(
											biomeID, blockName,
											potionEffects.get(potionEffect), potionEffect));
								}
							}
							for (String itemName : items) {
								Iterator<PotionEffect> iterator = potionEffects.keySet().iterator();
								while (iterator.hasNext()) {
									PotionEffect potionEffect = iterator.next();
									traits.add(new CasteTrait(biomeID, itemName, potionEffect));
								}
							}
						}

					}

					Castes.INSTANCE.registerTalent(new Caste(name, traits));
				}
			}
		}
	}

	private int getPotionID(String name) {
		for (int i = 0; i < Potion.potionTypes.length; i++) {
			if (Potion.potionTypes[i] != null && name.equals(Potion.potionTypes[i].getName())) {
				return i;
			}
		}
		return -1;
	}

	private int getBiomeID(String name) {
		for (int i = 0; i < BiomeGenBase.getBiomeGenArray().length; i++) {
			if (BiomeGenBase.getBiomeGenArray()[i] != null && BiomeGenBase
					.getBiomeGenArray()[i].biomeName.equals(name))
				return i;
		}
		return -1;
	}

	@Override
	public void register() {
		// TODO Do NORMAL .cfg things here!

	}

	@Override
	public Class<? extends GuiScreen> getGuiConfigClass() {
		return super.getGuiConfigClass();
	}

}
