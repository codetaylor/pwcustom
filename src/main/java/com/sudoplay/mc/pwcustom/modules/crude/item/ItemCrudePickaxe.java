package com.sudoplay.mc.pwcustom.modules.crude.item;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.Iterator;
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
      List<ItemStack> dropsToAdd = new ArrayList<>();

      for (Iterator<ItemStack> it = drops.iterator(); it.hasNext(); ) {
        ItemStack drop = it.next();
        Item item = drop.getItem();

        ResourceLocation registryName = item.getRegistryName();

        if (registryName == null) {
          it.remove();
          continue;
        }

        String registryNameString = registryName.toString();

        /*
        minecraft:cobblestone
        minecraft:stone
        minecraft:stone:1
        minecraft:stone:2
        minecraft:stone:3
        minecraft:stone:4
        minecraft:stone:5
        minecraft:stone:6
        */

        if ("minecraft:cobblestone".equals(registryNameString)) {
          Item rep = Item.getByNameOrId("immcraft:rock");

          if (rep != null) {
            dropsToAdd.add(new ItemStack(rep, 1 + player.world.rand.nextInt(3), 0));
          }

        } else if ("minecraft:stone".equals(registryNameString)) {

          if (drop.getMetadata() == 0) {
            Item rep = Item.getByNameOrId("immcraft:rock");

            if (rep != null) {
              dropsToAdd.add(new ItemStack(rep, 1 + player.world.rand.nextInt(3), 0));
            }

          } else if (drop.getMetadata() == 1 || drop.getMetadata() == 2) {
            Item rep = Item.getByNameOrId("immcraft:rock");

            if (rep != null) {
              dropsToAdd.add(new ItemStack(rep, 1 + player.world.rand.nextInt(3), 3));
            }

          } else if (drop.getMetadata() == 3 || drop.getMetadata() == 4) {
            Item rep = Item.getByNameOrId("immcraft:rock");

            if (rep != null) {
              dropsToAdd.add(new ItemStack(rep, 1 + player.world.rand.nextInt(3), 2));
            }

          } else if (drop.getMetadata() == 5 || drop.getMetadata() == 6) {
            Item rep = Item.getByNameOrId("immcraft:rock");

            if (rep != null) {
              dropsToAdd.add(new ItemStack(rep, 1 + player.world.rand.nextInt(3), 1));
            }
          }

        } else if ("bonecraft:pamfossil".equals(registryNameString)) {
          Item rep = Item.getByNameOrId("minecraft:dye");

          if (rep != null) {
            dropsToAdd.add(new ItemStack(rep, 1 + player.world.rand.nextInt(3), 15));
          }
        }

        it.remove();
      }

      drops.addAll(dropsToAdd);
    }

  }
}
