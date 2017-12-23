package com.sudoplay.mc.pwcustom.lib.integration;

import com.sudoplay.mc.pwcustom.lib.module.ModuleBase;

import javax.annotation.Nullable;

public interface IIntegrationPluginHandler {

  void execute(String plugin) throws Exception;

  default boolean hasModule() {

    return false;
  }

  @Nullable
  default Class<? extends ModuleBase> getModuleClass() {

    return null;
  }

}
