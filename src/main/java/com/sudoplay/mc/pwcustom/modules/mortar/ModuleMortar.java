package com.sudoplay.mc.pwcustom.modules.mortar;

import com.sudoplay.mc.pwcustom.ModPWCustom;
import com.sudoplay.mc.pwcustom.lib.module.ModuleBase;
import com.sudoplay.mc.pwcustom.modules.mortar.block.BlockMortar;
import com.sudoplay.mc.pwcustom.modules.mortar.integration.PluginCraftTweaker;
import com.sudoplay.mc.pwcustom.modules.mortar.tile.TESRMortar;
import com.sudoplay.mc.pwcustom.modules.mortar.tile.TileEntityMortarWood;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModuleMortar
    extends ModuleBase {

  public static class Blocks {

    @GameRegistry.ObjectHolder(ModPWCustom.MOD_ID + ":" + BlockMortar.NAME)
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

    ItemBlock itemBlock = this.getBlockRegistrationHelper().createItemBlock(Blocks.MORTAR);
    itemBlock.setMaxStackSize(1);

    event.getRegistry().registerAll(
        itemBlock
    );
  }

  @Override
  public void onRegisterTileEntitiesEvent() {

    this.getTileEntityRegistrationHelper().registerTileEntities(
        ModPWCustom.MOD_ID,
        TileEntityMortarWood.class
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
  }

  @Override
  public void onLoadCompleteEvent(FMLLoadCompleteEvent event) {

    if (Loader.isModLoaded("crafttweaker")) {
      PluginCraftTweaker.apply();
    }
  }

}
