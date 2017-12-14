package com.sudoplay.mc.pwcustom.modules.mortar;

import com.sudoplay.mc.pwcustom.ModPWCustom;
import com.sudoplay.mc.pwcustom.lib.module.IModule;
import com.sudoplay.mc.pwcustom.lib.util.BlockRegistrationUtil;
import com.sudoplay.mc.pwcustom.lib.util.ModelRegistrationUtil;
import com.sudoplay.mc.pwcustom.modules.mortar.block.BlockMortar;
import com.sudoplay.mc.pwcustom.modules.mortar.tile.TESRMortar;
import com.sudoplay.mc.pwcustom.modules.mortar.tile.TileEntityMortarWood;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModuleMortar
    implements IModule {

  public static class Blocks {

    @GameRegistry.ObjectHolder(ModPWCustom.MOD_ID + ":" + BlockMortar.NAME)
    public static final BlockMortar MORTAR;

    static {
      MORTAR = null;
    }
  }

  @Override
  public void onRegisterBlocksEvent(RegistryEvent.Register<Block> event) {

    event.getRegistry().registerAll(
        new BlockMortar()
    );
  }

  @Override
  public void onRegisterItemsEvent(RegistryEvent.Register<Item> event) {

    ItemBlock itemBlock = BlockRegistrationUtil.createItemBlock(Blocks.MORTAR);
    itemBlock.setMaxStackSize(1);

    event.getRegistry().registerAll(
        itemBlock
    );
  }

  @Override
  public void onRegisterTileEntitiesEvent() {

    GameRegistry.registerTileEntity(TileEntityMortarWood.class, ModPWCustom.MOD_ID + ".tile.mortar_wood");
  }

  @Override
  public void onClientRegisterModelsEvent(ModelRegistryEvent event) {

    ModelRegistrationUtil.registerVariantBlockItemModelsSeparately(
        Blocks.MORTAR.getDefaultState(),
        BlockMortar.VARIANT
    );

    ClientRegistry.bindTileEntitySpecialRenderer(
        TileEntityMortarWood.class,
        new TESRMortar()
    );
  }
}
