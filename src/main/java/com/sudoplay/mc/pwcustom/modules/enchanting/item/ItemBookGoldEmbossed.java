package com.sudoplay.mc.pwcustom.modules.enchanting.item;

import com.codetaylor.mc.athenaeum.spi.ItemBase;
import com.sudoplay.mc.pwcustom.modules.enchanting.ModuleEnchanting;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;

public class ItemBookGoldEmbossed
    extends ItemBase {

  public static final String NAME = "book_gold_embossed";

  public ItemBookGoldEmbossed() {

    super(ModuleEnchanting.MOD_ID, ModuleEnchanting.CREATIVE_TAB, NAME);
  }

  @Override
  public boolean isEnchantable(ItemStack stack) {

    return stack.getCount() == 1;
  }

  @Override
  public int getItemEnchantability() {

    return ToolMaterial.GOLD.getEnchantability();
  }

  @Override
  public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {

    return true;
  }
}
