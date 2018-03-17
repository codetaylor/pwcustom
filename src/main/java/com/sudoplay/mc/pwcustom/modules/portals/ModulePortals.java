package com.sudoplay.mc.pwcustom.modules.portals;

import com.codetaylor.mc.athenaeum.module.ModuleBase;
import com.codetaylor.mc.athenaeum.registry.Registry;
import com.codetaylor.mc.athenaeum.util.ModelRegistrationHelper;
import com.sudoplay.mc.pwcustom.ModPWCustom;
import com.sudoplay.mc.pwcustom.modules.portals.block.BlockPortalDarklands;
import com.sudoplay.mc.pwcustom.modules.portals.block.BlockPortalFrame;
import com.sudoplay.mc.pwcustom.modules.portals.item.ItemPortalWand;
import net.minecraft.creativetab.CreativeTabs;

public class ModulePortals
    extends ModuleBase {

  public static final String MOD_ID = ModPWCustom.MOD_ID;
  public static final CreativeTabs CREATIVE_TAB = ModPWCustom.CREATIVE_TAB;

  public ModulePortals() {

    super(0, MOD_ID);
    this.setRegistry(new Registry(MOD_ID, CREATIVE_TAB));
    this.enableAutoRegistry();
  }

  @SuppressWarnings("WeakerAccess")
  public static class Blocks {

    public static final BlockPortalFrame PORTAL_FRAME = new BlockPortalFrame();
    public static final BlockPortalDarklands PORTAL_DARKLANDS = new BlockPortalDarklands();
  }

  public static class Items {

    public static final ItemPortalWand PORTAL_WAND = new ItemPortalWand();
  }

  @Override
  public void onRegister(Registry registry) {

    registry.registerBlockWithItem(Blocks.PORTAL_FRAME, BlockPortalFrame.NAME);
    registry.registerBlock(Blocks.PORTAL_DARKLANDS, BlockPortalDarklands.NAME);

    registry.registerItem(Items.PORTAL_WAND, ItemPortalWand.NAME);
  }

  @Override
  public void onClientRegister(Registry registry) {

    registry.registerClientModelRegistrationStrategy(() -> {

      ModelRegistrationHelper.registerVariantBlockItemModels(
          Blocks.PORTAL_FRAME.getDefaultState(),
          BlockPortalFrame.VARIANT
      );

      ModelRegistrationHelper.registerVariantItemModels(
          Items.PORTAL_WAND,
          "variant",
          BlockPortalFrame.EnumType.values()
      );
    });
  }

}
