package com.sudoplay.mc.pwcustom.lib.module;

import java.lang.reflect.InvocationTargetException;

public interface IModuleInstanceFactory {

  <M extends IModule> M instantiate(Class<M> moduleClass) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException;

}
