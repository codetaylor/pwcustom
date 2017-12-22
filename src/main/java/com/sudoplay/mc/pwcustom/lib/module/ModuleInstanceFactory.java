package com.sudoplay.mc.pwcustom.lib.module;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ModuleInstanceFactory
    implements IModuleInstanceFactory {

  @Override
  public <M extends IModule> M instantiate(Class<M> moduleClass) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

    Constructor<M> declaredConstructor = moduleClass.getDeclaredConstructor();
    return declaredConstructor.newInstance();
  }
}
