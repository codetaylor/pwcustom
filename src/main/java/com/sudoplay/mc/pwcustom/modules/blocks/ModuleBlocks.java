package com.sudoplay.mc.pwcustom.modules.blocks;

import com.codetaylor.mc.athenaeum.module.ModuleBase;
import com.codetaylor.mc.athenaeum.registry.Registry;
import com.codetaylor.mc.athenaeum.util.ModelRegistrationHelper;
import com.sudoplay.mc.pwcustom.ModPWCustom;
import com.sudoplay.mc.pwcustom.modules.blocks.block.BlockOre;
import com.sudoplay.mc.pwcustom.modules.blocks.block.BlockSoulGravel;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.client.model.ModelLoader;

public class ModuleBlocks
    extends ModuleBase {

  public static final String MOD_ID = ModPWCustom.MOD_ID;
  public static final CreativeTabs CREATIVE_TAB = ModPWCustom.CREATIVE_TAB;

  public ModuleBlocks() {

    super(0, MOD_ID);
    this.setRegistry(new Registry(MOD_ID, CREATIVE_TAB));
    this.enableAutoRegistry();
  }

  @SuppressWarnings("WeakerAccess")
  public static class Blocks {

    public static final BlockSoulGravel SOUL_GRAVEL = new BlockSoulGravel();
    public static final BlockOre ORE = new BlockOre();

  }

  @Override
  public void onRegister(Registry registry) {

    registry.registerBlockWithItem(Blocks.SOUL_GRAVEL, BlockSoulGravel.NAME);
    registry.registerBlockWithItem(Blocks.ORE, BlockOre.NAME);
  }

  @Override
  public void onClientRegister(Registry registry) {

    registry.registerClientModelRegistrationStrategy(() -> {

      // Single Variant Blocks
      ModelRegistrationHelper.registerBlockItemModels(
          Blocks.SOUL_GRAVEL
      );

      // Ore
      ModelLoader.setCustomStateMapper(
          Blocks.ORE,
          (new StateMap.Builder()).withName(BlockOre.VARIANT).build()
      );
      ModelRegistrationHelper.registerVariantBlockItemModelsSeparately(
          MOD_ID,
          Blocks.ORE,
          BlockOre.VARIANT
      );

    });
  }

}
