package com.sudoplay.mc.pwcustom.event;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class TooltipEventHandler {

  @SubscribeEvent
  public static void onItemTooltipEvent(ItemTooltipEvent event) {

    ItemStack itemStack = event.getItemStack();
    Item item = itemStack.getItem();
    int enchantability = item.getItemEnchantability(itemStack);

    if (enchantability > 0) {
      event.getToolTip().add("Arcane Affinity: " + enchantability);
    }

  }

}
