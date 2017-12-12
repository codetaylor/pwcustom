package com.sudoplay.mc.pwcustom.proxy;

import com.sudoplay.mc.pwcustom.ModPWCustom;
import com.sudoplay.mc.pwcustom.gui.GuiHandler;
import com.sudoplay.mc.pwcustom.lib.module.IModule;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import java.util.ArrayList;
import java.util.List;

public abstract class ProxyCommon {

  public void onPreInitialization(FMLPreInitializationEvent event) {

    List<IModule> modules = ModPWCustom.MODULE_REGISTRY.getModules(new ArrayList<>());

    for (IModule module : modules) {
      module.onPreInitialization(event);
    }
  }

  public void onInitialization(FMLInitializationEvent event) {

    NetworkRegistry.INSTANCE.registerGuiHandler(ModPWCustom.INSTANCE, new GuiHandler());

    List<IModule> modules = ModPWCustom.MODULE_REGISTRY.getModules(new ArrayList<>());

    for (IModule module : modules) {
      module.onInitialization(event);
    }
  }

  public void onPostInitialization(FMLPostInitializationEvent event) {

    List<IModule> modules = ModPWCustom.MODULE_REGISTRY.getModules(new ArrayList<>());

    for (IModule module : modules) {
      module.onPostInitialization(event);
    }
  }
}
