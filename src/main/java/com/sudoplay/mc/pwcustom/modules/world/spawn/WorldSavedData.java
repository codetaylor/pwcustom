package com.sudoplay.mc.pwcustom.modules.world.spawn;

import com.sudoplay.mc.pwcustom.ModPWCustom;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraftforge.common.util.Constants;

import java.util.HashMap;
import java.util.Map;

public class WorldSavedData
    extends net.minecraft.world.storage.WorldSavedData
    implements IWorldSavedData {

  /*

  {
    "spawnData": {
      "<id>": [
        {
          "chunk": long,
          "pos": BlockPos
        }
      ]
    }
  }

   */

  public static final String KEY = ModPWCustom.MOD_ID;

  private Map<String, SpawnData> spawnData;
  private Map<String, SpawnData> transientSpawnData;
  private NBTTagCompound flags;

  public WorldSavedData() {

    super(KEY);
    this.spawnData = new HashMap<>();
    this.transientSpawnData = new HashMap<>();
    this.flags = new NBTTagCompound();
  }

  public static IWorldSavedData forWorld(World world) {

    MapStorage storage = world.getPerWorldStorage();
    WorldSavedData data = (WorldSavedData) storage
        .getOrLoadData(WorldSavedData.class, KEY);

    if (data == null) {
      data = new WorldSavedData();
      storage.setData(KEY, data);
    }

    return data;
  }

  @Override
  public boolean getFlag(String name) {

    return this.flags.getBoolean(name);
  }

  @Override
  public void setFlag(String name, boolean value) {

    this.flags.setBoolean(name, value);
  }

  @Override
  public SpawnData getSpawnData(String name) {

    SpawnData spawnData = this.spawnData.get(name);

    if (spawnData == null) {
      spawnData = new SpawnData();
      this.spawnData.put(name, spawnData);
    }

    return spawnData;
  }

  @Override
  public SpawnData getTransientSpawnData(String name) {

    SpawnData spawnData = this.transientSpawnData.get(name);

    if (spawnData == null) {
      spawnData = new SpawnData();
      this.transientSpawnData.put(name, spawnData);
    }

    return spawnData;
  }

  @Override
  public void readFromNBT(NBTTagCompound in) {

    NBTTagCompound tagSpawnData = in.getCompoundTag("spawnData");

    for (String key : tagSpawnData.getKeySet()) {
      SpawnData spawnData = new SpawnData();
      spawnData.deserializeNBT(tagSpawnData.getTagList(key, Constants.NBT.TAG_COMPOUND));
      this.spawnData.put(key, spawnData);
    }

    this.flags = in.getCompoundTag("flags");
  }

  @Override
  public NBTTagCompound writeToNBT(NBTTagCompound out) {

    NBTTagCompound tagSpawnData = new NBTTagCompound();

    for (String key : this.spawnData.keySet()) {
      tagSpawnData.setTag(key, this.spawnData.get(key).serializeNBT());
    }

    out.setTag("spawnData", tagSpawnData);
    out.setTag("flags", this.flags);

    return out;
  }

}
