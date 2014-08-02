package com.countrygamer.racesforminecraft.common.lib;

import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.HashSet;

/**
 * @author CountryGamer
 */
public class NameParser {

	public static String getName(ItemStack itemStack) {
		String name;

		Item item = itemStack.getItem();
		// is item
		if (Block.getBlockFromItem(item) == Blocks.air) {
			name = GameData.getItemRegistry().getNameForObject(item);
		}
		// is block
		else {
			name = GameData.getBlockRegistry()
					.getNameForObject(Block.getBlockFromItem(item));
		}

		return name;
	}

	public static ItemStack getItemStack(String name) {
		int endNameIndex = name.length();
		int metadata = OreDictionary.WILDCARD_VALUE;
		if (name.matches("(.*):(.*):(.*)")) {
			endNameIndex = name.lastIndexOf(':');
			metadata = Integer.parseInt(name.substring(endNameIndex + 1, name.length()));
		}
		String modid = name.substring(0, name.indexOf(':'));
		String itemName = name.substring(name.indexOf(':') + 1, endNameIndex);
		ItemStack itemStack = GameRegistry.findItemStack(modid, itemName, 1);
		if (itemStack != null)
			itemStack.setItemDamage(metadata);
		return itemStack;
	}

	public static boolean isInList(ItemStack itemStack, HashSet<String> list) {
		String itemStack_nonMetadata, itemStack_fullName;

		itemStack_nonMetadata = NameParser.getName(itemStack);
		itemStack_fullName = itemStack_nonMetadata + ":" + itemStack.getItemDamage();

		boolean item_is_in_list = list.contains(itemStack_fullName);
		if (!item_is_in_list) {
			item_is_in_list = list.contains(itemStack_nonMetadata);
		}

		return item_is_in_list;
	}

}
