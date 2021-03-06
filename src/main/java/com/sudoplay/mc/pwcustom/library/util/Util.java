package com.sudoplay.mc.pwcustom.library.util;

import com.codetaylor.mc.athenaeum.parser.recipe.item.MalformedRecipeItemException;
import com.codetaylor.mc.athenaeum.parser.recipe.item.ParseResult;
import com.codetaylor.mc.athenaeum.parser.recipe.item.RecipeItemParser;
import com.sudoplay.mc.pwcustom.ModPWCustom;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.text.DecimalFormat;
import java.util.Locale;
import java.util.Random;

/**
 * Some stuff from:
 * https://github.com/SlimeKnights/TinkersConstruct/blob/25a03943276b131e4dcbc74907c887491de343a8/src/main/java/slimeknights/tconstruct/library/Util.java
 */
public class Util {

  public static final String MOD_ID = ModPWCustom.MOD_ID;

  public static final DecimalFormat DF_PERCENT = new DecimalFormat("#%");

  public static final Random RANDOM = new Random();

  public static String prefix(String name) {

    return String.format("%s.%s", MOD_ID, name.toLowerCase(Locale.US));
  }

  public static String translateFormatted(String key, Object... pars) {
    // translates twice to allow rerouting/alias
    return I18n.translateToLocal(I18n.translateToLocalFormatted(key, (Object[]) pars).trim()).trim();
  }

  public static String translate(String key, Object... pars) {
    // translates twice to allow rerouting/alias
    return I18n.translateToLocal(I18n.translateToLocal(String.format(key, (Object[]) pars)).trim()).trim();
  }

  public static BlockMetaMatcher parseBlockStringWithWildcard(
      String blockString,
      RecipeItemParser parser
  ) throws MalformedRecipeItemException {

    ParseResult parse = parser.parse(blockString);
    ResourceLocation resourceLocation = new ResourceLocation(parse.getDomain(), parse.getPath());
    Block block = ForgeRegistries.BLOCKS.getValue(resourceLocation);

    if (block == null) {
      throw new IllegalArgumentException("Unable to locate block for [" + resourceLocation + "]");
    }

    int meta = parse.getMeta();
    return new BlockMetaMatcher(block, meta);
  }

  public static boolean canSetFire(World world, BlockPos pos) {

    if (Util.isLiquid(world, pos)) {
      return false;
    }

    Block block = world.getBlockState(pos).getBlock();

    return block == Blocks.AIR
        || block.isReplaceable(world, pos);
  }

  public static boolean isLiquid(World world, BlockPos pos) {

    return world.getBlockState(pos).getMaterial().isLiquid();
  }

  public static boolean isFluidBucket(ItemStack fuel, String name) {

    FluidStack fluidStack = FluidRegistry.getFluidStack(name, 1000);

    if (fluidStack == null) {
      return false;
    }

    FluidStack candidate = FluidStack.loadFluidStackFromNBT(fuel.getTagCompound());
    return fluidStack.isFluidStackIdentical(candidate);
  }


  private Util() {
    //
  }

}
