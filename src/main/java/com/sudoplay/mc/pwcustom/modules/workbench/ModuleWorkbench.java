package com.sudoplay.mc.pwcustom.modules.workbench;

import com.sudoplay.mc.pwcustom.ModPWCustom;
import com.sudoplay.mc.pwcustom.lib.module.IModule;
import com.sudoplay.mc.pwcustom.lib.util.BlockRegistrationUtil;
import com.sudoplay.mc.pwcustom.lib.util.ModelRegistrationUtil;
import com.sudoplay.mc.pwcustom.lib.util.TileEntityRegistrationUtil;
import com.sudoplay.mc.pwcustom.modules.workbench.block.BlockWorkbench;
import com.sudoplay.mc.pwcustom.modules.workbench.tile.*;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import static com.sudoplay.mc.pwcustom.modules.workbench.ModuleWorkbench.Blocks.WORKBENCH_BASIC;

public class ModuleWorkbench
    implements IModule {

  public static class Blocks {

    @GameRegistry.ObjectHolder(ModPWCustom.MOD_ID + ":" + BlockWorkbench.NAME)
    public static final BlockWorkbench WORKBENCH_BASIC;

    static {
      WORKBENCH_BASIC = null;
    }
  }

  @Override
  public void onRegisterBlocksEvent(RegistryEvent.Register<Block> event) {

    // Blocks
    event.getRegistry().registerAll(
        new BlockWorkbench()
    );
  }

  @Override
  public void onRegisterItemsEvent(RegistryEvent.Register<Item> event) {

    // Item Blocks
    event.getRegistry().registerAll(
        BlockRegistrationUtil.createItemBlocks(
            WORKBENCH_BASIC
        )
    );
  }

  @Override
  public void onRegisterTileEntitiesEvent() {

    TileEntityRegistrationUtil.registerTileEntities(
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
    ModelRegistrationUtil.registerVariantBlockItemModels(
        WORKBENCH_BASIC.getDefaultState(),
        BlockWorkbench.VARIANT
    );
  }
}
