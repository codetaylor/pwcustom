package com.sudoplay.mc.pwcustom.modules.charcoal.compat.jei;

import com.sudoplay.mc.pwcustom.modules.charcoal.recipe.IRecipeTimed;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;

import java.awt.*;

public abstract class JEIRecipeWrapperTimed
    implements IRecipeWrapper {

  protected final String timeString;

  public JEIRecipeWrapperTimed(IRecipeTimed recipe) {

    int totalSecs = recipe.getTimeTicks() / 20;
    int hours = totalSecs / 3600;
    int minutes = (totalSecs % 3600) / 60;
    int seconds = totalSecs % 60;
    this.timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
  }

  @Override
  public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {

    int stringWidth = minecraft.fontRenderer.getStringWidth(this.timeString);
    minecraft.fontRenderer.drawString(this.timeString, recipeWidth - stringWidth, 0, Color.GRAY.getRGB());
  }
}
