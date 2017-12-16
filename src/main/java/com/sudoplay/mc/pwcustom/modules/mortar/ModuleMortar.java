package com.sudoplay.mc.pwcustom.modules.mortar;

import com.sudoplay.mc.pwcustom.ModPWCustom;
import com.sudoplay.mc.pwcustom.lib.module.ModuleBase;
import com.sudoplay.mc.pwcustom.lib.spi.IBlockVariant;
import com.sudoplay.mc.pwcustom.modules.mortar.block.BlockMortar;
import com.sudoplay.mc.pwcustom.modules.mortar.integration.crafttweaker.PluginCraftTweaker;
import com.sudoplay.mc.pwcustom.modules.mortar.tile.*;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModuleMortar
    extends ModuleBase {

  /**
   * TODO:
   * <p>
   * test sound
   * fix mortar texture
   * graphic displayed in HUD for mortar mode
   * tooltip in JEI to clarify mortar mode
   */

  public static final String MOD_ID = ModPWCustom.MOD_ID;
  public static final boolean IS_DEV = ModPWCustom.IS_DEV;

  public static class Lang {

    public static final String MORTAR_MODE_LABEL = "hud." + MOD_ID + ".mortar.mode";
    public static final String MORTAR_MODE_MIXING = "hud." + MOD_ID + ".mortar.mode.mixing";
    public static final String MORTAR_MODE_CRUSHING = "hud." + MOD_ID + ".mortar.mode.crushing";
  }

  public static class Blocks {

    @GameRegistry.ObjectHolder(MOD_ID + ":" + BlockMortar.NAME)
    public static final BlockMortar MORTAR;

    static {
      MORTAR = null;
    }
  }

  @Override
  public void onRegisterBlockEvent(RegistryEvent.Register<Block> event) {

    event.getRegistry().registerAll(
        new BlockMortar()
    );
  }

  @Override
  public void onRegisterItemEvent(RegistryEvent.Register<Item> event) {

    ItemBlock itemBlock = new ItemMultiTexture(Blocks.MORTAR, Blocks.MORTAR, ((IBlockVariant) Blocks.MORTAR)::getName) {

      @Override
      public int getItemBurnTime(ItemStack itemStack) {

        return 0;
      }
    };

    itemBlock.setMaxStackSize(1);
    itemBlock.setRegistryName(Blocks.MORTAR.getRegistryName());

    event.getRegistry().registerAll(
        itemBlock
    );
  }

  @Override
  public void onRegisterTileEntitiesEvent() {

    this.getTileEntityRegistrationHelper().registerTileEntities(
        MOD_ID,
        TileEntityMortarWood.class,
        TileEntityMortarStone.class,
        TileEntityMortarIron.class,
        TileEntityMortarDiamond.class
    );
  }

  @Override
  public void onClientRegisterModelsEvent(ModelRegistryEvent event) {

    this.getModelRegistrationHelper().registerVariantBlockItemModelsSeparately(
        Blocks.MORTAR.getDefaultState(),
        BlockMortar.VARIANT
    );

    ClientRegistry.bindTileEntitySpecialRenderer(
        TileEntityMortarWood.class,
        new TESRMortar()
    );
    ClientRegistry.bindTileEntitySpecialRenderer(
        TileEntityMortarStone.class,
        new TESRMortar()
    );
    ClientRegistry.bindTileEntitySpecialRenderer(
        TileEntityMortarIron.class,
        new TESRMortar()
    );
    ClientRegistry.bindTileEntitySpecialRenderer(
        TileEntityMortarDiamond.class,
        new TESRMortar()
    );
  }

  @Override
  public void onLoadCompleteEvent(FMLLoadCompleteEvent event) {

    if (Loader.isModLoaded("crafttweaker")) {
      PluginCraftTweaker.apply();
    }
  }

}
