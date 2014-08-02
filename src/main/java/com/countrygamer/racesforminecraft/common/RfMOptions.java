package com.countrygamer.racesforminecraft.common;

import com.countrygamer.cgo.common.lib.LogHelper;
import com.countrygamer.cgo.wrapper.common.registries.OptionRegister;
import com.countrygamer.racesforminecraft.common.init.Castes;
import com.countrygamer.racesforminecraft.common.init.Races;
import com.countrygamer.racesforminecraft.common.init.Skills;
import com.countrygamer.racesforminecraft.common.lib.NameParser;
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

					Races.INSTANCE.registerTalent(new Race(
							raceObject.get("name").getAsString(),
							raceObject.get("listIsBlacklist").getAsBoolean(),
							list
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
					skill.addList(true, blackList);
					// add the whitelist
					skill.addList(false, whitelist);
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

					HashMap<String, HashMap<PotionEffect, Integer>> map =
							new HashMap<String, HashMap<PotionEffect, Integer>>();
					for (JsonElement blockAndEffect : jsonObject.getAsJsonArray("blocks")) {
						if (!blockAndEffect.isJsonObject())
							continue;
						JsonObject blockAndEffectObj = blockAndEffect.getAsJsonObject();

						for (JsonElement blockElement : blockAndEffectObj.getAsJsonArray("block")) {
							String block = blockElement.getAsString();

							HashMap<PotionEffect, Integer> effects = new HashMap<PotionEffect, Integer>();

							for (JsonElement effectElement : blockAndEffectObj
									.getAsJsonArray("effects")) {
								if (!effectElement.isJsonObject())
									continue;
								JsonObject effectObj = effectElement.getAsJsonObject();

								int effectID = this
										.getPotionID(effectObj.get("name").getAsString());
								if (effectID < 0)
									continue;

								int distanceY = effectObj.get("distanceY").getAsInt();
								int duration = effectObj.get("duration").getAsInt();
								int amplifier = effectObj.get("amplifier").getAsInt();
								boolean isAmbient = effectObj.get("isAmbient").getAsBoolean();

								PotionEffect potionEffect = new PotionEffect(effectID, duration,
										amplifier, isAmbient);
								if (!effectObj.get("hasDefaultCurativeItems").getAsBoolean()) {
									potionEffect.setCurativeItems(new ArrayList<ItemStack>());
								}
								for (JsonElement itemElement : effectObj
										.getAsJsonArray("curativeItems")) {
									potionEffect.addCurativeItem(
											NameParser.getItemStack(itemElement.getAsString()));
								}

								effects.put(potionEffect, distanceY);

							}

							map.put(block, effects);

						}

					}

					Castes.INSTANCE.registerTalent(new Caste(name, map));
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

	@Override
	public void register() {
		// TODO Do NORMAL .cfg things here!

	}

	@Override
	public Class<? extends GuiScreen> getGuiConfigClass() {
		return super.getGuiConfigClass();
	}

}
