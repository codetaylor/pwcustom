package com.sudoplay.mc.pwcustom.modules.charcoal.tile;

import com.codetaylor.mc.athenaeum.util.BlockHelper;
import com.sudoplay.mc.pwcustom.modules.charcoal.ModuleCharcoal;
import com.sudoplay.mc.pwcustom.modules.charcoal.block.BlockKiln;
import com.sudoplay.mc.pwcustom.modules.charcoal.recipe.KilnRecipe;
import com.sudoplay.mc.pwcustom.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileKiln
    extends TileBurnableBase
    implements ITickable,
    IProgressProvider {

  private ItemStackHandler stackHandler;
  private ItemStackHandler outputStackHandler;
  private boolean active;

  // transient
  private EntityItem entityItem;
  private int ticksSinceLastClientSync;

  public TileKiln() {

    this.stackHandler = new ItemStackHandler(1) {

      @Override
      protected int getStackLimit(int slot, @Nonnull ItemStack stack) {

        return 8;
      }

      @Override
      protected void onContentsChanged(int slot) {

        TileKiln.this.entityItem = null;
      }
    };

    this.outputStackHandler = new ItemStackHandler(9);

    this.setNeedStructureValidation();
  }

  public ItemStackHandler getStackHandler() {

    return this.stackHandler;
  }

  public ItemStackHandler getOutputStackHandler() {

    return this.outputStackHandler;
  }

  public void setActive(boolean active) {

    this.active = active;
  }

  public EntityItem getEntityItem() {

    if (this.entityItem == null) {
      ItemStack stackInSlot = this.stackHandler.getStackInSlot(0);
      this.entityItem = new EntityItem(this.world);
      this.entityItem.setItem(stackInSlot);
    }

    return this.entityItem;
  }

  @Override
  public float getProgress() {

    if (!this.isActive()) {
      return 0;
    }

    int totalBurnTimeTicks = this.getTotalBurnTimeTicks();
    int totalStages = this.getTotalStages();
    int burnTimePerStage = totalBurnTimeTicks / totalStages;
    float progress = ((this.remainingStages - 1) * burnTimePerStage + this.burnTimeTicksPerStage) / (float) totalBurnTimeTicks;

    return 1f - progress;
  }

  @Override
  protected boolean isActive() {

    return this.active;
  }

  @Override
  protected void onUpdate() {

    this.ticksSinceLastClientSync += 1;

    if (this.ticksSinceLastClientSync >= 20) {
      this.ticksSinceLastClientSync = 0;
      BlockHelper.notifyBlockUpdate(this.world, this.pos);
    }

    if (this.world.isRainingAt(this.pos)) {
      // set back to wood state and douse fire
      IBlockState blockState = ModuleCharcoal.Blocks.KILN.getDefaultState()
          .withProperty(BlockKiln.VARIANT, BlockKiln.EnumType.WOOD);
      this.world.setBlockState(this.pos, blockState);

      BlockPos up = this.pos.up();

      if (this.world.getBlockState(up).getBlock() == Blocks.FIRE) {
        this.world.setBlockToAir(up);
      }

      this.setActive(false);
    }
  }

  @Override
  protected void onUpdateValid() {

    // set the block above to fire if the kiln is active

    BlockPos up = this.pos.up();
    IBlockState blockState = this.world.getBlockState(up);
    Block block = blockState.getBlock();

    if (block != Blocks.FIRE) {

      if (block.isAir(blockState, this.world, up)
          || block.isReplaceable(this.world, up)) {
        this.world.setBlockState(up, Blocks.FIRE.getDefaultState());
      }
    }
  }

  @Override
  protected void onUpdateInvalid() {

    // reset the burn timer
    this.reset();
  }

  @Override
  protected void onInvalidDelayExpired() {

    // set blockstate to complete
    // add failure items or ash
    // clear fire block above if it exists

    ItemStack input = this.stackHandler.getStackInSlot(0);
    KilnRecipe recipe = KilnRecipe.getRecipe(input);

    if (!input.isEmpty()
        && recipe != null) {
      ItemStack[] failureItems = recipe.getFailureItems();

      if (failureItems.length > 0) {

        for (int i = 0; i < input.getCount(); i++) {
          ItemStack failureItemStack = failureItems[Util.RANDOM.nextInt(failureItems.length)].copy();
          failureItemStack.setCount(1);
          this.insertOutputItem(failureItemStack);
        }

      } else {
        this.insertOutputItem(new ItemStack(ModuleCharcoal.Items.WOOD_ASH, input.getCount(), 0));
      }

      ItemStack output = recipe.getOutput();
      output.setCount(input.getCount());
      this.stackHandler.setStackInSlot(0, ItemStack.EMPTY);
    }

    this.setActive(false);
    IBlockState blockState = ModuleCharcoal.Blocks.KILN.getDefaultState()
        .withProperty(BlockKiln.VARIANT, BlockKiln.EnumType.COMPLETE);
    this.world.setBlockState(this.pos, blockState);
    this.world.setBlockToAir(this.pos.up());
  }

  @Override
  protected void onBurnStageComplete() {
    //
  }

  @Override
  protected boolean isStructureValid() {

    IBlockState selfBlockState = this.world.getBlockState(this.pos);

    if (selfBlockState.getValue(BlockKiln.VARIANT) != BlockKiln.EnumType.WOOD
        && selfBlockState.getValue(BlockKiln.VARIANT) != BlockKiln.EnumType.ACTIVE) {
      return false;
    }

    BlockPos up = this.pos.up();
    IBlockState upBlockState = this.world.getBlockState(up);
    Block upBlock = upBlockState.getBlock();

    if (!upBlock.isAir(upBlockState, this.world, up)
        && !upBlock.isReplaceable(this.world, up)
        && upBlock != Blocks.FIRE) {
      return false;
    }

    for (EnumFacing facing : EnumFacing.HORIZONTALS) {

      BlockPos offset = this.pos.offset(facing);

      if (!this.isValidStructureBlock(this.world, offset, this.world.getBlockState(offset), facing.getOpposite())) {
        return false;
      }
    }

    return this.isValidStructureBlock(
        this.world,
        this.pos.down(),
        this.world.getBlockState(this.pos.down()),
        EnumFacing.UP
    );
  }

  @Override
  protected void onAllBurnStagesComplete() {

    // replace kiln block with complete variant
    // set stack handler items to recipe result

    ItemStack input = this.stackHandler.getStackInSlot(0);
    KilnRecipe recipe = KilnRecipe.getRecipe(input);

    if (recipe != null) {
      ItemStack output = recipe.getOutput();
      output.setCount(input.getCount());
      this.stackHandler.setStackInSlot(0, ItemStack.EMPTY);

      // TODO: broken stuff, less broken stuff if surrounded by refractory brick
      this.insertOutputItem(output);
    }

    this.setActive(false);
    IBlockState blockState = ModuleCharcoal.Blocks.KILN.getDefaultState()
        .withProperty(BlockKiln.VARIANT, BlockKiln.EnumType.COMPLETE);
    this.world.setBlockState(this.pos, blockState);
    this.world.setBlockToAir(this.pos.up());
  }

  private void insertOutputItem(ItemStack output) {

    for (int i = 0; i < 9 && !output.isEmpty(); i++) {
      output = this.outputStackHandler.insertItem(i, output, false);
    }
  }

  @Override
  protected int getTotalBurnTimeTicks() {

    return 1000;
  }

  @Override
  protected int getTotalStages() {

    return 1;
  }

  @Override
  public boolean shouldRefresh(
      World world,
      BlockPos pos,
      @Nonnull IBlockState oldState,
      @Nonnull IBlockState newState
  ) {

    if (oldState.getBlock() == newState.getBlock()) {
      return false;
    }

    return super.shouldRefresh(world, pos, oldState, newState);
  }

  @Nonnull
  @Override
  public NBTTagCompound writeToNBT(NBTTagCompound compound) {

    super.writeToNBT(compound);
    compound.setTag("stackHandler", this.stackHandler.serializeNBT());
    compound.setTag("outputStackHandler", this.outputStackHandler.serializeNBT());
    compound.setBoolean("active", this.active);
    return compound;
  }

  @Override
  public void readFromNBT(NBTTagCompound compound) {

    super.readFromNBT(compound);
    this.stackHandler.deserializeNBT(compound.getCompoundTag("stackHandler"));
    this.outputStackHandler.deserializeNBT(compound.getCompoundTag("outputStackHandler"));
    this.active = compound.getBoolean("active");
  }

  @Nonnull
  @Override
  public NBTTagCompound getUpdateTag() {

    return this.writeToNBT(new NBTTagCompound());
  }

  @Nullable
  @Override
  public SPacketUpdateTileEntity getUpdatePacket() {

    return new SPacketUpdateTileEntity(this.pos, -1, this.getUpdateTag());
  }

  @Override
  public void onDataPacket(NetworkManager networkManager, SPacketUpdateTileEntity packet) {

    this.readFromNBT(packet.getNbtCompound());
  }
}
