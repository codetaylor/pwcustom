package com.sudoplay.mc.pwcustom.tile;

import com.sudoplay.mc.pwcustom.ModPWCustom;
import com.sudoplay.mc.pwcustom.api.PWCustomAPI;
import com.sudoplay.mc.pwcustom.client.gui.inventory.GuiContainerWorkbench;
import com.sudoplay.mc.pwcustom.inventory.ContainerWorkbench;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityWorkbenchTailoring
    extends TileEntityWorkbenchBase
    implements IContainerProvider {

  public TileEntityWorkbenchTailoring() {

    super(3, 3, PWCustomAPI.getRegistryRecipeWorkbenchLeatherworking());
  }

  @Override
  public Container getContainer(
      InventoryPlayer inventoryPlayer, World world, IBlockState state, BlockPos pos
  ) {

    return new ContainerWorkbench(inventoryPlayer, world, this);
  }

  @Override
  public GuiContainer getGuiContainer(
      InventoryPlayer inventoryPlayer, World world, IBlockState state, BlockPos pos
  ) {

    return new GuiContainerWorkbench(
        new ContainerWorkbench(inventoryPlayer, world, this),
        "container." + ModPWCustom.MOD_ID + ".workbench.tailoring.basic"
    );
  }

}
