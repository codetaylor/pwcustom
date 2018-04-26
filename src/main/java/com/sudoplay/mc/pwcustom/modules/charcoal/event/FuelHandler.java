package com.sudoplay.mc.pwcustom.modules.charcoal.event;

import com.sudoplay.mc.pwcustom.modules.charcoal.init.ModuleBlocks;
import com.sudoplay.mc.pwcustom.modules.charcoal.init.ModuleFluids;
import com.sudoplay.mc.pwcustom.modules.charcoal.init.ModuleItems;
import com.sudoplay.mc.pwcustom.modules.charcoal.item.ItemMaterial;
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

    if (fuel.getItem() == ModuleItems.MATERIAL
        && fuel.getMetadata() == ItemMaterial.EnumType.COAL_COKE.getMeta()) {
      event.setBurnTime(3200);

    } else if (fuel.getItem() == Item.getItemFromBlock(ModuleBlocks.COAL_COKE_BLOCK)) {
      event.setBurnTime(3200 * 10);

    } else if (Util.isFluidBucket(fuel, ModuleFluids.WOOD_TAR.getName())) {
      event.setBurnTime(4800);

    } else if (Util.isFluidBucket(fuel, ModuleFluids.COAL_TAR.getName())) {
      event.setBurnTime(6400);

    } else if (fuel.getItem() == Item.getItemFromBlock(ModuleBlocks.THATCH)) {
      event.setBurnTime(200);

    } else if (fuel.getItem() == ModuleItems.MATERIAL
        && fuel.getMetadata() == ItemMaterial.EnumType.STRAW.getMeta()) {
      event.setBurnTime(50);
    }
  }

}
