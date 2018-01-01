package com.sudoplay.mc.pwcustom.modules.portals;

import com.codetaylor.mc.athenaeum.helper.BlockRegistrationHelper;
import com.codetaylor.mc.athenaeum.helper.ModelRegistrationHelper;
import com.codetaylor.mc.athenaeum.module.ModuleBase;
import com.sudoplay.mc.pwcustom.ModPWCustom;
import com.sudoplay.mc.pwcustom.modules.portals.block.BlockPortalDarklands;
import com.sudoplay.mc.pwcustom.modules.portals.block.BlockPortalFrame;
import com.sudoplay.mc.pwcustom.modules.portals.item.ItemPortalWand;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModulePortals
    extends ModuleBase {

  public static final String MOD_ID = ModPWCustom.MOD_ID;
  public static final CreativeTabs CREATIVE_TAB = ModPWCustom.CREATIVE_TAB;

  public ModulePortals() {

    super(0);
  }

  @SuppressWarnings("WeakerAccess")
  public static class Blocks {

    @GameRegistry.ObjectHolder(ModPWCustom.MOD_ID + ":" + BlockPortalFrame.NAME)
    public static final BlockPortalFrame PORTAL_FRAME;

    @GameRegistry.ObjectHolder(ModPWCustom.MOD_ID + ":" + BlockPortalDarklands.NAME)
    public static final BlockPortalDarklands PORTAL_DARKLANDS;

    static {
      PORTAL_FRAME = null;
      PORTAL_DARKLANDS = null;
    }
  }

  public static class Items {

    @GameRegistry.ObjectHolder(ModPWCustom.MOD_ID + ":" + ItemPortalWand.NAME)
    public static final ItemPortalWand PORTAL_WAND;

    static {
      PORTAL_WAND = null;
    }
  }

  @Override
  public void onRegisterBlockEvent(RegistryEvent.Register<Block> event) {

    // Blocks
    event.getRegistry().registerAll(
        new BlockPortalFrame(),
        new BlockPortalDarklands()
    );
  }

  @Override
  public void onRegisterItemEvent(RegistryEvent.Register<Item> event) {

    // Item Blocks
    event.getRegistry().registerAll(
        BlockRegistrationHelper.createItemBlocks(
            Blocks.PORTAL_FRAME
        )
    );

    // Items
    event.getRegistry().registerAll(
        new ItemPortalWand()
    );
  }

  @Override
  public void onClientRegisterModelsEvent(ModelRegistryEvent event) {

    ModelRegistrationHelper.registerVariantBlockItemModels(
        Blocks.PORTAL_FRAME.getDefaultState(),
        BlockPortalFrame.VARIANT
    );

    ModelRegistrationHelper.registerVariantItemModels(
        Items.PORTAL_WAND,
        "variant",
        BlockPortalFrame.EnumType.values()
    );
  }
}
