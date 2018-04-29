package com.sudoplay.mc.pwcustom.modules.charcoal.compat.waila;

import com.sudoplay.mc.pwcustom.modules.charcoal.ModuleCharcoal;
import com.sudoplay.mc.pwcustom.modules.charcoal.tile.TileTarTankBase;
import com.sudoplay.mc.pwcustom.library.util.Util;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

import javax.annotation.Nonnull;
import java.util.List;

public class TankDataProvider
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

    if (config.getConfig(WailaRegistrar.CONFIG_TANK)) {
      TileEntity tileEntity = accessor.getTileEntity();

      if (tileEntity instanceof TileTarTankBase) {
        FluidTank fluidTank = ((TileTarTankBase) tileEntity).getFluidTank();
        FluidStack fluid = fluidTank.getFluid();

        if (fluid != null) {
          tooltip.add(Util.translateFormatted(
              "gui." + ModuleCharcoal.MOD_ID + ".waila.tank.fluid",
              fluid.getLocalizedName()
          ));
          tooltip.add(Util.translateFormatted(
              "gui." + ModuleCharcoal.MOD_ID + ".waila.tank.amount",
              fluid.amount,
              fluidTank.getCapacity()
          ));

        } else {
          tooltip.add(Util.translate("gui." + ModuleCharcoal.MOD_ID + ".waila.empty"));
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
      TileEntity te,
      NBTTagCompound tag,
      World world,
      BlockPos pos
  ) {

    return tag;
  }
}
