package com.sudoplay.mc.pwcustom.modules.charcoal.event;

import com.sudoplay.mc.pwcustom.library.util.Util;
import com.sudoplay.mc.pwcustom.modules.charcoal.ModuleCharcoalConfig;
import com.sudoplay.mc.pwcustom.modules.charcoal.init.ModuleBlocks;
import com.sudoplay.mc.pwcustom.modules.charcoal.init.ModuleFluids;
import com.sudoplay.mc.pwcustom.modules.charcoal.init.ModuleItems;
import com.sudoplay.mc.pwcustom.modules.charcoal.item.ItemMaterial;
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
      event.setBurnTime(ModuleCharcoalConfig.FUEL.COAL_COKE_BURN_TIME_TICKS);

    } else if (fuel.getItem() == Item.getItemFromBlock(ModuleBlocks.COAL_COKE_BLOCK)) {
      event.setBurnTime(ModuleCharcoalConfig.FUEL.COAL_COKE_BLOCK_BURN_TIME_TICKS);

    } else if (Util.isFluidBucket(fuel, ModuleFluids.WOOD_TAR.getName())) {
      event.setBurnTime(ModuleCharcoalConfig.FUEL.WOOD_TAR_BURN_TIME_TICKS);

    } else if (Util.isFluidBucket(fuel, ModuleFluids.COAL_TAR.getName())) {
      event.setBurnTime(ModuleCharcoalConfig.FUEL.COAL_TAR_BURN_TIME_TICKS);

    } else if (fuel.getItem() == Item.getItemFromBlock(ModuleBlocks.THATCH)) {
      event.setBurnTime(ModuleCharcoalConfig.FUEL.THATCH_BURN_TIME_TICKS);

    } else if (fuel.getItem() == ModuleItems.MATERIAL
        && fuel.getMetadata() == ItemMaterial.EnumType.STRAW.getMeta()) {
      event.setBurnTime(ModuleCharcoalConfig.FUEL.STRAW_BURN_TIME_TICKS);

    } else if (fuel.getItem() == ModuleItems.TINDER) {
      event.setBurnTime(ModuleCharcoalConfig.FUEL.TINDER_BURN_TIME_TICKS);
    }
  }

}
