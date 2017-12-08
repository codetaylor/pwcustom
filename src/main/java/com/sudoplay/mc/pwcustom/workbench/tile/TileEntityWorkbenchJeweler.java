package com.sudoplay.mc.pwcustom.workbench.tile;

import com.sudoplay.mc.pwcustom.ModPWCustom;
import com.sudoplay.mc.pwcustom.api.PWCustomAPI;
import com.sudoplay.mc.pwcustom.recipe.RegistryRecipeWorkbenchBasic;
import com.sudoplay.mc.pwcustom.workbench.block.BlockWorkbenchBasic;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class TileEntityWorkbenchJeweler
    extends TileEntityWorkbenchBasicBase {

  private static final int TEXT_SHADOW_DARK_COLOR = new Color(188, 152, 98).getRGB();
  private static final int TEXT_SHADOW_LIGHT_COLOR = new Color(105, 89, 133).getRGB();
  private static final BlockWorkbenchBasic.EnumType TYPE = BlockWorkbenchBasic.EnumType.JEWELER;
  private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(
      ModPWCustom.MOD_ID,
      "textures/gui/workbench_basic_jeweler.png"
  );

  public TileEntityWorkbenchJeweler() {

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
  public RegistryRecipeWorkbenchBasic getRecipeRegistry() {

    return PWCustomAPI.Recipes.Workbench.REGISTRY_MAP.get(TYPE.getName());
  }

  @Override
  protected ResourceLocation getBackgroundTexture() {

    return BACKGROUND_TEXTURE;
  }
}
