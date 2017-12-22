package com.sudoplay.mc.pwcustom.lib.module;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.*;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class ModBase {

  private List<Class<? extends IModule>> moduleClassList;
  private List<IModule> moduleList;
  private IModuleInstanceFactory moduleInstanceFactory;
  private ModuleEventRouter moduleEventRouter;

  public ModBase() {

    this.moduleClassList = new ArrayList<>();
    this.moduleList = new ArrayList<>();
    this.moduleInstanceFactory = new ModuleInstanceFactory();
    this.moduleEventRouter = new ModuleEventRouter(this.moduleList);

    MinecraftForge.EVENT_BUS.register(this.moduleEventRouter);
  }

  @SafeVarargs
  protected final void registerModules(Class<? extends IModule>... moduleClassArray) {

    this.moduleClassList.addAll(Arrays.asList(moduleClassArray));
  }

  public abstract void onConstructionEvent(FMLConstructionEvent event);

  protected void _onConstructionEvent(FMLConstructionEvent event) {

    for (Class<? extends IModule> moduleClass : this.moduleClassList) {

      try {
        IModule module = this.moduleInstanceFactory.instantiate(moduleClass);
        this.moduleList.add(module);

      } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
        // TODO
        e.printStackTrace();
      }
    }

    this.moduleClassList = null;

    Collections.sort(this.moduleList);

    this.moduleEventRouter.onConstructionEvent(event);
  }

  public abstract void onPreInitializationEvent(FMLPreInitializationEvent event);

  protected void _onPreInitializationEvent(FMLPreInitializationEvent event) {

    this.moduleEventRouter.onPreInitializationEvent(event);
  }

  public abstract void onInitializationEvent(FMLInitializationEvent event);

  protected void _onInitializationEvent(FMLInitializationEvent event) {

    this.moduleEventRouter.onInitializationEvent(event);
  }

  public abstract void onPostInitializationEvent(FMLPostInitializationEvent event);

  protected void _onPostInitializationEvent(FMLPostInitializationEvent event) {

    this.moduleEventRouter.onPostInitializationEvent(event);
  }

  public abstract void onLoadCompleteEvent(FMLLoadCompleteEvent event);

  protected void _onLoadCompleteEvent(FMLLoadCompleteEvent event) {

    this.moduleEventRouter.onLoadCompleteEvent(event);
  }

  public abstract void onServerAboutToStartEvent(FMLServerAboutToStartEvent event);

  protected void _onServerAboutToStartEvent(FMLServerAboutToStartEvent event) {

    this.moduleEventRouter.onServerAboutToStartEvent(event);
  }

  public abstract void onServerStartingEvent(FMLServerStartingEvent event);

  protected void _onServerStartingEvent(FMLServerStartingEvent event) {

    this.moduleEventRouter.onServerStartingEvent(event);
  }

  public abstract void onServerStartedEvent(FMLServerStartedEvent event);

  protected void _onServerStartedEvent(FMLServerStartedEvent event) {

    this.moduleEventRouter.onServerStartedEvent(event);
  }

  public abstract void onServerStoppingEvent(FMLServerStoppingEvent event);

  protected void _onServerStoppingEvent(FMLServerStoppingEvent event) {

    this.moduleEventRouter.onServerStoppingEvent(event);
  }

  public abstract void onServerStoppedEvent(FMLServerStoppedEvent event);

  protected void _onServerStoppedEvent(FMLServerStoppedEvent event) {

    this.moduleEventRouter.onServerStoppedEvent(event);
  }
}
