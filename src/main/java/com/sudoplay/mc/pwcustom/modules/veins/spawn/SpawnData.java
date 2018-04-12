package com.sudoplay.mc.pwcustom.modules.veins.spawn;

import com.sudoplay.mc.pwcustom.modules.veins.util.LongObjectMap;
import com.sudoplay.mc.pwcustom.modules.veins.util.MathUtil;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.INBTSerializable;

public class SpawnData
    implements INBTSerializable<NBTTagList> {

  private LongObjectMap<BlockPos> map;

  public SpawnData() {

    this.map = new LongObjectMap<>(8);
  }

  private long getKeyFromBlock(int x, int z) {

    return this.getKeyFromChunk(x >> 4, z >> 4);
  }

  private long getKeyFromChunk(int chunkX, int chunkZ) {

    return ((long) chunkX << 32) | (chunkZ & 0xffffffffL);
  }

  public BlockPos getFromChunk(int chunkX, int chunkZ) {

    long key = this.getKeyFromChunk(chunkX, chunkZ);
    return this.map.get(key);
  }

  public void set(BlockPos pos) {

    this.set(pos.getX(), pos.getY(), pos.getZ());
  }

  public void set(int blockX, int blockY, int blockZ) {

    long key = this.getKeyFromBlock(blockX, blockZ);
    this.map.put(key, new BlockPos(blockX, blockY, blockZ));
  }

  public void removeChunk(int chunkX, int chunkZ) {

    long key = this.getKeyFromChunk(chunkX, chunkZ);
    this.map.remove(key);
  }

  public boolean containsChunk(int chunkX, int chunkZ) {

    long key = this.getKeyFromChunk(chunkX, chunkZ);
    return this.map.containsKey(key);
  }

  public boolean containsChunkInRadius(int radius, int chunkX, int chunkZ, boolean includeOriginChunk) {

    int radiusSquared = radius * radius;
    int chunkCount = (4 * radius) * (radius + 1) + 1;

    if (chunkCount < this.map.size()) {
      // The map is larger than the chunk area.
      for (int x = chunkX - radius; x <= chunkX + radius; x++) {

        for (int z = chunkZ - radius; z <= chunkZ + radius; z++) {

          if (!includeOriginChunk && x == chunkX && z == chunkZ) {
            continue;
          }

          if (this.containsChunk(x, z)
              && MathUtil.distanceSquared(chunkX, chunkZ, x, z) <= radiusSquared) {
            return true;
          }
        }
      }

    } else {
      // The map is smaller than the chunk area.
      long[] keys = this.map.keys();

      for (int i = 0; i < this.map.size(); i++) {
        long key = keys[i];
        int x = (int) (key >> 32);
        int z = (int) key;

        if (!includeOriginChunk && x == chunkX && z == chunkZ) {
          continue;
        }

        int distanceSquared = (int) MathUtil.distanceSquared(chunkX, chunkZ, x, z);

        if (distanceSquared <= radiusSquared) {
          return true;
        }
      }
    }

    return false;
  }

  public void removeInRadius(int radius, int chunkX, int chunkZ, boolean includeOriginChunk) {

    int radiusSquared = radius * radius;
    int chunkCount = (4 * radius) * (radius + 1) + 1;

    if (chunkCount < this.map.size()) {
      // The map is larger than the chunk area.
      for (int x = chunkX - radius; x <= chunkX + radius; x++) {

        for (int z = chunkZ - radius; z <= chunkZ + radius; z++) {

          if (!includeOriginChunk && x == chunkX && z == chunkZ) {
            continue;
          }

          int distanceSquared = (int) MathUtil.distanceSquared(chunkX, chunkZ, x, z);

          if (distanceSquared <= radiusSquared) {
            this.map.remove(this.getKeyFromChunk(x, z));
          }
        }
      }

    } else {
      // The map is smaller than the chunk area.
      long[] keys = this.map.keys();
      long[] toRemove = new long[this.map.size()];
      int removeIndex = 0;

      for (int i = 0; i < this.map.size(); i++) {
        long key = keys[i];
        int x = (int) (key >> 32);
        int z = (int) key;

        if (!includeOriginChunk && x == chunkX && z == chunkZ) {
          continue;
        }

        int distanceSquared = (int) MathUtil.distanceSquared(chunkX, chunkZ, x, z);

        if (distanceSquared <= radiusSquared) {
          toRemove[removeIndex] = this.getKeyFromChunk(x, z);
          removeIndex += 1;
        }
      }

      for (int i = 0; i < removeIndex; i++) {
        this.map.remove(toRemove[i]);
      }
    }

  }

  @Override
  public NBTTagList serializeNBT() {

    NBTTagList tag = new NBTTagList();

    int size = this.map.size();
    long[] keys = this.map.keys();

    for (int i = 0; i < size; i++) {
      NBTTagCompound compound = new NBTTagCompound();
      compound.setLong("chunk", keys[i]);
      compound.setTag("pos", NBTUtil.createPosTag(this.map.get(keys[i])));
      tag.appendTag(compound);
    }

    return tag;
  }

  @Override
  public void deserializeNBT(NBTTagList tag) {

    int count = tag.tagCount();

    for (int i = 0; i < count; i++) {
      NBTTagCompound compound = (NBTTagCompound) tag.get(i);
      long key = compound.getLong("chunk");
      BlockPos pos = NBTUtil.getPosFromTag(compound.getCompoundTag("pos"));
      this.map.put(key, pos);
    }
  }
}
