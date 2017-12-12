package com.sudoplay.mc.pwcustom.modules.workbench.tile;

import com.sudoplay.mc.pwcustom.ModPWCustom;
import com.sudoplay.mc.pwcustom.api.PWCustomAPI;
import com.sudoplay.mc.pwcustom.modules.workbench.recipe.RegistryRecipeWorkbench;
import com.sudoplay.mc.pwcustom.modules.workbench.block.BlockWorkbench;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class TileEntityWorkbenchBlacksmith
    extends TileEntityWorkbenchBasicBase {

  private static final int TEXT_SHADOW_COLOR = new Color(162, 162, 162).getRGB();
  private static final BlockWorkbench.EnumType TYPE = BlockWorkbench.EnumType.BLACKSMITH;
  private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(
      ModPWCustom.MOD_ID,
      "textures/gui/workbench_basic_blacksmith.png"
  );

  public TileEntityWorkbenchBlacksmith() {

    super(3, 3);
  }

  @Override
  protected int getWorkbenchGuiTextShadowColor() {

    return TEXT_SHADOW_COLOR;
  }

  @Override
  public RegistryRecipeWorkbench getRecipeRegistry() {

    return PWCustomAPI.Recipes.Workbench.REGISTRY_MAP.get(TYPE.getName());
  }

  @Override
  protected ResourceLocation getBackgroundTexture() {

    return BACKGROUND_TEXTURE;
  }
}
