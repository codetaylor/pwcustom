package com.sudoplay.mc.pwcustom.modules.visibility.compat.crafttweaker;

import com.sudoplay.mc.pwcustom.modules.visibility.ModuleVisibility;
import crafttweaker.api.item.IIngredient;
import net.minecraft.item.ItemStack;

public class ActionRemoveRestriction
    extends ActionVisibilityStage {

  public ActionRemoveRestriction(IIngredient restricted) {

    super(restricted);
  }

  @Override
  public void apply() {

    this.validate();

    for (final ItemStack stack : this.getRestrictedItems()) {

      ModuleVisibility.VISIBILITY_STAGES.remove(stack);
    }
  }

  @Override
  public String describe() {

    return "Removing item stage for " + this.describeRestrictedStacks();
  }
}