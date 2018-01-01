package com.sudoplay.mc.pwcustom.modules.sawing.item;

import com.codetaylor.mc.athenaeum.spi.ItemToolBase;
import com.sudoplay.mc.pwcustom.material.EnumMaterial;
import com.sudoplay.mc.pwcustom.modules.sawing.ModuleSawing;
import com.sudoplay.mc.pwcustom.modules.sawing.api.SawingAPI;
import com.sudoplay.mc.pwcustom.modules.sawing.recipe.RecipeSawing;
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

  private EnumMaterial material;

  public ItemSaw(String name, EnumMaterial material) {

    super(ModuleSawing.MOD_ID, ModuleSawing.CREATIVE_TAB, name, material.getToolMaterial(), Collections.emptySet());
    this.setMaxStackSize(1);
    this.material = material;
  }

  public EnumMaterial getMaterial() {

    return this.material;
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
        RecipeSawing recipeSawing = SawingAPI.RECIPE_REGISTRY.findRecipe(
            heldItem,
            new ItemStack(itemDropped.getItem(), 1, itemDropped.getMetadata())
        );

        if (recipeSawing != null) {
          it.remove();

          for (ItemStack itemStack : recipeSawing.getReplacementItems()) {
            dropsToAdd.add(itemStack.copy());
          }
        }
      }

      drops.addAll(dropsToAdd);
    }
  }

}
