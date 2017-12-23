package com.sudoplay.mc.pwcustom.lib.integration.jei;

import com.sudoplay.mc.pwcustom.lib.integration.IIntegrationPluginHandler;
import mezz.jei.api.IModPlugin;

public class IntegrationPluginHandlerJEI
    implements IIntegrationPluginHandler {

  @Override
  public void execute(String pluginClass) throws Exception {

    IModPlugin plugin = (IModPlugin) Class.forName(pluginClass).newInstance();
    PluginDelegateJEI.registerPlugin(plugin);
  }
}
