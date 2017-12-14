package com.sudoplay.mc.pwcustom.modules.workbench.tile;

import com.sudoplay.mc.pwcustom.ModPWCustom;
import com.sudoplay.mc.pwcustom.modules.workbench.api.WorkbenchAPI;
import com.sudoplay.mc.pwcustom.modules.workbench.block.BlockWorkbench;
import com.sudoplay.mc.pwcustom.modules.workbench.recipe.RegistryRecipeWorkbench;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class TileEntityWorkbenchTailor
    extends TileEntityWorkbenchBasicBase {

  private static final int TEXT_SHADOW_COLOR = new Color(172, 81, 227).getRGB();
  private static final BlockWorkbench.EnumType TYPE = BlockWorkbench.EnumType.TAILOR;
  private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(
      ModPWCustom.MOD_ID,
      "textures/gui/workbench_basic_tailor.png"
  );

  public TileEntityWorkbenchTailor() {

    super(3, 3);
  }

  @Override
  protected int getWorkbenchGuiTextShadowColor() {

    return TEXT_SHADOW_COLOR;
  }

  @Override
  public RegistryRecipeWorkbench getRecipeRegistry() {

    return WorkbenchAPI.RECIPE_REGISTRY_MAP.get(TYPE.getName());
  }

  @Override
  protected ResourceLocation getBackgroundTexture() {

    return BACKGROUND_TEXTURE;
  }
}
