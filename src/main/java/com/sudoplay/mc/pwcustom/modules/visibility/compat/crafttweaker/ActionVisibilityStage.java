package com.sudoplay.mc.pwcustom.modules.visibility.compat.crafttweaker;

import crafttweaker.IAction;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.darkhax.bookshelf.lib.Constants;
import net.darkhax.bookshelf.util.StackUtils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.StringJoiner;

/**
 * Derived from:
 * https://github.com/Darkhax-Minecraft/ItemStages/blob/master/src/main/java/net/darkhax/itemstages/compat/crt/ActionItemStage.java
 */
public abstract class ActionVisibilityStage
    implements IAction {

  private ItemStack[] restrictions;

  public ActionVisibilityStage(Item item) {

    this.restrictions = StackUtils.getAllItems(item);

    if (this.restrictions.length == 0) {

      this.restrictions = new ItemStack[]{new ItemStack(item)};
    }
  }

  public ActionVisibilityStage(IIngredient restricted) {

    this.restrictions = CraftTweakerMC.getItemStacks(restricted.getItems());
  }

  protected ItemStack[] getRestrictedItems() {

    return this.restrictions;
  }

  private String describeStack(ItemStack stack) {

    return String.format(
        "%s - %s:%d%s",
        stack.getDisplayName(),
        StackUtils.getStackIdentifier(stack),
        stack.getMetadata(),
        stack.hasTagCompound() ? stack.getTagCompound().toString() : ""
    );
  }

  protected String describeRestrictedStacks() {

    // Handle only one item separately.
    if (this.restrictions.length == 1) {

      return this.describeStack(this.restrictions[0]);
    }

    final StringJoiner joiner = new StringJoiner(Constants.NEW_LINE);

    for (final ItemStack stack : this.getRestrictedItems()) {

      joiner.add(this.describeStack(stack));
    }

    return this.getRestrictedItems().length + " entries: " + Constants.NEW_LINE + joiner.toString();
  }

  protected void validate() {

    if (this.restrictions.length == 0) {

      throw new IllegalArgumentException("No items or blocks found for this entry!");
    }

    for (final ItemStack stack : this.restrictions) {

      if (stack.isEmpty()) {

        throw new IllegalArgumentException("Entry contains an empty/air stack!");
      }
    }
  }
}
