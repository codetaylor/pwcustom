package com.sudoplay.mc.pwcustom.modules.workbench;

import com.sudoplay.mc.pwcustom.ModPWCustom;
import com.sudoplay.mc.pwcustom.lib.ModelRegistrationHelper;
import com.sudoplay.mc.pwcustom.lib.TileEntityRegistrationHelper;
import com.sudoplay.mc.pwcustom.lib.module.ModuleBase;
import com.sudoplay.mc.pwcustom.modules.workbench.block.BlockWorkbench;
import com.sudoplay.mc.pwcustom.modules.workbench.item.ItemWorkbench;
import com.sudoplay.mc.pwcustom.modules.workbench.tile.*;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import static com.sudoplay.mc.pwcustom.modules.workbench.ModuleWorkbench.Blocks.WORKBENCH_BASIC;

public class ModuleWorkbench
    extends ModuleBase {

  public ModuleWorkbench() {

    super(0);

    this.registerIntegrationPlugin(
        "crafttweaker",
        "com.sudoplay.mc.pwcustom.modules.workbench.integration.crafttweaker.ZenWorkbench"
    );

    this.registerIntegrationPlugin(
        "jei",
        "com.sudoplay.mc.pwcustom.modules.workbench.integration.jei.PluginJEI"
    );
  }

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
        new ItemWorkbench(Blocks.WORKBENCH_BASIC)
    );
  }

  @Override
  public void onRegisterTileEntitiesEvent() {

    TileEntityRegistrationHelper.registerTileEntities(
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
    ModelRegistrationHelper.registerVariantBlockItemModels(
        WORKBENCH_BASIC.getDefaultState(),
        BlockWorkbench.VARIANT
    );
  }
}
