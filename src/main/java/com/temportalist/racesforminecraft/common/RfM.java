package com.temportalist.racesforminecraft.common;

import com.temportalist.origin.library.common.helpers.RegisterHelper;
import com.temportalist.origin.wrapper.common.PluginHelper;
import com.temportalist.origin.wrapper.common.PluginWrapper;
import com.temportalist.origin.wrapper.common.ProxyWrapper;
import com.temportalist.racesforminecraft.common.extended.RacePlayer;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

/**
 * @author TheTemportalist
 */
@Mod(modid = RfM.pluginID, name = RfM.pluginName, version = "@PLUGIN_VERSION@",
		guiFactory = "",
		modLanguage = "java",
		dependencies = "required-after:Forge@[10.13,);required-after:origin@[3.3,)"
)
public class RfM extends PluginWrapper {

	public static final String pluginID = "racesforminecraft";
	public static final String pluginName = "Races For Minecraft";

	@SidedProxy(
			clientSide = "com.temportalist.racesforminecraft.client.ClientProxy",
			serverSide = "com.temportalist.racesforminecraft.common.CommonProxy"
	)
	private static ProxyWrapper proxy;

	public static ProxyWrapper getProxy() {
		return RfM.proxy;
	}

	@Mod.Instance(RfM.pluginID)
	private static RfM instance;

	public static RfM getRfM() {
		return RfM.instance;
	}

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		super.preInitialize(RfM.pluginID, RfM.pluginName, event, RfM.proxy,
				PluginHelper.seqFrom(new RfMOptions()));

		RegisterHelper.registerExtendedPlayer("RacePlayer", RacePlayer.class, true);

		RegisterHelper.registerHandler(new EventHandler(), null);

		RegisterHelper.registerCommand(new RfMCommand());

	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		super.initialize(event);

	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		super.postInitialize(event);

		((RfMOptions) this.options()).registerPostInit();

	}

}
