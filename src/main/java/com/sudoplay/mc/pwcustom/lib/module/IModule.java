package com.sudoplay.mc.pwcustom.lib.module;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public interface IModule {

  // --------------------------------------------------------------------------
  // - Common
  // --------------------------------------------------------------------------

  default void onRegisterBlocksEvent(RegistryEvent.Register<Block> event) {
    // override
  }

  default void onRegisterItemsEvent(RegistryEvent.Register<Item> event) {
    // override
  }

  default void onRegisterTileEntitiesEvent() {
    // override
  }

  default void onPreInitialization(FMLPreInitializationEvent event) {
    // override
  }

  default void onInitialization(FMLInitializationEvent event) {
    // override
  }

  default void onPostInitialization(FMLPostInitializationEvent event) {
    // override
  }

  // --------------------------------------------------------------------------
  // - Client
  // --------------------------------------------------------------------------

  default void onClientRegisterModelsEvent(ModelRegistryEvent event) {
    // override
  }

  default void onClientPreInitialization(FMLPreInitializationEvent event) {
    // override
  }

  default void onClientInitialization(FMLInitializationEvent event) {
    // override
  }

  default void onClientPostInitialization(FMLPostInitializationEvent event) {
    // override
  }

}
