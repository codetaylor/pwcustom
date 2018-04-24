package com.sudoplay.mc.pwcustom.modules.charcoal.tile;

import com.sudoplay.mc.pwcustom.modules.charcoal.ModuleCharcoal;
import com.sudoplay.mc.pwcustom.modules.charcoal.ModuleCharcoalConfig;
import com.sudoplay.mc.pwcustom.modules.charcoal.Registries;
import com.sudoplay.mc.pwcustom.modules.charcoal.block.BlockRefractoryDoor;
import com.sudoplay.mc.pwcustom.modules.charcoal.recipe.BurnRecipe;
import com.sudoplay.mc.pwcustom.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class TileActivePile
    extends TileBurnableBase {

  private static final int DEFAULT_TOTAL_BURN_TIME_TICKS = 1000;
  private static final int DEFAULT_BURN_STAGES = 1;
  private static final int MAX_FLUID_PUSH_DEPTH = 3;

  private FluidTank fluidTank;
  private ResourceLocation recipeKey;
  private ItemStackHandler output;

  public TileActivePile() {

    super();
    this.fluidTank = new FluidTank(this.getMaxFluidLevel());
    this.output = new ItemStackHandler(9);
  }

  public FluidTank getFluidTank() {

    return this.fluidTank;
  }

  public ItemStackHandler getOutput() {

    return this.output;
  }

  public void setRecipe(BurnRecipe recipe) {

    this.recipeKey = recipe.getRegistryName();

    // Call this to recalculate the current burn time after setting the recipe.
    this.reset();

    // Call this for the same reason.
    this.remainingStages = this.getBurnStages();
  }

  @Override
  protected int getTotalBurnTimeTicks() {

    BurnRecipe recipe = Registries.BURN_RECIPE.getValue(this.recipeKey);

    if (recipe != null) {
      return recipe.getTotalBurnTimeTicks();

    } else {
      return DEFAULT_TOTAL_BURN_TIME_TICKS;
    }
  }

  @Override
  protected int getBurnStages() {

    BurnRecipe recipe = Registries.BURN_RECIPE.getValue(this.recipeKey);

    if (recipe != null) {
      return recipe.getBurnStages();

    } else {
      return DEFAULT_BURN_STAGES;
    }
  }

  protected int getMaxFluidLevel() {

    return ModuleCharcoalConfig.REFRACTORY.ACTIVE_PILE_MAX_FLUID_CAPACITY;
  }

  @Override
  protected boolean isActive() {

    return true;
  }

  @Override
  protected void onUpdate() {
    //
  }

  @Override
  protected void onUpdateValid() {
    //
  }

  @Override
  protected void onUpdateInvalid() {

    for (EnumFacing facing : EnumFacing.VALUES) {
      BlockPos offset = this.pos.offset(facing);
      IBlockState blockState = this.world.getBlockState(offset);
      Block block = blockState.getBlock();

      if (block.isAir(blockState, this.world, offset) ||
          block.isReplaceable(this.world, offset)) {
        this.world.setBlockState(offset, Blocks.FIRE.getDefaultState());
      }
    }
  }

  @Override
  protected void onBurnStageComplete() {

    BurnRecipe recipe = Registries.BURN_RECIPE.getValue(this.recipeKey);

    if (recipe == null) {
      return;
    }

    // --- Fill Fluid ---

    FluidStack fluidStack = recipe.getFluidProduced();

    if (fluidStack != null) {
      FluidStack fluidProduced = fluidStack.copy();

      if (fluidProduced.amount > 0) {
        this.fluidTank.fill(fluidProduced, true);
      }
    }

    // --- Push Fluid Down ---

    if (this.fluidTank.getFluidAmount() > 0) {

      for (int i = MAX_FLUID_PUSH_DEPTH; i >= 1; i--) {

        TileEntity tileEntity = this.world.getTileEntity(this.pos.offset(EnumFacing.DOWN, i));

        if (tileEntity instanceof TileActivePile) {
          TileActivePile down = (TileActivePile) tileEntity;
          this.fluidTank.drain(down.fluidTank.fillInternal(this.fluidTank.getFluid(), true), true);

        } else if (tileEntity instanceof TileTarCollector) {
          TileTarCollector down = (TileTarCollector) tileEntity;
          this.fluidTank.drain(down.fluidTank.fillInternal(this.fluidTank.getFluid(), true), true);
        }

        if (this.fluidTank.getFluidAmount() == 0) {
          break;
        }
      }
    }

    // --- Items ---

    ItemStack output = recipe.getOutput();
    float failureChance = recipe.getFailureChance();
    ItemStack[] failureItems = recipe.getFailureItems();

    if (recipe.doesFluidLevelAffectsFailureChance()) {
      failureChance += (1 - failureChance) * (this.fluidTank.getFluidAmount() / (float) this.fluidTank.getCapacity());
    }

    failureChance = MathHelper.clamp(
        failureChance,
        ModuleCharcoalConfig.REFRACTORY.MIN_FAILURE_CHANCE,
        ModuleCharcoalConfig.REFRACTORY.MAX_FAILURE_CHANCE
    );

    if (Util.RANDOM.nextFloat() < failureChance) {

      if (failureItems.length > 0) {
        this.insertItem(failureItems[Util.RANDOM.nextInt(failureItems.length)].copy());
      }

    } else {
      this.insertItem(output.copy());
    }
  }

  private void insertItem(ItemStack output) {

    for (int i = 0; i < 9 && !output.isEmpty(); i++) {
      output = this.output.insertItem(i, output, false);
    }
  }

  @Override
  protected void onAllBurnStagesComplete() {

    List<ItemStack> itemStackList = new ArrayList<>(this.output.getSlots());

    for (int i = 0; i < this.output.getSlots(); i++) {
      ItemStack stackInSlot = this.output.getStackInSlot(i);
      this.output.setStackInSlot(i, ItemStack.EMPTY);

      if (!stackInSlot.isEmpty()) {
        itemStackList.add(stackInSlot);
      }
    }

    IBlockState state = ModuleCharcoal.Blocks.PIT_ASH_BLOCK.getDefaultState();
    this.world.setBlockState(this.pos, state);
    TileEntity tileEntity = this.world.getTileEntity(this.pos);

    if (tileEntity instanceof TilePitAsh) {
      TilePitAsh tilePitAsh = (TilePitAsh) tileEntity;

      for (ItemStack itemStack : itemStackList) {
        tilePitAsh.insertItem(itemStack);
      }
    }
  }

  @Override
  protected void onInvalidDelayExpired() {

    this.world.setBlockToAir(this.pos);
  }

  @Override
  protected boolean isValidStructureBlock(World world, BlockPos pos, IBlockState blockState, EnumFacing facing) {

    blockState = blockState.getActualState(world, pos);
    BurnRecipe recipe = Registries.BURN_RECIPE.getValue(this.recipeKey);

    if (recipe == null) {
      return false;
    }

    for (Predicate<IBlockState> matcher : Registries.COKE_OVEN_VALID_STRUCTURE_BLOCK_LIST) {

      if (matcher.test(blockState)) {
        return true;
      }
    }

    for (Predicate<IBlockState> matcher : Registries.REFRACTORY_BLOCK_LIST) {

      if (matcher.test(blockState)) {
        return true;
      }
    }

    if (this.isValidRefractoryDoor(blockState, facing)) {
      return true;
    }

    if (recipe.requiresRefractoryBlocks()) {
      return false;

    } else {
      return super.isValidStructureBlock(world, pos, blockState, facing);
    }
  }

  private boolean isValidRefractoryDoor(IBlockState blockState, EnumFacing facing) {

    if (blockState.getBlock() == ModuleCharcoal.Blocks.REFRACTORY_DOOR) {

      if (!blockState.getValue(BlockRefractoryDoor.OPEN)
          && blockState.getValue(BlockRefractoryDoor.FACING) == facing) {
        return true;

      } else if (blockState.getValue(BlockRefractoryDoor.OPEN)
          && blockState.getValue(BlockRefractoryDoor.HINGE) == BlockDoor.EnumHingePosition.LEFT
          && blockState.getValue(BlockRefractoryDoor.FACING) == facing.rotateYCCW()) {
        return true;

      } else if (blockState.getValue(BlockRefractoryDoor.OPEN)
          && blockState.getValue(BlockRefractoryDoor.HINGE) == BlockDoor.EnumHingePosition.RIGHT
          && blockState.getValue(BlockRefractoryDoor.FACING) == facing.rotateY()) {
        return true;
      }
    }
    return false;
  }

  @Nonnull
  @Override
  public NBTTagCompound writeToNBT(NBTTagCompound compound) {

    super.writeToNBT(compound);

    compound.setTag("fluidTank", this.fluidTank.writeToNBT(new NBTTagCompound()));
    compound.setString("recipeKey", this.recipeKey.toString());
    compound.setTag("output", this.output.serializeNBT());
    return compound;
  }

  @Override
  public void readFromNBT(NBTTagCompound compound) {

    super.readFromNBT(compound);

    this.fluidTank.readFromNBT(compound.getCompoundTag("fluidTank"));
    this.recipeKey = new ResourceLocation(compound.getString("recipeKey"));
    this.output.deserializeNBT(compound.getCompoundTag("output"));
  }

}
