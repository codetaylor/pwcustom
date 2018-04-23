package com.sudoplay.mc.pwcustom.modules.charcoal;

import com.sudoplay.mc.pwcustom.modules.charcoal.block.BlockIgniter;
import com.sudoplay.mc.pwcustom.modules.charcoal.block.BlockRefractoryBrick;
import com.sudoplay.mc.pwcustom.modules.charcoal.block.BlockTarCollector;
import com.sudoplay.mc.pwcustom.modules.charcoal.block.BlockTarDrain;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.config.Config;

@Config(modid = ModuleCharcoal.MOD_ID, name = ModuleCharcoal.MOD_ID + ".module.Charcoal")
public class ModuleCharcoalConfig {

  public static PitKiln PIT_KILN = new PitKiln();

  public static class PitKiln {

    @Config.Comment({
        "How many ticks to complete burning"
    })
    public int BURN_TIME_TICKS = 8000;
  }

  public static General GENERAL = new General();

  public static class General {

    @Config.Comment({
        "List of valid refractory bricks used in the pit kiln and coke oven"
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
        ModuleCharcoal.MOD_ID + ":" + BlockIgniter.NAME + ":" + this.getIgniterMeta(EnumFacing.WEST)
    };

    private int getTarDrainMeta(EnumFacing facing) {

      return ModuleCharcoal.Blocks.TAR_DRAIN.getMetaFromState(
          ModuleCharcoal.Blocks.TAR_DRAIN.getDefaultState()
              .withProperty(BlockTarDrain.VARIANT, BlockTarDrain.EnumType.BRICK)
              .withProperty(BlockTarDrain.FACING, facing)
      );
    }

    private int getIgniterMeta(EnumFacing facing) {

      return ModuleCharcoal.Blocks.IGNITER.getMetaFromState(
          ModuleCharcoal.Blocks.IGNITER.getDefaultState()
              .withProperty(BlockIgniter.VARIANT, BlockIgniter.EnumType.BRICK)
              .withProperty(BlockIgniter.FACING, facing)
      );
    }

    @Config.Comment({
        "Fluid capacity of the tar collector in millibuckets"
    })
    public int TAR_COLLECTOR_CAPACITY = 8000;

    @Config.Comment({
        "Fluid capacity of the tar drain in millibuckets"
    })
    public int TAR_DRAIN_CAPACITY = 1000;
  }

}
