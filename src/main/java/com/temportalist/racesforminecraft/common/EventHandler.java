package com.temportalist.racesforminecraft.common;

import com.temportalist.racesforminecraft.common.extended.RacePlayer;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

/**
 * @author TheTemportalist
 */
public class EventHandler {

	private boolean canRun(EntityPlayer player) {
		if (FMLCommonHandler.instance().getEffectiveSide().isClient()
				|| player.capabilities.isCreativeMode)
			return true;

		RacePlayer racePlayer = RacePlayer.get(player);

		if (racePlayer == null)
			return true;

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
			event.useBlock = Event.Result.DENY;
			event.useItem = Event.Result.DENY;
		}
		else {
			RacePlayer.get(event.entityPlayer)
					.applyEffectsForItem(event.entityPlayer.getHeldItem());
		}
	}

	@SubscribeEvent
	public void playerInteract_Entity(EntityInteractEvent event) {
		if (!this.canRun(event.entityPlayer)) {
			event.setCanceled(true);
		}
		else {
			RacePlayer.get(event.entityPlayer).applyEffectsForItem(
					event.entityPlayer.getHeldItem());
		}
	}

	@SubscribeEvent
	public void entityAttack(LivingAttackEvent event) {
		if (event.entityLiving instanceof EntityPlayer) {
			if (!this.canRun((EntityPlayer) event.entityLiving)) {
				event.setCanceled(true);
			}
			else {
				RacePlayer.get((EntityPlayer) event.entityLiving)
						.applyEffectsForItem(event.entityLiving.getHeldItem());
			}
		}
	}

	@SubscribeEvent
	public void onlivingUpdate(LivingEvent.LivingUpdateEvent event) {
		if (event.entityLiving instanceof EntityPlayer) {
			RacePlayer.get((EntityPlayer) event.entityLiving).applyEffectsForBlock();
		}

	}

}
