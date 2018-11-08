package com.sudoplay.mc.pwcustom.modules.charcoal.item;

import com.codetaylor.mc.athenaeum.util.StackHelper;
import com.sudoplay.mc.pwcustom.library.util.Util;
import com.sudoplay.mc.pwcustom.modules.charcoal.block.BlockCampfire;
import com.sudoplay.mc.pwcustom.modules.charcoal.init.ModuleBlocks;
import com.sudoplay.mc.pwcustom.modules.charcoal.init.ModuleItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class ItemTinder
    extends Item {

  public static final String NAME = "tinder";

  @Nonnull
  @Override
  public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {

    ItemStack heldItem = player.getHeldItem(hand);

    if (!heldItem.isEmpty()
        && heldItem.getItem() == ModuleItems.TINDER) {

      RayTraceResult rayTraceResult = this.rayTrace(world, player, true);

      if (rayTraceResult != null
          && rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK) {

        BlockPos blockPos = rayTraceResult.getBlockPos();

        if (!world.isBlockModifiable(player, blockPos)
            || !player.canPlayerEdit(blockPos.offset(rayTraceResult.sideHit), rayTraceResult.sideHit, heldItem)) {
          return new ActionResult<>(EnumActionResult.PASS, heldItem);
        }

        BlockPos offset = blockPos.offset(rayTraceResult.sideHit);

        if (ModuleBlocks.CAMPFIRE.canPlaceBlockAt(world, offset)) {
          this.playSound(world, player);
          world.setBlockState(
              offset,
              ModuleBlocks.CAMPFIRE.getDefaultState()
                  .withProperty(BlockCampfire.VARIANT, BlockCampfire.EnumType.NORMAL)
                  .withProperty(BlockCampfire.WOOD, 0),
              3
          );

          ItemStack itemStack = StackHelper.decrease(heldItem, 1, false);
          //player.setHeldItem(hand, itemStack);
          return new ActionResult<>(EnumActionResult.SUCCESS, itemStack);
        }
      }
    }

    return super.onItemRightClick(world, player, hand);
  }

  private void playSound(World world, EntityPlayer player) {

    world.playSound(
        player,
        player.posX,
        player.posY,
        player.posZ,
        SoundEvents.BLOCK_GRASS_PLACE,
        SoundCategory.PLAYERS,
        1,
        (float) (1 + Util.RANDOM.nextGaussian() * 0.4f)
    );
  }
}
