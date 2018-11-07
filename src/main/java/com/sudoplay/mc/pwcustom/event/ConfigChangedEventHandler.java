package com.sudoplay.mc.pwcustom.event;

import com.sudoplay.mc.pwcustom.ModPWCustom;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = ModPWCustom.MOD_ID)
public class ConfigChangedEventHandler {

  @SubscribeEvent
  public static void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {

    if (event.getModID().equals(ModPWCustom.MOD_ID)) {
      ConfigManager.sync(ModPWCustom.MOD_ID, Config.Type.INSTANCE);
    }
  }

}
