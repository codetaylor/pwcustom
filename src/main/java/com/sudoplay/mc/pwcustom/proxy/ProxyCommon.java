package com.sudoplay.mc.pwcustom.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public abstract class ProxyCommon {

  public void onPreInitialization(FMLPreInitializationEvent event) {
    //
  }

  public void onInitialization(FMLInitializationEvent event) {
    //
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
