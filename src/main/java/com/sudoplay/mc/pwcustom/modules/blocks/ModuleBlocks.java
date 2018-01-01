package com.sudoplay.mc.pwcustom.modules.blocks;

import com.codetaylor.mc.athenaeum.helper.BlockRegistrationHelper;
import com.codetaylor.mc.athenaeum.helper.ModelRegistrationHelper;
import com.codetaylor.mc.athenaeum.module.ModuleBase;
import com.sudoplay.mc.pwcustom.ModPWCustom;
import com.sudoplay.mc.pwcustom.modules.blocks.block.BlockSoulGravel;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModuleBlocks
    extends ModuleBase {

  public ModuleBlocks() {

    super(0);
  }

  @SuppressWarnings("WeakerAccess")
  public static class Blocks {

    @GameRegistry.ObjectHolder(ModPWCustom.MOD_ID + ":" + BlockSoulGravel.NAME)
    public static final BlockSoulGravel SOUL_GRAVEL;

    static {
      SOUL_GRAVEL = null;
    }
  }

  @Override
  public void onRegisterBlockEvent(RegistryEvent.Register<Block> event) {

    event.getRegistry().registerAll(
        new BlockSoulGravel()
    );
  }

  @Override
  public void onRegisterItemEvent(RegistryEvent.Register<Item> event) {

    event.getRegistry().registerAll(
        BlockRegistrationHelper.createItemBlocks(
            Blocks.SOUL_GRAVEL
        )
    );
  }

  @Override
  public void onClientRegisterModelsEvent(ModelRegistryEvent event) {

    // Single Variant Blocks
    ModelRegistrationHelper.registerBlockItemModels(
        Blocks.SOUL_GRAVEL
    );
  }
}
