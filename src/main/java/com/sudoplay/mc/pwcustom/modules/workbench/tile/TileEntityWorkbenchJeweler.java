package com.sudoplay.mc.pwcustom.modules.workbench.tile;

import com.sudoplay.mc.pwcustom.ModPWCustom;
import com.sudoplay.mc.pwcustom.modules.workbench.api.WorkbenchAPI;
import com.sudoplay.mc.pwcustom.modules.workbench.recipe.RegistryRecipeWorkbench;
import com.sudoplay.mc.pwcustom.modules.workbench.block.BlockWorkbench;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class TileEntityWorkbenchJeweler
    extends TileEntityWorkbenchBasicBase {

  private static final int TEXT_SHADOW_COLOR = new Color(105, 89, 133).getRGB();
  private static final BlockWorkbench.EnumType TYPE = BlockWorkbench.EnumType.JEWELER;
  private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(
      ModPWCustom.MOD_ID,
      "textures/gui/workbench_basic_jeweler.png"
  );

  public TileEntityWorkbenchJeweler() {

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
