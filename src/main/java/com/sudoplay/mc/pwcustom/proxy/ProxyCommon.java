package com.sudoplay.mc.pwcustom.proxy;

import com.sudoplay.mc.pwcustom.ModPWCustom;
import com.sudoplay.mc.pwcustom.gui.GuiHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public abstract class ProxyCommon {

  public void onPreInitialization(FMLPreInitializationEvent event) {
    //
  }

  public void onInitialization(FMLInitializationEvent event) {

    NetworkRegistry.INSTANCE.registerGuiHandler(ModPWCustom.INSTANCE, new GuiHandler());
  }

  public void onPostInitialization(FMLPostInitializationEvent event) {
    //
  }

  class WrongSideException
      extends RuntimeException {

    public WrongSideException(final String message) {

      super(message);
    }

    public WrongSideException(final String message, final Throwable cause) {

      super(message, cause);
    }
  }
}
