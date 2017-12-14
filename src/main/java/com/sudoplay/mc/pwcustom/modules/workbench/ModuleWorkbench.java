package com.sudoplay.mc.pwcustom.modules.workbench;

import com.sudoplay.mc.pwcustom.ModPWCustom;
import com.sudoplay.mc.pwcustom.lib.module.ModuleBase;
import com.sudoplay.mc.pwcustom.modules.workbench.block.BlockWorkbench;
import com.sudoplay.mc.pwcustom.modules.workbench.integration.crafttweaker.PluginCraftTweaker;
import com.sudoplay.mc.pwcustom.modules.workbench.tile.*;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import static com.sudoplay.mc.pwcustom.modules.workbench.ModuleWorkbench.Blocks.WORKBENCH_BASIC;

public class ModuleWorkbench
    extends ModuleBase {

  public static class Blocks {

    @GameRegistry.ObjectHolder(ModPWCustom.MOD_ID + ":" + BlockWorkbench.NAME)
    public static final BlockWorkbench WORKBENCH_BASIC;

    static {
      WORKBENCH_BASIC = null;
    }
  }

  @Override
  public void onRegisterBlockEvent(RegistryEvent.Register<Block> event) {

    // Blocks
    event.getRegistry().registerAll(
        new BlockWorkbench()
    );
  }

  @Override
  public void onRegisterItemEvent(RegistryEvent.Register<Item> event) {

    // Item Blocks
    event.getRegistry().registerAll(
        this.getBlockRegistrationHelper().createItemBlocks(
            WORKBENCH_BASIC
        )
    );
  }

  @Override
  public void onRegisterTileEntitiesEvent() {

    this.getTileEntityRegistrationHelper().registerTileEntities(
        ModPWCustom.MOD_ID,
        TileEntityWorkbenchBlacksmith.class,
        TileEntityWorkbenchCarpenter.class,
        TileEntityWorkbenchJeweler.class,
        TileEntityWorkbenchMason.class,
        TileEntityWorkbenchTailor.class
    );
  }

  @Override
  public void onClientRegisterModelsEvent(ModelRegistryEvent event) {

    // Workbench Basic
    this.getModelRegistrationHelper().registerVariantBlockItemModels(
        WORKBENCH_BASIC.getDefaultState(),
        BlockWorkbench.VARIANT
    );
  }

  @Override
  public void onLoadCompleteEvent(FMLLoadCompleteEvent event) {

    if (Loader.isModLoaded("crafttweaker")) {
      PluginCraftTweaker.apply();
    }
  }

}
