package com.sudoplay.mc.pwcustom.modules.blocks;

import com.sudoplay.mc.pwcustom.ModPWCustom;
import com.sudoplay.mc.pwcustom.modules.blocks.block.BlockSoulGravel;
import com.sudoplay.mc.pwcustom.lib.module.IModule;
import com.sudoplay.mc.pwcustom.lib.util.BlockRegistrationUtil;
import com.sudoplay.mc.pwcustom.lib.util.ModelRegistrationUtil;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModuleBlocks
    implements IModule {

  @SuppressWarnings("WeakerAccess")
  public static class Blocks {

    @GameRegistry.ObjectHolder(ModPWCustom.MOD_ID + ":" + BlockSoulGravel.NAME)
    public static final BlockSoulGravel SOUL_GRAVEL;

    static {
      SOUL_GRAVEL = null;
    }
  }

  @Override
  public void onRegisterBlocksEvent(RegistryEvent.Register<Block> event) {

    event.getRegistry().registerAll(
        new BlockSoulGravel()
    );
  }

  @Override
  public void onRegisterItemsEvent(RegistryEvent.Register<Item> event) {

    event.getRegistry().registerAll(
        BlockRegistrationUtil.createItemBlocks(
            Blocks.SOUL_GRAVEL
        )
    );
  }

  @Override
  public void onClientRegisterModelsEvent(ModelRegistryEvent event) {

    // Single Variant Blocks
    ModelRegistrationUtil.registerBlockItemModels(
        Blocks.SOUL_GRAVEL
    );
  }
}
