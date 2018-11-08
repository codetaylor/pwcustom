package com.sudoplay.mc.pwcustom.modules.charcoal;

import com.sudoplay.mc.pwcustom.modules.charcoal.block.*;
import com.sudoplay.mc.pwcustom.modules.charcoal.init.ModuleBlocks;
import com.sudoplay.mc.pwcustom.modules.charcoal.init.ModuleFluids;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.config.Config;

import java.util.HashMap;
import java.util.Map;

@Config(modid = ModuleCharcoal.MOD_ID, name = ModuleCharcoal.MOD_ID + "/" + ModuleCharcoal.MOD_ID + ".module.Charcoal")
public class ModuleCharcoalConfig {

  public static BrickKiln BRICK_KILN = new BrickKiln();

  public static class BrickKiln {

    @Config.Comment({
        "Set to true to deactivate the kiln when a recipe completes.",
        "The kiln will need to be re-lit when it deactivates.",
        "Default: false"
    })
    public boolean KEEP_HEAT = false;
  }

  public static PitKiln PIT_KILN = new PitKiln();

  public static class PitKiln {

    @Config.Comment({
        "Reduces the duration of all recipes by this amount for each adjacent refractory block.",
        "Range: [0, 0.2]",
        "Default: 10% or 0.1"
    })
    public double REFRACTORY_BLOCK_TIME_BONUS = 0.1;

  }

  public static Refractory REFRACTORY = new Refractory();

  public static class Refractory {

    @Config.Comment({
        "Maximum chance for a recipe item to fail conversion.",
        "Default: 0.95"
    })
    public double MAX_FAILURE_CHANCE = 0.95;

    @Config.Comment({
        "Minimum chance for a recipe item to fail conversion.",
        "Default: 0.05"
    })
    public double MIN_FAILURE_CHANCE = 0.05;

    @Config.Comment({
        "The maximum fluid capacity of an active pile in mb.",
        "Default: 500"
    })
    public int ACTIVE_PILE_MAX_FLUID_CAPACITY = 500;

    @Config.Comment({
        "The duration in ticks that different fluids will burn in the Tar Collector."
    })
    public Map<String, Integer> FLUID_BURN_TICKS = new HashMap<String, Integer>() {{
      this.put(ModuleFluids.WOOD_TAR.getName(), 20);
      this.put(ModuleFluids.COAL_TAR.getName(), 40);
    }};

    @Config.Comment({
        "How many smoke particles a burning collector will emit per tick.",
        "Default: 10"
    })
    public int BURNING_COLLECTOR_SMOKE_PARTICLES = 10;
  }

  public static General GENERAL = new General();

  public static class General {

    @Config.Comment({
        "List of valid refractory bricks used in the pit kiln and coke oven."
    })
    public String[] REFRACTORY_BRICKS = new String[]{
        ModuleCharcoal.MOD_ID + ":" + BlockRefractoryBrick.NAME,
        ModuleCharcoal.MOD_ID + ":" + BlockTarCollector.NAME + ":" + BlockTarCollector.EnumType.BRICK.getMeta(),
        ModuleCharcoal.MOD_ID + ":" + BlockTarDrain.NAME + ":" + this.getTarDrainMeta(EnumFacing.NORTH),
        ModuleCharcoal.MOD_ID + ":" + BlockTarDrain.NAME + ":" + this.getTarDrainMeta(EnumFacing.EAST),
        ModuleCharcoal.MOD_ID + ":" + BlockTarDrain.NAME + ":" + this.getTarDrainMeta(EnumFacing.SOUTH),
        ModuleCharcoal.MOD_ID + ":" + BlockTarDrain.NAME + ":" + this.getTarDrainMeta(EnumFacing.WEST),
        ModuleCharcoal.MOD_ID + ":" + BlockIgniter.NAME + ":" + this.getIgniterMeta(EnumFacing.NORTH),
        ModuleCharcoal.MOD_ID + ":" + BlockIgniter.NAME + ":" + this.getIgniterMeta(EnumFacing.EAST),
        ModuleCharcoal.MOD_ID + ":" + BlockIgniter.NAME + ":" + this.getIgniterMeta(EnumFacing.SOUTH),
        ModuleCharcoal.MOD_ID + ":" + BlockIgniter.NAME + ":" + this.getIgniterMeta(EnumFacing.WEST),
        ModuleCharcoal.MOD_ID + ":" + BlockRefractoryGlass.NAME + ":*"
    };

    private int getTarDrainMeta(EnumFacing facing) {

      return ModuleBlocks.TAR_DRAIN.getMetaFromState(
          ModuleBlocks.TAR_DRAIN.getDefaultState()
              .withProperty(BlockTarDrain.VARIANT, BlockTarDrain.EnumType.BRICK)
              .withProperty(BlockTarDrain.FACING, facing)
      );
    }

    private int getIgniterMeta(EnumFacing facing) {

      return ModuleBlocks.IGNITER.getMetaFromState(
          ModuleBlocks.IGNITER.getDefaultState()
              .withProperty(BlockIgniter.VARIANT, BlockIgniter.EnumType.BRICK)
              .withProperty(BlockIgniter.FACING, facing)
      );
    }

    @Config.Comment({
        "Fluid capacity of the tar collector in mb.",
        "Default: 8000"
    })
    public int TAR_COLLECTOR_CAPACITY = 8000;

    @Config.Comment({
        "Fluid capacity of the tar drain in mb.",
        "Default: 1000"
    })
    public int TAR_DRAIN_CAPACITY = 1000;

    @Config.Comment({
        "The durability of the bow drill.",
        "Default: 16"
    })
    public int BOW_DRILL_DURABILITY = 16;

    @Config.Comment({
        "Defines how many ticks it takes to start a fire while using the bow drill.",
        "Default: 60"
    })
    public int BOW_DRILL_USE_DURATION = 60;

    @Config.Comment({
        "The durability of the flint and tinder.",
        "Default: 8"
    })
    public int FLINT_AND_TINDER_DURABILITY = 8;

    @Config.Comment({
        "Defines how many ticks it takes to start a fire while using the flint and tinder.",
        "Default: 100"
    })
    public int FLINT_AND_TINDER_USE_DURATION = 100;
  }

}
