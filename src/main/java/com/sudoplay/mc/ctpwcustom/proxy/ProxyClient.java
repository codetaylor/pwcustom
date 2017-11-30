package com.sudoplay.mc.ctpwcustom.proxy;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ProxyClient
    extends ProxyCommon {

  @Override
  public void onPreInitialization(FMLPreInitializationEvent event) {

    super.onPreInitialization(event);
    //ModEntities.RegistrationHandler.registerRenderers();
  }

  @Override
  public void onInitialization(FMLInitializationEvent event) {

    super.onInitialization(event);
    //ModBlocks.ClientRegistrationHandler.registerBlockColor();
  }

}
