package com.sudoplay.mc.pwcustom.modules.veins.util;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;

import java.util.function.Predicate;

public class ChunkDigger {

  private Chunk chunk;

  private int xMin;
  private int xMax;
  private int zMin;
  private int zMax;

  public ChunkDigger(Chunk chunk, int chunkX, int chunkZ) {

    this.chunk = chunk;
    this.xMin = chunkX << 4;
    this.xMax = this.xMin + 15;
    this.zMin = chunkZ << 4;
    this.zMax = this.zMin + 15;
  }

  public void setBlock(int x, int y, int z, IBlockState blockState) {

    if (y < 1 || y > 255) {
      return;
    }

    if (x >= this.xMin
        && x <= this.xMax
        && z >= this.zMin
        && z <= this.zMax) {

      int adjustedX = ((x % 16) + 16) % 16;
      int adjustedZ = ((z % 16) + 16) % 16;

      this.chunk.setBlockState(new BlockPos(adjustedX, y, adjustedZ), blockState);
    }
  }

  public void replaceBlock(int x, int y, int z, IBlockState replaceWith, Predicate<IBlockState> toReplace) {

    if (y < 1 || y > 255) {
      return;
    }

    if (x >= this.xMin
        && x <= this.xMax
        && z >= this.zMin
        && z <= this.zMax) {

      int adjustedX = ((x % 16) + 16) % 16;
      int adjustedZ = ((z % 16) + 16) % 16;

      IBlockState currentBlockState = this.chunk.getBlockState(adjustedX, y, adjustedZ);

      if (toReplace.test(currentBlockState)) {
        this.chunk.setBlockState(new BlockPos(adjustedX, y, adjustedZ), replaceWith);
      }

    }
  }

  public void setInRadius(int radius, int x, int y, int z, IBlockState blockState) {

    for (int xx = x - radius; xx <= x + radius; xx++) {
      for (int zz = z - radius; zz <= z + radius; zz++) {
        for (int yy = y - radius; yy <= y + radius; yy++) {
          if (MathUtil.distanceSquared(xx, yy, zz, x, y, z) <= radius * radius) {
            this.setBlock(xx, yy, zz, blockState);
          }
        }
      }
    }
  }

  public void replaceInRadius(
      int radius,
      int x,
      int y,
      int z,
      IBlockState replaceWith,
      Predicate<IBlockState> toReplace
  ) {

    for (int xx = x - radius; xx <= x + radius; xx++) {
      for (int zz = z - radius; zz <= z + radius; zz++) {
        for (int yy = y - radius; yy <= y + radius; yy++) {
          if (MathUtil.distanceSquared(xx, yy, zz, x, y, z) <= radius * radius) {
            this.replaceBlock(xx, yy, zz, replaceWith, toReplace);
          }
        }
      }
    }
  }

  public void setInRadiusInterpolate(
      int steps,
      float radius1,
      int x1,
      int y1,
      int z1,
      float radius2,
      int x2,
      int y2,
      int z2,
      IBlockState blockState
  ) {

    float sx = (x2 - x1) / (float) steps;
    float sy = (y2 - y1) / (float) steps;
    float sz = (z2 - z1) / (float) steps;
    float sr = (radius2 - radius1) / (float) steps;

    for (int i = 0; i < steps; i++) {
      this.setInRadius(
          (int) (radius1 + sr * i),
          (int) (x1 + sx * i),
          (int) (y1 + sy * i),
          (int) (z1 + sz * i),
          blockState
      );
    }

  }

  public void replaceInRadiusInterpolate(
      int steps,
      float radius1,
      int x1,
      int y1,
      int z1,
      float radius2,
      int x2,
      int y2,
      int z2,
      IBlockState replaceWith,
      Predicate<IBlockState> toReplace
  ) {

    float sx = (x2 - x1) / (float) steps;
    float sy = (y2 - y1) / (float) steps;
    float sz = (z2 - z1) / (float) steps;
    float sr = (radius2 - radius1) / (float) steps;

    for (int i = 0; i < steps; i++) {
      this.replaceInRadius(
          (int) (radius1 + sr * i),
          (int) (x1 + sx * i),
          (int) (y1 + sy * i),
          (int) (z1 + sz * i),
          replaceWith,
          toReplace
      );
    }

  }

  public Chunk getChunk() {

    return this.chunk;
  }
}
