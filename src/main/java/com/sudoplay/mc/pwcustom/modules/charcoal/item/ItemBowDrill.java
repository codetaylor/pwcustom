package com.sudoplay.mc.pwcustom.modules.charcoal.item;

import com.sudoplay.mc.pwcustom.modules.charcoal.ModuleCharcoalConfig;
import com.sudoplay.mc.pwcustom.modules.charcoal.event.IgnitionHandler;
import com.sudoplay.mc.pwcustom.util.Util;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class ItemBowDrill
    extends Item {

  public static final String NAME = "bow_drill";

  public ItemBowDrill() {

    this.setMaxDamage(ModuleCharcoalConfig.GENERAL.BOW_DRILL_DURABILITY);
    this.setMaxStackSize(1);
  }

  @Override
  public int getMaxItemUseDuration(ItemStack stack) {

    return ModuleCharcoalConfig.GENERAL.BOW_DRILL_USE_DURATION;
  }

  @Nonnull
  @Override
  public EnumAction getItemUseAction(ItemStack stack) {

    return EnumAction.BOW;
  }

  @Nonnull
  @Override
  public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {

    RayTraceResult rayTraceResult = this.rayTrace(world, player, false);
    ItemStack heldItem = player.getHeldItem(hand);

    // The ray trace result can be null
    //noinspection ConstantConditions
    if (rayTraceResult != null
        && rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK) {

      player.setActiveHand(hand);
      return new ActionResult<>(EnumActionResult.SUCCESS, heldItem);

    } else {
      return new ActionResult<>(EnumActionResult.FAIL, heldItem);
    }
  }

  @Override
  public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {

    if (!(player instanceof EntityPlayer)) {
      return;
    }

    World world = player.world;
    RayTraceResult rayTraceResult = this.rayTrace(world, (EntityPlayer) player, false);

    // The ray trace result can be null
    //noinspection ConstantConditions
    if (rayTraceResult == null
        || rayTraceResult.typeOfHit != RayTraceResult.Type.BLOCK) {

      player.stopActiveHand();
      return;
    }

    if (world.isRemote) {

      Vec3d hit = rayTraceResult.hitVec;
      world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, hit.x, hit.y, hit.z, 0, 0, 0);

    } else {

      if (count == 1) {
        BlockPos pos = rayTraceResult.getBlockPos();
        BlockPos offset = pos.offset(rayTraceResult.sideHit);

        if (Util.canSetFire(world, offset)) {
          world.setBlockState(offset, Blocks.FIRE.getDefaultState(), 3);
          world.playSound(
              null,
              offset,
              SoundEvents.ITEM_FLINTANDSTEEL_USE,
              SoundCategory.BLOCKS,
              1.0F,
              Util.RANDOM.nextFloat() * 0.4F + 0.8F
          );

          if (!((EntityPlayer) player).isCreative()) {
            stack.damageItem(1, player);
          }

        } else {
          IgnitionHandler.igniteBlocks(world, pos);
        }
      }
    }
  }
}
