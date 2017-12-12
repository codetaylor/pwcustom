package com.sudoplay.mc.pwcustom.proxy;

import com.sudoplay.mc.pwcustom.ModPWCustom;
import com.sudoplay.mc.pwcustom.lib.module.IModule;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ProxyClient
    extends ProxyCommon {

  @Override
  public void onPreInitialization(FMLPreInitializationEvent event) {

    super.onPreInitialization(event);

    List<IModule> modules = ModPWCustom.MODULE_REGISTRY.getModules(new ArrayList<>());

    for (IModule module : modules) {
      module.onClientPreInitialization(event);
    }
  }

  @Override
  public void onInitialization(FMLInitializationEvent event) {

    super.onInitialization(event);

    List<IModule> modules = ModPWCustom.MODULE_REGISTRY.getModules(new ArrayList<>());

    for (IModule module : modules) {
      module.onClientInitialization(event);
    }
  }

  @Override
  public void onPostInitialization(FMLPostInitializationEvent event) {

    super.onPostInitialization(event);

    List<IModule> modules = ModPWCustom.MODULE_REGISTRY.getModules(new ArrayList<>());

    for (IModule module : modules) {
      module.onClientPostInitialization(event);
    }
  }
}
