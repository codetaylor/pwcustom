package com.sudoplay.mc.pwcustom.lib.integration.crafttweaker;

import com.sudoplay.mc.pwcustom.lib.integration.IIntegrationPluginHandler;
import com.sudoplay.mc.pwcustom.lib.module.ModuleBase;

import javax.annotation.Nullable;

public class IntegrationPluginHandlerCraftTweaker
    implements IIntegrationPluginHandler {

  @Override
  public void execute(String plugin) throws Exception {

    PluginDelegateCraftTweaker.registerZenClass(Class.forName(plugin));
  }

  @Override
  public boolean hasModule() {

    return true;
  }

  @Nullable
  @Override
  public Class<? extends ModuleBase> getModuleClass() {

    return ModuleIntegrationCraftTweaker.class;
  }
}
