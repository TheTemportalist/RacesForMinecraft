package com.temportalist.racesforminecraft.common;

import com.temportalist.origin.wrapper.common.ProxyWrapper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * @author TheTemportalist
 */
public class CommonProxy implements ProxyWrapper {

	@Override
	public void registerRender() {
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x,
			int y, int z) {
		return this.getClientElement(ID, player, world, x, y, z, world.getTileEntity(x, y, z));
	}

	@Override
	public Object getClientElement(int ID, EntityPlayer player, World world, int x, int y,
			int z, TileEntity tileEntity) {
		return null;
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x,
			int y, int z) {
		return this.getServerElement(ID, player, world, x, y, z, world.getTileEntity(x, y, z));
	}

	@Override
	public Object getServerElement(int ID, EntityPlayer player, World world, int x, int y,
			int z, TileEntity tileEntity) {
		return null;
	}

}
