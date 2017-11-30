package com.sudoplay.mc.ctpwcustom.item;

import com.sudoplay.mc.ctpwcustom.api.PWCustomAPI;
import com.sudoplay.mc.ctpwcustom.api.SawRecipe;
import com.sudoplay.mc.ctpwcustom.spi.ItemToolBase;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Collections;
import java.util.List;

@Mod.EventBusSubscriber
public class ItemSaw
    extends ItemToolBase {

  public static final String NAME_FLINT = "saw_flint";
  public static final String NAME_IRON = "saw_iron";
  public static final String NAME_DIAMOND = "saw_diamond";

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
    Block block = event.getState().getBlock();

    if (heldItem.getItem() instanceof ItemSaw) {

      Item item = ItemBlock.getItemFromBlock(block);
      int meta = block.getMetaFromState(event.getState());
      SawRecipe sawRecipe = PWCustomAPI.getSawRecipe(heldItem, new ItemStack(item, 1, meta));

      if (sawRecipe != SawRecipe.NULL) {
        List<ItemStack> drops = event.getDrops();
        drops.clear();

        for (ItemStack itemStack : sawRecipe.getDrops()) {
          drops.add(itemStack.copy());
        }
      }
    }
  }

}
