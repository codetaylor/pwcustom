package com.sudoplay.mc.pwcustom.modules.veins.world;

import com.sudoplay.mc.pwcustom.modules.veins.ModuleVeins;
import com.sudoplay.mc.pwcustom.modules.veins.data.VeinData;
import com.sudoplay.mc.pwcustom.modules.veins.data.VeinDataSelector;
import com.sudoplay.mc.pwcustom.modules.veins.spawn.IWorldSavedData;
import com.sudoplay.mc.pwcustom.modules.veins.spawn.SpawnData;
import com.sudoplay.mc.pwcustom.modules.veins.spawn.WorldSavedData;
import com.sudoplay.mc.pwcustom.modules.veins.util.ChunkDigger;
import com.sudoplay.mc.pwcustom.modules.veins.util.MathUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

public class WorldGenLargeVein
    implements IWorldGenerator {

  private static final String SPAWN_DATA_NAME = ModuleVeins.MOD_ID + ":vein";

  private final int genCheckRange;
  private final Random rand;
  private final VeinDataSelector selector;

  public WorldGenLargeVein(VeinDataSelector selector, Random random) {

    this.selector = selector;

    this.genCheckRange = 8;
    this.rand = random;
  }

  @Override
  public void generate(
      Random random,
      int chunkX,
      int chunkZ,
      World world,
      IChunkGenerator chunkGenerator,
      IChunkProvider chunkProvider
  ) {

    Chunk chunk = world.getChunkFromChunkCoords(chunkX, chunkZ);
    IWorldSavedData data = WorldSavedData.forWorld(world);
    SpawnData spawnData = data.getSpawnData(SPAWN_DATA_NAME);
    ChunkDigger chunkDigger = new ChunkDigger(chunk, chunkX, chunkZ);

    this.rand.setSeed(world.getSeed());
    long j = this.rand.nextLong();
    long k = this.rand.nextLong();

    for (int x = chunkX - this.genCheckRange; x <= chunkX + this.genCheckRange; ++x) {

      for (int z = chunkZ - this.genCheckRange; z <= chunkZ + this.genCheckRange; ++z) {

        long j1 = (long) x * j;
        long k1 = (long) z * k;
        this.rand.setSeed(j1 ^ k1 ^ world.getSeed() ^ 51647654L);

        int dimension = world.provider.getDimension();
        Biome biome = world.getBiome(new BlockPos(x << 4, 0, z << 4));
        ResourceLocation biomeRegistryName = biome.getRegistryName();

        VeinData veinData = this.selector.selectVeinData(dimension, biomeRegistryName);

        if (veinData == null) {
          continue;
        }

        if (!spawnData.containsChunk(x, z)
            && spawnData.containsChunkInRadius(veinData.range, x, z, false)) {
          continue;
        }

        this.generate(world, chunkX, chunkZ, x, z, veinData, spawnData, chunkDigger);
      }
    }
  }

  private void generate(
      World world,
      int originX,
      int originZ,
      int chunkX,
      int chunkZ,
      VeinData veinData,
      SpawnData spawnData,
      ChunkDigger chunkDigger
  ) {

    // chance to generate
    if (this.rand.nextFloat() > 0.5f) {
      return;
    }

    int rangeY = Math.max(veinData.maxGenHeight - veinData.minGenHeight, 1);

    int blockX = chunkX * 16 + this.rand.nextInt(16);
    int blockY = this.rand.nextInt(rangeY) + veinData.minGenHeight;
    int blockZ = chunkZ * 16 + this.rand.nextInt(16);

    spawnData.set(blockX, blockY, blockZ, new NBTTagCompound());

    int veinSize = (int) Math.max(veinData.minSize, 1);
    int nodeCount = (int) Math.max(veinData.length, 1);

    this.generateVein(
        originX,
        originZ,
        blockX,
        blockY,
        blockZ,
        (this.rand.nextFloat() - 0.5f) * Math.PI * 0.25,
        this.rand.nextFloat() * Math.PI * 2,
        veinSize,
        veinData._replaceWith,
        veinData._toReplace,
        nodeCount,
        chunkDigger,
        veinData
    );

    if (veinData.generateIndicator
        && chunkX == originX
        && chunkZ == originZ) {

      this.generateSurfaceIndicator(
          world,
          (chunkX << 4) + 8,
          (chunkZ << 4) + 8,
          chunkDigger,
          veinData._indicator
      );
    }
  }

  private void generateSurfaceIndicator(
      World world,
      int blockX,
      int blockZ,
      ChunkDigger chunkDigger,
      IBlockState indicatorBlock
  ) {

    BlockPos top = world.getTopSolidOrLiquidBlock(new BlockPos(blockX, 64, blockZ));

    IBlockState state = world.getBlockState(top);
    IBlockState down = world.getBlockState(top.down());

    if (down.isOpaqueCube() && state.getBlock().isReplaceable(world, top)) {

      int minSize = 10;
      int maxSize = 40;
      int range = Math.max(maxSize - minSize, 1);

      int attempts = this.rand.nextInt(range) + minSize;
      List<BlockPos> blockList = new ArrayList<>();
      blockList.add(top);

      int heights[] = new int[3];
      heights[0] = 5;
      heights[1] = 3;
      heights[2] = 1;

      spawning:
      for (int i = 0; i < attempts; i++) {
        BlockPos origin = blockList.get(this.rand.nextInt(blockList.size()));

        EnumFacing facing = EnumFacing.getFront(this.rand.nextInt(4) + 2);
        BlockPos pos = world.getTopSolidOrLiquidBlock(origin.offset(facing));
        int height = 0;

        while (blockList.contains(pos)) {
          pos = pos.up();
          height += 1;
        }

        for (int j = heights.length - 1; j >= 0; j--) {

          if (height >= j + 1) {

            if (heights[j] == 0) {
              continue spawning;

            } else {
              heights[j] -= 1;
              break;
            }
          }
        }

        if (world.getBlockState(pos).getBlock().isReplaceable(world, pos)) {
          blockList.add(pos);
        }
      }

      for (BlockPos blockPos : blockList) {
        chunkDigger.setBlock(blockPos.getX(), blockPos.getY(), blockPos.getZ(), indicatorBlock);
      }
    }

  }

  private void generateVein(
      int originX,
      int originZ,
      double blockX,
      double blockY,
      double blockZ,
      double inclinationAngle,
      double azimuthalAngle,
      double veinSize,
      IBlockState replaceWith,
      Predicate<IBlockState> toReplace,
      int nodeCount,
      ChunkDigger chunkDigger,
      VeinData veinData
  ) {

    if (nodeCount < 0) {
      return;
    }

    double distanceFromOriginSquared = MathUtil.distanceSquared(originX, originZ, (int) blockX >> 4, (int) blockZ >> 4);
    int distanceLimitSquared = (this.genCheckRange - 1) * (this.genCheckRange - 1);

    if (distanceFromOriginSquared >= distanceLimitSquared) {
      return;
    }

    chunkDigger.replaceInRadius((int) veinSize, (int) blockX, (int) blockY, (int) blockZ, replaceWith, toReplace);

    //double f2 = MathHelper.cos((float) inclinationAngle);
    double f3 = MathHelper.sin((float) inclinationAngle);

    blockX += MathHelper.cos((float) azimuthalAngle) * f3;
    blockY += f3;
    blockZ += MathHelper.sin((float) azimuthalAngle) * f3;

    blockY = MathHelper.clamp(blockY, veinData.minGenHeight, veinData.maxGenHeight);

    nodeCount -= 1;

    inclinationAngle += (this.rand.nextFloat() - 0.5f) * Math.PI * 0.25f;
    azimuthalAngle += (this.rand.nextFloat() - 0.5f) * Math.PI * 0.25f;

    inclinationAngle = MathHelper.clamp(inclinationAngle, Math.PI * 0.25, -Math.PI * 0.25);

    float scalar = nodeCount / veinData.length;
    float sizeRange = Math.max(veinData.maxSize - veinData.minSize, 0);
    veinSize = Math.max(veinData.minSize + sizeRange * scalar * scalar, 1);

    this.generateVein(
        originX,
        originZ,
        blockX,
        blockY,
        blockZ,
        inclinationAngle,
        azimuthalAngle,
        veinSize,
        replaceWith,
        toReplace,
        nodeCount,
        chunkDigger,
        veinData
    );

  }

}

