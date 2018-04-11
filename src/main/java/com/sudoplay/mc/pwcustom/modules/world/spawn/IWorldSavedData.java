package com.sudoplay.mc.pwcustom.modules.world.spawn;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

public interface IWorldSavedData
    extends INBTSerializable<NBTTagCompound> {

  boolean getFlag(String name);

  void setFlag(String name, boolean value);

  SpawnData getSpawnData(String name);

  SpawnData getTransientSpawnData(String name);
}
