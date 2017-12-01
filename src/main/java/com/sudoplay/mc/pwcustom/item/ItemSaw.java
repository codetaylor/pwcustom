package com.sudoplay.mc.pwcustom.item;

import com.sudoplay.mc.pwcustom.api.PWCustomAPI;
import com.sudoplay.mc.pwcustom.api.SawRecipe;
import com.sudoplay.mc.pwcustom.spi.ItemToolBase;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Mod.EventBusSubscriber
public class ItemSaw
    extends ItemToolBase {

  public ItemSaw(String name, ToolMaterial material) {

    super(name, material, Collections.emptySet());
    this.setMaxStackSize(1);
  }

  @Override
  public float getDestroySpeed(ItemStack stack, IBlockState state) {

    if (state.getMaterial() == Material.WOOD) {
      return this.efficiency;
    }

    return 1.0f;
  }

  @SubscribeEvent
  public static void event(BlockEvent.HarvestDropsEvent event) {

    EntityPlayer player = event.getHarvester();

    if (player == null
        || player.world.isRemote) {
      return;
    }

    ItemStack heldItem = player.getHeldItem(EnumHand.MAIN_HAND);

    if (heldItem.getItem() instanceof ItemSaw) {

      List<ItemStack> drops = event.getDrops();
      List<ItemStack> dropsToAdd = new ArrayList<>();

      for (Iterator<ItemStack> it = drops.iterator(); it.hasNext(); ) {
        ItemStack itemDropped = it.next();
        SawRecipe sawRecipe = PWCustomAPI.getSawRecipe(
            heldItem,
            new ItemStack(itemDropped.getItem(), 1, itemDropped.getMetadata())
        );

        if (sawRecipe != SawRecipe.NULL) {
          it.remove();

          for (ItemStack itemStack : sawRecipe.getDrops()) {
            dropsToAdd.add(itemStack.copy());
          }
        }
      }

      drops.addAll(dropsToAdd);
    }
  }

}
