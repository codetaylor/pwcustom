package com.sudoplay.mc.pwcustom.modules.charcoal;

import com.codetaylor.mc.athenaeum.module.ModuleBase;
import com.codetaylor.mc.athenaeum.registry.Registry;
import com.codetaylor.mc.athenaeum.util.ModelRegistrationHelper;
import com.sudoplay.mc.pwcustom.ModPWCustom;
import com.sudoplay.mc.pwcustom.modules.charcoal.block.*;
import com.sudoplay.mc.pwcustom.modules.charcoal.tile.TileActiveCoalPile;
import com.sudoplay.mc.pwcustom.modules.charcoal.tile.TileActiveLogPile;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModuleCharcoal
    extends ModuleBase {

  public static final String MOD_ID = ModPWCustom.MOD_ID;
  public static final CreativeTabs CREATIVE_TAB = ModPWCustom.CREATIVE_TAB;

  static {
    FluidRegistry.enableUniversalBucket();
  }

  public static final class Blocks {

    public static final BlockLogPile LOG_PILE = new BlockLogPile();
    public static final BlockLogPileActive LOG_PILE_ACTIVE = new BlockLogPileActive();
    public static final BlockLogPileAsh LOG_PILE_ASH = new BlockLogPileAsh();
    public static final BlockCoalCokeBlock COAL_COKE_BLOCK = new BlockCoalCokeBlock();
    public static final BlockThatch THATCH = new BlockThatch();
    public static final BlockCoalPileActive COAL_PILE_ACTIVE = new BlockCoalPileActive();
    public static final BlockCoalPileAsh COAL_PILE_ASH = new BlockCoalPileAsh();
  }

  public static final class Items {

    public static final Item WOOD_ASH = new Item();
    public static final Item COAL_COKE = new Item();
    public static final Item STRAW = new Item();
    public static final Item COAL_ASH = new Item();
  }

  public ModuleCharcoal() {

    super(0, MOD_ID);

    this.setRegistry(new Registry(MOD_ID, CREATIVE_TAB));
    this.enableAutoRegistry();
  }

  @Override
  public void onRegister(Registry registry) {

    registry.registerBlock(Blocks.LOG_PILE_ACTIVE, BlockLogPileActive.NAME);
    registry.registerBlock(Blocks.COAL_PILE_ACTIVE, BlockCoalPileActive.NAME);

    registry.registerBlockWithItem(Blocks.LOG_PILE, BlockLogPile.NAME);
    registry.registerBlockWithItem(Blocks.LOG_PILE_ASH, BlockLogPileAsh.NAME);
    registry.registerBlockWithItem(Blocks.COAL_COKE_BLOCK, BlockCoalCokeBlock.NAME);
    registry.registerBlockWithItem(Blocks.THATCH, BlockThatch.NAME);
    registry.registerBlockWithItem(Blocks.COAL_PILE_ASH, BlockCoalPileAsh.NAME);

    registry.registerItem(Items.WOOD_ASH, "wood_ash");
    registry.registerItem(Items.COAL_COKE, "coal_coke");
    registry.registerItem(Items.STRAW, "straw");
    registry.registerItem(Items.COAL_ASH, "coal_ash");

    registry.registerTileEntities(
        TileActiveLogPile.class,
        TileActiveCoalPile.class
    );

    GameRegistry.registerFuelHandler(fuel -> {

      if (fuel.getItem() == Items.COAL_COKE) {
        return 3200;

      } else if (fuel.getItem() == Item.getItemFromBlock(Blocks.COAL_COKE_BLOCK)) {
        return 3200 * 10;

      } else if (fuel.getItem() == Item.getItemFromBlock(Blocks.THATCH)) {
        return 200;

      } else if (fuel.getItem() == Items.STRAW) {
        return 50;
      }

      return 0;
    });
  }

  @Override
  public void onClientRegister(Registry registry) {

    registry.registerClientModelRegistrationStrategy(() -> {

      ModelRegistrationHelper.registerBlockItemModels(
          Blocks.LOG_PILE,
          Blocks.LOG_PILE_ASH,
          Blocks.COAL_COKE_BLOCK,
          Blocks.THATCH,
          Blocks.COAL_PILE_ASH
      );

      ModelRegistrationHelper.registerItemModels(
          Items.WOOD_ASH,
          Items.COAL_ASH,
          Items.COAL_COKE,
          Items.STRAW
      );
    });
  }
}
