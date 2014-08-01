package com.countrygamer.racesforminecraft.common;

import com.countrygamer.cgo.wrapper.common.ProxyWrapper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * @author CountryGamer
 */
public class CommonProxy implements ProxyWrapper {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x,
			int y, int z) {
		return null;
	}

	@Override
	public void registerRender() {
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x,
			int y, int z) {
		return null;
	}

}
