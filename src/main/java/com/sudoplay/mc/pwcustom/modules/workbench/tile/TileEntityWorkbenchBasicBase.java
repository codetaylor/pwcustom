package com.sudoplay.mc.pwcustom.modules.workbench.tile;

import com.sudoplay.mc.pwcustom.ModPWCustom;
import com.sudoplay.mc.pwcustom.lib.tile.IContainerProvider;
import com.sudoplay.mc.pwcustom.modules.workbench.block.BlockWorkbench;
import com.sudoplay.mc.pwcustom.modules.workbench.gui.ContainerWorkbenchBasic;
import com.sudoplay.mc.pwcustom.modules.workbench.gui.GuiContainerWorkbenchBasic;
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

    BlockWorkbench.EnumType type = state.getValue(BlockWorkbench.VARIANT);

    return new GuiContainerWorkbenchBasic(
        this.getContainer(inventoryPlayer, world, state, pos),
        this.getBackgroundTexture(),
        "container." + ModPWCustom.MOD_ID + ".workbench." + type.getName() + "." + this.getWorkbenchTier().getName(),
        this.getWorkbenchGuiTextShadowColor()
    );
  }

}
