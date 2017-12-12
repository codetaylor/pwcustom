package com.sudoplay.mc.pwcustom.modules.enchanting.event;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ArcaneAffinityTooltipEventHandler {

  @SubscribeEvent
  public static void onItemTooltipEvent(ItemTooltipEvent event) {

    ItemStack itemStack = event.getItemStack();
    Item item = itemStack.getItem();
    int arcaneAffinity = item.getItemEnchantability(itemStack);

    if (arcaneAffinity > 0) {
      event.getToolTip().add("Arcane Affinity: " + arcaneAffinity);
    }

  }

}
