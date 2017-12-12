package com.sudoplay.mc.pwcustom.lib.module;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModuleRegistry {

  private Map<Class<? extends IModule>, IModule> moduleMap;

  public ModuleRegistry() {

    this.moduleMap = new HashMap<>();
  }

  public void registerModules(IModule... modules) {

    for (IModule module : modules) {
      this.registerModule(module);
    }
  }

  public void registerModule(IModule module) {

    if (this.moduleMap.containsKey(module.getClass())) {
      throw new RuntimeException("Module already registered: " + module.getClass());
    }

    this.moduleMap.put(module.getClass(), module);
  }

  public <M extends IModule> M getModule(Class<M> moduleClass) {

    //noinspection unchecked
    return (M) this.moduleMap.get(moduleClass);
  }

  public List<IModule> getModules(List<IModule> result) {

    result.addAll(this.moduleMap.values());
    return result;
  }

  public boolean hasModule(Class<? extends IModule> moduleClass) {

    return this.moduleMap.containsKey(moduleClass);
  }
}
