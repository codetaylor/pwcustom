package com.sudoplay.mc.pwcustom.workbench.tile;

import com.sudoplay.mc.pwcustom.ModPWCustom;
import com.sudoplay.mc.pwcustom.tile.IContainerProvider;
import com.sudoplay.mc.pwcustom.workbench.block.BlockWorkbenchBasic;
import com.sudoplay.mc.pwcustom.workbench.gui.ContainerWorkbenchBasic;
import com.sudoplay.mc.pwcustom.workbench.gui.GuiContainerWorkbenchBasic;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class TileEntityWorkbenchBasicBase
    extends TileEntityWorkbenchBase
    implements IContainerProvider<ContainerWorkbenchBasic, GuiContainerWorkbenchBasic> {

  public TileEntityWorkbenchBasicBase(int width, int height) {

    super(width, height);
  }

  protected EnumWorkbenchTier getWorkbenchTier() {

    return EnumWorkbenchTier.BASIC;
  }

  @Override
  public ContainerWorkbenchBasic getContainer(
      InventoryPlayer inventoryPlayer, World world, IBlockState state, BlockPos pos
  ) {

    return new ContainerWorkbenchBasic(inventoryPlayer, world, this);
  }

  @Override
  public GuiContainerWorkbenchBasic getGuiContainer(
      InventoryPlayer inventoryPlayer, World world, IBlockState state, BlockPos pos
  ) {

    BlockWorkbenchBasic.EnumType type = state.getValue(BlockWorkbenchBasic.VARIANT);

    return new GuiContainerWorkbenchBasic(
        this.getContainer(inventoryPlayer, world, state, pos),
        this.getBackgroundTexture(),
        "container." + ModPWCustom.MOD_ID + ".workbench." + type.getName() + "." + this.getWorkbenchTier().getName(),
        this.getWorkbenchGuiTextShadowLightColor(),
        this.getWorkbenchGuiTextShadowDarkColor()
    );
  }

}
