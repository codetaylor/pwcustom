package com.sudoplay.mc.pwcustom.modules.crude.item;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

@Mod.EventBusSubscriber
public class ItemCrudePickaxe
    extends ItemPickaxe {

  public ItemCrudePickaxe() {

    super(ToolMaterial.STONE);
    this.setMaxDamage(ToolMaterial.STONE.getMaxUses() / 4);
  }

  @Override
  public float getDestroySpeed(ItemStack stack, IBlockState state) {

    return super.getDestroySpeed(stack, state) * 0.5f;
  }

  @SubscribeEvent
  public static void event(BlockEvent.HarvestDropsEvent event) {

    EntityPlayer player = event.getHarvester();

    if (player == null
        || player.world.isRemote) {
      return;
    }

    ItemStack heldItem = player.getHeldItemMainhand();

    if (heldItem.getItem() instanceof ItemCrudePickaxe) {

      List<ItemStack> drops = event.getDrops();
      drops.clear();

      IBlockState state = event.getState();
      Block block = state.getBlock();
      ResourceLocation blockRegistryName = block.getRegistryName();

      if (blockRegistryName != null) {
        String blockRegistryNameString = blockRegistryName.toString();

        if ("minecraft:cobblestone".equals(blockRegistryNameString)) {
          Item rep = Item.getByNameOrId("survivalist:rock");

          if (rep != null) {
            drops.add(new ItemStack(rep, 1 + player.world.rand.nextInt(3), 0));
          }

        } else if ("minecraft:stone".equals(blockRegistryNameString)) {
          int meta = block.getMetaFromState(state);

          if (meta == 0) {
            Item rep = Item.getByNameOrId("survivalist:rock");

            if (rep != null) {
              drops.add(new ItemStack(rep, 1 + player.world.rand.nextInt(3), 0));
            }

          } else if (meta == 1 || meta == 2) {
            Item rep = Item.getByNameOrId("survivalist:rock");

            if (rep != null) {
              drops.add(new ItemStack(rep, 1 + player.world.rand.nextInt(3), 3));
            }

          } else if (meta == 3 || meta == 4) {
            Item rep = Item.getByNameOrId("survivalist:rock");

            if (rep != null) {
              drops.add(new ItemStack(rep, 1 + player.world.rand.nextInt(3), 2));
            }

          } else if (meta == 5 || meta == 6) {
            Item rep = Item.getByNameOrId("survivalist:rock");

            if (rep != null) {
              drops.add(new ItemStack(rep, 1 + player.world.rand.nextInt(3), 1));
            }
          }
        }
      }
    }
  }
}
