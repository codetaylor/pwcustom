package com.sudoplay.mc.pwcustom.init;

import com.sudoplay.mc.pwcustom.ModPWCustom;
import com.sudoplay.mc.pwcustom.lib.module.IModule;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class ModuleRegistrationHandler {

  @Mod.EventBusSubscriber(modid = ModPWCustom.MOD_ID)
  public static class CommonModuleRegistrationHandler {

    @SubscribeEvent
    public static void onRegisterBlocksEvent(RegistryEvent.Register<Block> event) {

      List<IModule> modules = ModPWCustom.MODULE_REGISTRY.getModules(new ArrayList<>());

      for (IModule module : modules) {
        module.onRegisterBlocksEvent(event);
        module.onRegisterTileEntitiesEvent();
      }
    }

    @SubscribeEvent
    public static void onRegisterItemsEvent(RegistryEvent.Register<Item> event) {

      List<IModule> modules = ModPWCustom.MODULE_REGISTRY.getModules(new ArrayList<>());

      for (IModule module : modules) {
        module.onRegisterItemsEvent(event);
      }
    }
  }

  @Mod.EventBusSubscriber(modid = ModPWCustom.MOD_ID, value = Side.CLIENT)
  public static class ClientModuleRegistrationHandler {

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {

      List<IModule> modules = ModPWCustom.MODULE_REGISTRY.getModules(new ArrayList<>());

      for (IModule module : modules) {
        module.onClientRegisterModelsEvent(event);
      }
    }
  }

}
