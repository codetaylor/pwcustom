package com.sudoplay.mc.pwcustom.modules.charcoal.event;

import com.sudoplay.mc.pwcustom.modules.charcoal.ModuleCharcoal;
import com.sudoplay.mc.pwcustom.modules.charcoal.init.ModuleFluids;
import com.sudoplay.mc.pwcustom.util.Util;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class FuelHandler {

  @SubscribeEvent
  public static void onFurnaceFuelBurnTimeEvent(FurnaceFuelBurnTimeEvent event) {

    ItemStack fuel = event.getItemStack();

    if (fuel.getItem() == ModuleCharcoal.Items.COAL_COKE) {
      event.setBurnTime(3200);

    } else if (fuel.getItem() == Item.getItemFromBlock(ModuleCharcoal.Blocks.COAL_COKE_BLOCK)) {
      event.setBurnTime(3200 * 10);

    } else if (Util.isFluidBucket(fuel, ModuleFluids.WOOD_TAR.getName())) {
      event.setBurnTime(4800);

    } else if (Util.isFluidBucket(fuel, ModuleFluids.COAL_TAR.getName())) {
      event.setBurnTime(6400);

    } else if (fuel.getItem() == Item.getItemFromBlock(ModuleCharcoal.Blocks.THATCH)) {
      event.setBurnTime(200);

    } else if (fuel.getItem() == ModuleCharcoal.Items.STRAW) {
      event.setBurnTime(50);
    }
  }

}
