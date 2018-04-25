package com.sudoplay.mc.pwcustom.modules.block.init;

import com.codetaylor.mc.athenaeum.registry.Registry;
import com.codetaylor.mc.athenaeum.util.ModelRegistrationHelper;
import com.sudoplay.mc.pwcustom.modules.block.ModuleBlock;
import com.sudoplay.mc.pwcustom.modules.block.block.BlockOre;
import com.sudoplay.mc.pwcustom.modules.block.block.BlockSoulGravel;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModuleBlocks {

  public static final BlockSoulGravel SOUL_GRAVEL = new BlockSoulGravel();
  public static final BlockOre ORE = new BlockOre();

  public static void onRegister(Registry registry) {

    registry.registerBlockWithItem(ModuleBlocks.SOUL_GRAVEL, BlockSoulGravel.NAME);
    registry.registerBlockWithItem(ModuleBlocks.ORE, BlockOre.NAME);
  }

  @SideOnly(Side.CLIENT)
  public static void onClientRegister(Registry registry) {

    registry.registerClientModelRegistrationStrategy(() -> {

      // Single Variant Blocks
      ModelRegistrationHelper.registerBlockItemModels(
          ModuleBlocks.SOUL_GRAVEL
      );

      // Ore
      ModelLoader.setCustomStateMapper(
          ModuleBlocks.ORE,
          (new StateMap.Builder()).withName(BlockOre.VARIANT).build()
      );
      ModelRegistrationHelper.registerVariantBlockItemModelsSeparately(
          ModuleBlock.MOD_ID,
          ModuleBlocks.ORE,
          BlockOre.VARIANT
      );

    });
  }
}
