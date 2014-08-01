package com.countrygamer.racesforminecraft.common;

import com.countrygamer.racesforminecraft.common.extended.RacePlayer;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

/**
 * @author CountryGamer
 */
public class EventHandler {

	private boolean canRun(EntityPlayer player) {
		if (FMLCommonHandler.instance().getEffectiveSide().isClient())
			return true;

		RacePlayer racePlayer = RacePlayer.get(player);

		if (racePlayer == null) return true;

		ItemStack heldStack = player.getHeldItem();

		if (heldStack != null) {
			if (!racePlayer.canUseItem(heldStack)) {
				// TODO Send banned item key message
				//Player.sendMessageToPlayer(event.entityPlayer, );
				return false;
			}
		}
		return true;
	}

	@SubscribeEvent
	public void playerInteract_Item(PlayerInteractEvent event) {
		if (!this.canRun(event.entityPlayer)) {
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public void playerInteract_Entity(EntityInteractEvent event) {
		if (!this.canRun(event.entityPlayer)) {
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public void entityAttack(LivingAttackEvent event) {
		if (event.entityLiving instanceof EntityPlayer) {
			if (!this.canRun((EntityPlayer)event.entityLiving)) {
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public void livingUpdate(LivingEvent.LivingUpdateEvent event) {
		// TODO use this to check if the entity is walking over a block

	}

}
