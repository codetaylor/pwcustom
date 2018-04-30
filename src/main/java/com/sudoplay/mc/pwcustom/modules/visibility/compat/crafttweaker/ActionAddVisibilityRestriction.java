package com.sudoplay.mc.pwcustom.modules.visibility.compat.crafttweaker;

import com.sudoplay.mc.pwcustom.modules.visibility.ModuleVisibility;
import crafttweaker.api.item.IIngredient;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Derived from:
 * https://github.com/Darkhax-Minecraft/ItemStages/blob/master/src/main/java/net/darkhax/itemstages/compat/crt/ActionAddItemRestriction.java
 */
public class ActionAddVisibilityRestriction
    extends ActionVisibilityStage {

  private final String stage;

  public ActionAddVisibilityRestriction(String stage, Item item) {

    super(item);
    this.stage = stage;
  }

  public ActionAddVisibilityRestriction(String stage, IIngredient restricted) {

    super(restricted);
    this.stage = stage;
  }

  @Override
  public void apply() {

    if (this.stage.isEmpty()) {

      throw new IllegalArgumentException("Empty stage name for this entry!");
    }

    this.validate();

    for (final ItemStack stack : this.getRestrictedItems()) {

      ModuleVisibility.VISIBILITY_STAGES.put(stack, this.stage);
    }
  }

  @Override
  public String describe() {

    return "Adding to visibility stage " + this.stage + ". " + this.describeRestrictedStacks();
  }
}
