/*
 * The MIT License (MIT)
 * Copyright (c) 2014 Dries K. Aka Dries007
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.countrygamer.racesforminecraft.common.lib;

import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;

/**
 * Config file representation
 * Loaded from file (server) or string (client).
 * 
 * @author Dries007
 */
public class Config
{
	private static final JsonParser JSON_PARSER = new JsonParser();
	private static final Gson GSON = new Gson();

	public final JsonObject jsonObject;
	
	public File configFile;
	private HashMap<String, String> messages = new HashMap<String, String>();
	
	public Config(File configFile)
	{
		this.configFile = configFile;
		
		if (configFile.exists())
		{
			JsonObject temp = new JsonObject();
			try
			{
				temp = JSON_PARSER.parse(new FileReader(configFile))
				        .getAsJsonObject();
			} catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}
			jsonObject = temp;
			parseJson();
		}
		else
		{
			populateConfigWithDefaults();
			jsonObject = new JsonObject();
		}
		saveConfig();
	}
	
	public Config(String config)
	{
		jsonObject = JSON_PARSER.parse(config).getAsJsonObject();
		parseJson();
	}
	
	/**
	 * Default config happens in here
	 */
	private void populateConfigWithDefaults()
	{
		/*
		{
			Race human = new Race("Human", "Humans");
			Race.RACE_MAP.put(human.getName(), human);
			human.getBannedItems().add("item.bow");
		}
		
		{
			Race dwarf = new Race("Dwarf", "Dwarves");
			Race.RACE_MAP.put(dwarf.getName(), dwarf);
			dwarf.getBannedItems().add("item.bow");
		}
		
		{
			Race elf = new Race("Elf", "Elves");
			Race.RACE_MAP.put(elf.getName(), elf);
			elf.getBannedItems().add("item.fishing_rod");
		}
		
		messages.put(RoMRef.BANNEDITEM_KEY, "$plural can't use this.");
		*/
		// TODO Put in static ForDummiesBook?
		/*
		new ForDummiesBook(
		        "Archery for dummies",
		        "Villager #8",
		        "You can now use the bow!",
		        new String[]
		        {
			        "item.bow"
		        },
		        "Archery for dummies\nBy Villager #8\n\nTeaches you how to use a bow.\n\nSneak right click to consume.");
		*/
	}
	
	private void parseJson()
	{
		/*
		if (jsonObject.has("messages"))
		{
			messages = RoMRef.GSON.fromJson(jsonObject.get("messages"),
			        new TypeToken<HashMap<String, String>>()
			        {
			        }.getType());
		}
		
		if (jsonObject.has("races"))
		{
			for (JsonElement element : jsonObject.getAsJsonArray("races"))
			{
				if (!element.isJsonObject()) continue;
				
				Race race = RoMRef.GSON.fromJson(element, Race.class);
				Race.RACE_MAP.put(race.getName(), race);
				Race.RACE_TRACKER.add(race.getName());
			}
		}
		
		if (jsonObject.has("books"))
		{
			for (JsonElement element : jsonObject.getAsJsonArray("books"))
			{
				if (!element.isJsonObject()) continue;
				
				// TODO, ForDummiesBook.clas == Skill.class
				// RoMRef.GSON.fromJson(element, ForDummiesBook.class);
			}
		}
		*/
	}
	
	public void saveConfig()
	{
		if (configFile == null) return;

		JsonObject root = new JsonObject();
		/*
		JsonArray races = new JsonArray();
		for (Race race : Race.RACE_MAP.values())
		{
			races.add(GSON.toJsonTree(race));
		}
		root.add("races", races);
		root.add("messages", GSON.toJsonTree(messages));
		JsonArray books = new JsonArray();
		*/
		// TODO ForDummesBook == Skill
		/*
		for (ForDummiesBook dummiesBook : ForDummiesBook.SET)
		{
			books.add(RoMRef.GSON.toJsonTree(dummiesBook));
		}
		*/
		/*
		root.add("books", books);
		*/
		try
		{
			Files.write(GSON.toJson(root), configFile,
					Charset.defaultCharset());
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public String getMessage(String key)
	{
		return messages.containsKey(key) ? messages.get(key) : "";
	}
}
