package com.sudoplay.mc.pwcustom.modules.world;

import com.sudoplay.mc.pwcustom.modules.world.spawn.IWorldSavedData;
import com.sudoplay.mc.pwcustom.modules.world.spawn.SpawnData;
import com.sudoplay.mc.pwcustom.modules.world.spawn.WorldSavedData;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockStateMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import java.util.Random;

public class MapGenLargeVein {

  private String groupName;
  private int groupRange;
  private float chance;
  private final long seed;
  private final BlockStateMatcher matcher;
  private final IBlockState blockState;
  private final int genCheckRange;
  private final Random rand;
  private final int minGenHeight;
  private final int maxGenHeight;
  private final float sizeScalar;

  public MapGenLargeVein(
      String groupName,
      int groupRange,
      float chance,
      long seed,
      int minGenHeight,
      int maxGenHeight,
      float sizeScalar, BlockStateMatcher matcher,
      IBlockState blockState
  ) {

    this.groupName = ModuleWorld.MOD_ID + ":MapGenLargeVeinGroup:" + groupName;
    this.groupRange = groupRange;
    this.chance = chance;
    this.seed = seed;
    this.minGenHeight = minGenHeight;
    this.maxGenHeight = maxGenHeight;
    this.sizeScalar = sizeScalar;
    this.matcher = matcher;
    this.blockState = blockState;

    this.genCheckRange = 8;
    this.rand = new Random();

  }

  public void generate(World world, int chunkX, int chunkZ, Chunk chunk) {

    IWorldSavedData data = WorldSavedData.forWorld(world);
    SpawnData spawnData = data.getSpawnData(this.groupName);

    this.rand.setSeed(world.getSeed());
    long j = this.rand.nextLong();
    long k = this.rand.nextLong();

    for (int x = chunkX - this.genCheckRange; x <= chunkX + this.genCheckRange; ++x) {

      for (int z = chunkZ - this.genCheckRange; z <= chunkZ + this.genCheckRange; ++z) {

        if (spawnData.containsChunkInRadius(this.groupRange, x, z, true)) {
          continue;
        }

        long j1 = (long) x * j;
        long k1 = (long) z * k;
        this.rand.setSeed(j1 ^ k1 ^ world.getSeed() ^ this.seed);
        this.recursiveGenerate(world, x, z, chunkX, chunkZ, chunk, spawnData);
      }
    }
  }

  private void recursiveGenerate(
      World world,
      int chunkX,
      int chunkZ,
      int originX,
      int originZ,
      Chunk chunk,
      SpawnData spawnData
  ) {

    int cavesToGenerate = 1;

    if (this.rand.nextFloat() > this.chance) {
      return;
    }

    ChunkPos pos = chunk.getPos();
    spawnData.set(pos.x << 4, 0, pos.z << 4);

    int rangeY = Math.max(this.maxGenHeight - this.minGenHeight, 1);

    for (int i = 0; i < cavesToGenerate; ++i) {

      double blockX = (double) (chunkX * 16 + this.rand.nextInt(16));
      double blockY = this.rand.nextInt(rangeY) + this.minGenHeight + 16;
      double blockZ = (double) (chunkZ * 16 + this.rand.nextInt(16));

      int tunnelsToGenerate = 1;

      for (int j = 0; j < tunnelsToGenerate; ++j) {
        float randomPI = this.rand.nextFloat() * ((float) Math.PI * 2F);
        float angleToGenerate = (this.rand.nextFloat() - 0.5F) * 2.0F / 8.0F;
        float caveSize = this.rand.nextFloat() * 2.0F + this.rand.nextFloat();
        caveSize *= this.sizeScalar;

        this.addTunnel(
            this.rand.nextLong(),
            originX,
            originZ,
            chunk,
            blockX,
            blockY,
            blockZ,
            caveSize,
            randomPI,
            angleToGenerate,
            0,
            0,
            1.0D
        );
      }
    }
  }

  private void addTunnel(
      long seed,
      int originalX,
      int originalZ,
      Chunk chunk,
      double randomX,
      double randomY,
      double randomZ,
      float caveSize,
      float randomPI,
      float angleToGenerate,
      int loopOne,
      int loopEnd,
      double yScale
  ) {

    if (randomY <= 10) {
      return;
    }

    double centerX = (double) (originalX * 16 + 8);
    double centerZ = (double) (originalZ * 16 + 8);
    float f = 0.0F;
    float f1 = 0.0F;
    Random random = new Random(seed);

    if (loopEnd <= 0) {
      int i = this.genCheckRange * 16 - 16;
      loopEnd = i - random.nextInt(i / 4);
    }

    boolean shouldStop = false;

    if (loopOne == -1) {
      loopOne = loopEnd / 2;
      shouldStop = true;
    }

    int j = random.nextInt(loopEnd / 2) + loopEnd / 4;

    for (boolean flag = random.nextInt(6) == 0; loopOne < loopEnd; ++loopOne) {
      double d2 = 1.5D + (double) (MathHelper.sin((float) loopOne * (float) Math.PI / (float) loopEnd) * caveSize);
      double d3 = d2 * yScale;

      float f2 = MathHelper.cos(angleToGenerate);
      float f3 = MathHelper.sin(angleToGenerate);

      randomX += (double) (MathHelper.cos(randomPI) * f2);
      randomY += (double) f3;
      randomZ += (double) (MathHelper.sin(randomPI) * f2);

      randomY = MathHelper.clamp(randomY, this.minGenHeight, this.maxGenHeight);

      if (flag) {
        angleToGenerate = angleToGenerate * 0.92F;

      } else {
        angleToGenerate = angleToGenerate * 0.7F;
      }

      angleToGenerate = angleToGenerate + f1 * 0.1F;
      randomPI += f * 0.1F;

      f1 = f1 * 0.9F;
      f1 = f1 + (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0F;

      f = f * 0.75F;
      f = f + (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4.0F;

      if (!shouldStop && loopOne == j && caveSize > 1.0F && loopEnd > 0) {
        this.addTunnel(
            random.nextLong(),
            originalX, originalZ,
            chunk,
            randomX, randomY, randomZ,
            random.nextFloat() * 0.5F + 0.5F,
            randomPI - ((float) Math.PI / 2F),
            angleToGenerate / 3.0F,
            loopOne,
            loopEnd,
            1.0D
        );
        this.addTunnel(
            random.nextLong(),
            originalX, originalZ,
            chunk,
            randomX, randomY, randomZ,
            random.nextFloat() * 0.5F + 0.5F,
            randomPI + ((float) Math.PI / 2F),
            angleToGenerate / 3.0F,
            loopOne,
            loopEnd,
            1.0D
        );
        return;
      }

      if (shouldStop || random.nextInt(4) != 0) {
        double d4 = randomX - centerX;
        double d5 = randomZ - centerZ;
        double d6 = (double) (loopEnd - loopOne);
        double d7 = (double) (caveSize + 2.0F + 16.0F);

        if (d4 * d4 + d5 * d5 - d6 * d6 > d7 * d7) {
          return;
        }

        if (randomX >= centerX - 16.0D - d2 * 2.0D
            && randomZ >= centerZ - 16.0D - d2 * 2.0D
            && randomX <= centerX + 16.0D + d2 * 2.0D
            && randomZ <= centerZ + 16.0D + d2 * 2.0D) {

          int xMin = MathHelper.floor(randomX - d2) - originalX * 16 - 1;
          int xMax = MathHelper.floor(randomX + d2) - originalX * 16 + 1;
          int yMin = MathHelper.floor(randomY - d3) - 1;
          int yMax = MathHelper.floor(randomY + d3) + 1;
          int zMin = MathHelper.floor(randomZ - d2) - originalZ * 16 - 1;
          int zMax = MathHelper.floor(randomZ + d2) - originalZ * 16 + 1;

          if (xMin < 0) {
            xMin = 0;
          }

          if (xMax > 16) {
            xMax = 16;
          }

          if (yMin < 1) {
            yMin = 1;
          }

          if (yMax > 248) {
            yMax = 248;
          }

          if (zMin < 0) {
            zMin = 0;
          }

          if (zMax > 16) {
            zMax = 16;
          }

          boolean foundOcean = false;

          for (int x = xMin; !foundOcean && x < xMax; ++x) {
            for (int z = zMin; !foundOcean && z < zMax; ++z) {
              for (int y = yMax + 1; !foundOcean && y >= yMin - 1; --y) {

                if (y >= 0 && y < 256) {

                  if (this.isOceanBlock(chunk, x, y, z)) {
                    foundOcean = true;
                  }

                  if (y != yMin - 1 && x != xMin && x != xMax - 1 && z != zMin && z != zMax - 1) {
                    y = yMin;
                  }
                }
              }
            }
          }

          if (!foundOcean) {

            for (int x = xMin; x < xMax; ++x) {
              double d10 = ((double) (x + originalX * 16) + 0.5D - randomX) / d2;

              for (int z = zMin; z < zMax; ++z) {
                double d8 = ((double) (z + originalZ * 16) + 0.5D - randomZ) / d2;

                if (d10 * d10 + d8 * d8 < 1.0D) {

                  for (int y = yMax; y > yMin; --y) {
                    double d9 = ((double) (y - 1) + 0.5D - randomY) / d3;

                    if (d9 > -0.7D && d10 * d10 + d9 * d9 + d8 * d8 < 1.0D) {
                      this.digBlock(chunk, x, y, z);
                    }
                  }
                }
              }
            }

            if (shouldStop) {
              break;
            }
          }
        }
      }
    }
  }

  protected boolean isOceanBlock(Chunk chunk, int x, int y, int z) {

    net.minecraft.block.Block block = chunk.getBlockState(x, y, z).getBlock();
    return block == Blocks.FLOWING_LAVA || block == Blocks.LAVA;
  }

  protected void digBlock(
      Chunk chunk,
      int x,
      int y,
      int z
  ) {

    if (this.matcher.apply(chunk.getBlockState(x, y, z))) {
      chunk.setBlockState(new BlockPos(x, y, z), this.blockState);
    }
  }

}
