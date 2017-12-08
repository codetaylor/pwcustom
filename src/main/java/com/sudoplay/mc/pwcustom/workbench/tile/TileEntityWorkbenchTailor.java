package com.sudoplay.mc.pwcustom.workbench.tile;

import com.sudoplay.mc.pwcustom.ModPWCustom;
import com.sudoplay.mc.pwcustom.api.PWCustomAPI;
import com.sudoplay.mc.pwcustom.recipe.RegistryRecipeWorkbenchBasic;
import com.sudoplay.mc.pwcustom.workbench.block.BlockWorkbenchBasic;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class TileEntityWorkbenchTailor
    extends TileEntityWorkbenchBasicBase {

  private static final int TEXT_SHADOW_DARK_COLOR = new Color(70, 34, 100).getRGB();
  private static final int TEXT_SHADOW_LIGHT_COLOR = new Color(172, 81, 227).getRGB();
  private static final BlockWorkbenchBasic.EnumType TYPE = BlockWorkbenchBasic.EnumType.TAILOR;
  private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(
      ModPWCustom.MOD_ID,
      "textures/gui/workbench_basic_tailor.png"
  );

  public TileEntityWorkbenchTailor() {

    super(3, 3);
  }

  @Override
  protected int getWorkbenchGuiTextShadowDarkColor() {

    return TEXT_SHADOW_DARK_COLOR;
  }

  @Override
  protected int getWorkbenchGuiTextShadowLightColor() {

    return TEXT_SHADOW_LIGHT_COLOR;
  }

  @Override
  protected RegistryRecipeWorkbenchBasic getRecipeRegistry() {

    return PWCustomAPI.Recipes.Workbench.REGISTRY_MAP.get(TYPE.getName());
  }

  @Override
  protected ResourceLocation getBackgroundTexture() {

    return BACKGROUND_TEXTURE;
  }
}
