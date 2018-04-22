package com.sudoplay.mc.pwcustom.modules.charcoal;

import com.sudoplay.mc.pwcustom.modules.charcoal.block.BlockRefractoryBrick;
import com.sudoplay.mc.pwcustom.modules.charcoal.block.BlockTarCollector;
import com.sudoplay.mc.pwcustom.modules.charcoal.block.BlockTarDrain;
import com.sudoplay.mc.pwcustom.modules.charcoal.init.ModuleFluids;
import net.minecraftforge.common.config.Config;

@Config(modid = ModuleCharcoal.MOD_ID, name = ModuleCharcoal.MOD_ID + ".module.Charcoal")
public class ModuleCharcoalConfig {

  public static LogPile LOG_PILE = new LogPile();

  public static class LogPile {

    @Config.Comment({
        "How many ticks to complete burning"
    })
    public int BURN_TIME_TICKS = 18000;

    @Config.Comment({
        "Total number of burn stages"
    })
    public int BURN_STAGES = 10;

    @Config.Comment({
        "The type of fluid produced during burning"
    })
    public String FLUID_PRODUCED = ModuleFluids.WOOD_TAR.getName();

    @Config.Comment({
        "The amount of fluid produced per burn stage in millibuckets"
    })
    public int FLUID_PRODUCED_AMOUNT_MB = 50;
  }

  public static CoalBlock COAL_BLOCK = new CoalBlock();

  public static class CoalBlock {

    @Config.Comment({
        "How many ticks to complete burning"
    })
    public int BURN_TIME_TICKS = 36000;

    @Config.Comment({
        "Total number of burn stages"
    })
    public int BURN_STAGES = 10;

    @Config.Comment({
        "The type of fluid produced during burning"
    })
    public String FLUID_PRODUCED = ModuleFluids.COAL_TAR.getName();

    @Config.Comment({
        "The amount of fluid produced per burn stage in millibuckets"
    })
    public int FLUID_PRODUCED_AMOUNT_MB = 50;
  }

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
        ModuleCharcoal.MOD_ID + ":" + BlockTarDrain.NAME + ":" + BlockTarDrain.EnumType.BRICK.getMeta()
    };
  }

}
