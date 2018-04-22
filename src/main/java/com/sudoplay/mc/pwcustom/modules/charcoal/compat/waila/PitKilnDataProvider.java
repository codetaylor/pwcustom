package com.sudoplay.mc.pwcustom.modules.charcoal.compat.waila;

import com.sudoplay.mc.pwcustom.modules.charcoal.ModuleCharcoal;
import com.sudoplay.mc.pwcustom.modules.charcoal.block.BlockKiln;
import com.sudoplay.mc.pwcustom.modules.charcoal.recipe.KilnRecipe;
import com.sudoplay.mc.pwcustom.modules.charcoal.tile.TileKiln;
import com.sudoplay.mc.pwcustom.util.Util;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import java.util.List;

public class PitKilnDataProvider
    implements IWailaDataProvider {

  @Nonnull
  @Override
  public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {

    return ItemStack.EMPTY;
  }

  @Nonnull
  @Override
  public List<String> getWailaHead(
      ItemStack itemStack,
      List<String> tooltip,
      IWailaDataAccessor accessor,
      IWailaConfigHandler config
  ) {

    return tooltip;
  }

  @Nonnull
  @Override
  public List<String> getWailaBody(
      ItemStack itemStack,
      List<String> tooltip,
      IWailaDataAccessor accessor,
      IWailaConfigHandler config
  ) {

    if (config.getConfig(WailaRegistrar.CONFIG_PROGRESS)
        && accessor.getTileEntity() instanceof TileKiln) {

      IBlockState blockState = accessor.getBlockState();
      TileKiln tileKiln = (TileKiln) accessor.getTileEntity();
      float progress = tileKiln.getProgress();
      ItemStackHandler stackHandler = tileKiln.getStackHandler();
      ItemStack input = stackHandler.getStackInSlot(0);
      KilnRecipe recipe = KilnRecipe.getRecipe(input);
      ItemStack output = ItemStack.EMPTY;

      StringBuilder renderString = new StringBuilder();

      if (recipe != null) {
        output = recipe.getOutput();
        output.setCount(input.getCount());
      }

      if (blockState.getValue(BlockKiln.VARIANT) == BlockKiln.EnumType.COMPLETE) {

        tooltip.add(Util.translate("gui." + ModuleCharcoal.MOD_ID + ".waila.kiln.pit.finished"));

        ItemStackHandler outputStackHandler = tileKiln.getOutputStackHandler();
        StringBuilder outputString = new StringBuilder();
        boolean hasOutput = false;

        for (int i = 0; i < outputStackHandler.getSlots(); i++) {
          ItemStack stackInSlot = outputStackHandler.getStackInSlot(i);

          if (!stackInSlot.isEmpty()) {
            outputString.append(WailaUtil.getStackRenderString(stackInSlot));
            hasOutput = true;
          }
        }

        tooltip.add("");
        tooltip.add(Util.translate("gui." + ModuleCharcoal.MOD_ID + ".waila.result"));

        if (hasOutput) {
          renderString.append(outputString.toString());

        } else {
          renderString.append(Util.translate("gui." + ModuleCharcoal.MOD_ID + ".waila.empty"));
        }

      } else {

        if (input.isEmpty()) {
          renderString.append(Util.translate("gui." + ModuleCharcoal.MOD_ID + ".waila.empty"));

        } else {

          if (blockState.getValue(BlockKiln.VARIANT) == BlockKiln.EnumType.WOOD) {
            tooltip.add(Util.translate("gui." + ModuleCharcoal.MOD_ID + ".waila.kiln.pit.ready"));
          }

          renderString.append(WailaUtil.getStackRenderString(input));
          renderString.append(WailaUtil.getProgressRenderString((int) (100 * progress), 100));
          renderString.append(WailaUtil.getStackRenderString(output));
        }
      }

      tooltip.add(renderString.toString());
    }

    return tooltip;
  }

  @Nonnull
  @Override
  public List<String> getWailaTail(
      ItemStack itemStack,
      List<String> tooltip,
      IWailaDataAccessor accessor,
      IWailaConfigHandler config
  ) {

    return tooltip;
  }

  @Nonnull
  @Override
  public NBTTagCompound getNBTData(
      EntityPlayerMP player,
      TileEntity tileEntity,
      NBTTagCompound tag,
      World world,
      BlockPos pos
  ) {

    return tag;
  }
}
