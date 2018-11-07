package com.sudoplay.mc.pwcustom.modules.charcoal.compat.waila;

import com.codetaylor.mc.athenaeum.util.StringHelper;
import com.sudoplay.mc.pwcustom.library.util.Util;
import com.sudoplay.mc.pwcustom.modules.charcoal.ModuleCharcoal;
import com.sudoplay.mc.pwcustom.modules.charcoal.recipe.KilnBrickRecipe;
import com.sudoplay.mc.pwcustom.modules.charcoal.tile.TileKilnBrick;
import com.sudoplay.mc.pwcustom.modules.charcoal.tile.TileKilnBrickTop;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import java.util.List;

public class KilnBrickDataProvider
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

    if (!config.getConfig(WailaRegistrar.CONFIG_PROGRESS)) {
      return tooltip;
    }

    TileEntity tileEntity = accessor.getTileEntity();

    if (tileEntity instanceof TileKilnBrick
        || tileEntity instanceof TileKilnBrickTop) {

      TileKilnBrick tileKiln = null;

      if (tileEntity instanceof TileKilnBrick) {
        tileKiln = (TileKilnBrick) tileEntity;

      } else {
        World world = tileEntity.getWorld();
        TileEntity candidate = world.getTileEntity(tileEntity.getPos().down());

        if (candidate instanceof TileKilnBrick) {
          tileKiln = (TileKilnBrick) candidate;
        }
      }

      if (tileKiln == null) {
        return tooltip;
      }

      float progress = tileKiln.getProgress();

      ItemStackHandler stackHandler = tileKiln.getStackHandler();
      ItemStackHandler outputStackHandler = tileKiln.getOutputStackHandler();
      ItemStackHandler fuelStackHandler = tileKiln.getFuelStackHandler();

      ItemStack input = stackHandler.getStackInSlot(0);
      boolean hasOutput = !outputStackHandler.getStackInSlot(0).isEmpty();
      ItemStack fuel = fuelStackHandler.getStackInSlot(0);

      if (!input.isEmpty()) {

        // Display input item and recipe output.

        StringBuilder renderString = new StringBuilder();
        renderString.append(WailaUtil.getStackRenderString(input));
        renderString.append(WailaUtil.getStackRenderString(fuel));

        KilnBrickRecipe recipe = KilnBrickRecipe.getRecipe(input);

        if (recipe != null) {
          ItemStack recipeOutput = recipe.getOutput();
          recipeOutput.setCount(input.getCount());
          renderString.append(WailaUtil.getProgressRenderString((int) (100 * progress), 100));
          renderString.append(WailaUtil.getStackRenderString(recipeOutput));
        }

        tooltip.add(renderString.toString());

      } else if (hasOutput) {

        // Display output items.

        //tooltip.add(Util.translate("gui." + ModuleCharcoal.MOD_ID + ".waila.kiln.brick.finished"));

        StringBuilder renderString = new StringBuilder();

        for (int i = 0; i < outputStackHandler.getSlots(); i++) {
          ItemStack stackInSlot = outputStackHandler.getStackInSlot(i);

          if (!stackInSlot.isEmpty()) {
            renderString.append(WailaUtil.getStackRenderString(stackInSlot));
          }
        }

        //tooltip.add(Util.translate("gui." + ModuleCharcoal.MOD_ID + ".waila.result"));
        tooltip.add(renderString.toString());
      }

      {
        if (!fuel.isEmpty()) {
          tooltip.add(Util.translateFormatted(
              "gui." + ModuleCharcoal.MOD_ID + ".waila.kiln.brick.fuel",
              fuel.getItem().getItemStackDisplayName(fuel) + " * " + fuel.getCount()
          ));
        }

        if (tileKiln.getRemainingBurnTimeTicks() > 0) {
          tooltip.add(Util.translateFormatted(
              "gui." + ModuleCharcoal.MOD_ID + ".waila.kiln.brick.heat",
              StringHelper.ticksToHMS(tileKiln.getRemainingBurnTimeTicks())
          ));
        }
      }

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
