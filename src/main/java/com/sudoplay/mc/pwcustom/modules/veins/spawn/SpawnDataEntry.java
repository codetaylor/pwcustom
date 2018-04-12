package com.sudoplay.mc.pwcustom.modules.veins.spawn;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.INBTSerializable;

public class SpawnDataEntry
    implements INBTSerializable<NBTTagCompound> {

  private BlockPos pos;
  private NBTTagCompound data;

  public SpawnDataEntry() {
    // serialization
  }

  public SpawnDataEntry(BlockPos pos, NBTTagCompound data) {

    this.pos = pos;
    this.data = data;
  }

  @Override
  public NBTTagCompound serializeNBT() {

    NBTTagCompound tag = new NBTTagCompound();
    tag.setTag("pos", NBTUtil.createPosTag(this.pos));
    tag.setTag("data", this.data);
    return tag;
  }

  @Override
  public void deserializeNBT(NBTTagCompound tag) {

    this.pos = NBTUtil.getPosFromTag(tag.getCompoundTag("pos"));
    this.data = tag.getCompoundTag("data");
  }
}
