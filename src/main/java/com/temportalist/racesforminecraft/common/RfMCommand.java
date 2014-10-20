package com.temportalist.racesforminecraft.common;

import com.temportalist.origin.library.common.utility.Player;
import com.temportalist.racesforminecraft.api.talent.ICaste;
import com.temportalist.racesforminecraft.api.talent.IRace;
import com.temportalist.racesforminecraft.api.talent.ISkill;
import com.temportalist.racesforminecraft.common.extended.RacePlayer;
import com.temportalist.racesforminecraft.common.init.Castes;
import com.temportalist.racesforminecraft.common.init.Races;
import com.temportalist.racesforminecraft.common.init.Skills;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

/**
 * @author TheTemportalist
 */
public class RfMCommand extends CommandBase {

	@Override
	public String getCommandName() {
		return "rfm";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "command.rfm:rfm.usage";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if (!this.checkArgs(sender, args, 2))
			return;

		EntityPlayer player = this.getRequestedPlayer(sender, null);

		if (args[0].equals("get") && args[1].equals("race")) {
			if (this.checkArgs(sender, args, 3)) {
				player = this.getRequestedPlayer(sender, args[2]);
			}

			RacePlayer racePlayer = RacePlayer.get(player);

			if (!sender.getEntityWorld().isRemote)
				sender.addChatMessage(new ChatComponentText(
						player.getCommandSenderName() + "\'s race is " + racePlayer.getRace()
								.getName()
				));
		}
		else if (args[0].equals("set") && args[1].equals("race")) {

			if (!this.checkArgs(sender, args, 3)) {
				return;
			}

			if (this.checkArgs(sender, args, 4)) {
				player = this.getRequestedPlayer(sender, args[3]);
			}

			RacePlayer racePlayer = RacePlayer.get(player);

			IRace race = Races.INSTANCE.getRaceFromName(args[2]);
			if (race == null) {
				this.invalidCommand(sender);
			}
			else {
				racePlayer.setRace(race);
				if (!sender.getEntityWorld().isRemote)
					sender.addChatMessage(new ChatComponentText(
							"Set " + player.getCommandSenderName() + "\'s race to " + args[2]));
			}

		}
		else if (args[0].equals("has")) {

			if (!this.checkArgs(sender, args, 3))
				return;

			if (this.checkArgs(sender, args, 4)) {
				player = this.getRequestedPlayer(sender, args[2]);
			}

			RacePlayer racePlayer = RacePlayer.get(player);

			if (args[1].equals("skill")) {
				if (racePlayer.hasSkill(Skills.INSTANCE.getSkillFromName(args[2]))) {
					if (!sender.getEntityWorld().isRemote)
						sender.addChatMessage(new ChatComponentText(
										player.getCommandSenderName() + " has skill " + args[2])
						);
				}
				else {
					if (!sender.getEntityWorld().isRemote)
						sender.addChatMessage(new ChatComponentText(
										player.getCommandSenderName() + " does not have skill "
												+ args[2])
						);
				}
			}
			else if (args[1].equals("caste")) {
				if (racePlayer.hasCaste(Castes.INSTANCE.getCasteFromName(args[2]))) {
					if (!sender.getEntityWorld().isRemote)
						sender.addChatMessage(new ChatComponentText(
										player.getCommandSenderName() + " has caste " + args[2])
						);
				}
				else {
					if (!sender.getEntityWorld().isRemote)
						sender.addChatMessage(new ChatComponentText(
										player.getCommandSenderName() + " does not have caste "
												+ args[2])
						);
				}
			}

		}
		else if (args[0].equals("add")) {

			if (!this.checkArgs(sender, args, 3))
				return;

			if (this.checkArgs(sender, args, 4)) {
				player = this.getRequestedPlayer(sender, args[2]);
			}

			RacePlayer racePlayer = RacePlayer.get(player);

			if (args[1].equals("skill")) {
				ISkill skill = Skills.INSTANCE.getSkillFromName(args[2]);
				if (skill != null) {
					if (racePlayer.addSkill(skill)) {
						if (!sender.getEntityWorld().isRemote)
							sender.addChatMessage(new ChatComponentText(
											"Added " + args[2] + " to " + player
													.getCommandSenderName()
													+ "\'s skills")
							);
					}
					else {
						if (!sender.getEntityWorld().isRemote)
							sender.addChatMessage(new ChatComponentText(
											player.getCommandSenderName() + " already has skill "
													+ args[2])
							);
					}
				}
				else {
					this.invalidCommand(sender);
				}
			}
			else if (args[1].equals("caste")) {
				ICaste caste = Castes.INSTANCE.getCasteFromName(args[2]);
				if (caste != null) {
					if (racePlayer.addCaste(caste)) {
						if (!sender.getEntityWorld().isRemote)
							sender.addChatMessage(new ChatComponentText(
											"Added " + args[2] + " to " + player
													.getCommandSenderName()
													+ "\'s castes")
							);
					}
					else {
						if (!sender.getEntityWorld().isRemote)
							sender.addChatMessage(new ChatComponentText(
											player.getCommandSenderName() + " already has caste "
													+ args[2])
							);
					}
				}
				else {
					this.invalidCommand(sender);
				}
			}
		}
		else if (args[0].equals("remove")) {

			if (!this.checkArgs(sender, args, 3))
				return;

			if (this.checkArgs(sender, args, 4)) {
				player = this.getRequestedPlayer(sender, args[2]);
			}

			RacePlayer racePlayer = RacePlayer.get(player);

			if (args[1].equals("skill")) {
				ISkill skill = Skills.INSTANCE.getSkillFromName(args[2]);
				if (skill != null) {
					if (racePlayer.removeSkill(skill)) {
						if (!sender.getEntityWorld().isRemote)
							sender.addChatMessage(new ChatComponentText(
											"Removed " + args[2] + " from " + player
													.getCommandSenderName()
													+ "\'s skills")
							);
					}
					else {
						if (!sender.getEntityWorld().isRemote)
							sender.addChatMessage(new ChatComponentText(
											player.getCommandSenderName() + " does not have skill "
													+ args[2])
							);
					}
				}
				else {
					this.invalidCommand(sender);
				}
			}
			else if (args[1].equals("caste")) {
				ICaste caste = Castes.INSTANCE.getCasteFromName(args[2]);
				if (caste != null) {
					if (racePlayer.removeCaste(caste)) {
						if (!sender.getEntityWorld().isRemote)
							sender.addChatMessage(new ChatComponentText(
											"Removed " + args[2] + " from " + player
													.getCommandSenderName()
													+ "\'s castes")
							);
					}
					else {
						if (!sender.getEntityWorld().isRemote)
							sender.addChatMessage(new ChatComponentText(
											player.getCommandSenderName() + " does not have caste "
													+ args[2])
							);
					}
				}
				else {
					this.invalidCommand(sender);
				}
			}
		}

	}

	private boolean checkArgs(ICommandSender sender, String[] args, int wantedLength) {
		if (args.length < wantedLength) {
			this.invalidCommand(sender);
			return false;
		}
		return true;
	}

	private void invalidCommand(ICommandSender sender) {
		if (!sender.getEntityWorld().isRemote)
			sender.addChatMessage(new ChatComponentText("Invalid Command"));
	}

	private EntityPlayer getRequestedPlayer(ICommandSender sender, String name) {
		EntityPlayer player = null;

		if (name != null) {
			player = Player.getPlayer(sender.getCommandSenderName());
		}

		if (player == null && sender instanceof EntityPlayer) {
			player = (EntityPlayer) sender;
		}

		return player;
	}

}
