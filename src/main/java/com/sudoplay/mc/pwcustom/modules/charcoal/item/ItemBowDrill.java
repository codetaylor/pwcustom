package com.sudoplay.mc.pwcustom.modules.charcoal.item;

import com.sudoplay.mc.pwcustom.modules.charcoal.ModuleCharcoalConfig;
import com.sudoplay.mc.pwcustom.modules.charcoal.event.IgnitionHandler;
import com.sudoplay.mc.pwcustom.library.util.Util;
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
    extends ItemIgniterBase {

  public static final String NAME = "bow_drill";

  public ItemBowDrill() {

    this.setMaxDamage(ModuleCharcoalConfig.GENERAL.BOW_DRILL_DURABILITY);
    this.setMaxStackSize(1);
  }

  @Override
  public int getMaxItemUseDuration(ItemStack stack) {

    return ModuleCharcoalConfig.GENERAL.BOW_DRILL_USE_DURATION;
  }

}
