package com.temportalist.racesforminecraft.client;

import com.temportalist.racesforminecraft.common.CommonProxy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * @author TheTemportalist
 */
public class ClientProxy extends CommonProxy {

	@Override
	public void registerRender() {

	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x,
			int y, int z) {
		return null;
	}

}
