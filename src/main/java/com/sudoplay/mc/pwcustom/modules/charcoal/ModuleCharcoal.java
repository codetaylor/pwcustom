package com.sudoplay.mc.pwcustom.modules.charcoal;

import com.codetaylor.mc.athenaeum.module.ModuleBase;
import com.codetaylor.mc.athenaeum.registry.Registry;
import com.codetaylor.mc.athenaeum.util.ModelRegistrationHelper;
import com.sudoplay.mc.pwcustom.ModPWCustom;
import com.sudoplay.mc.pwcustom.modules.charcoal.block.*;
import com.sudoplay.mc.pwcustom.modules.charcoal.client.render.TESRKiln;
import com.sudoplay.mc.pwcustom.modules.charcoal.init.FuelHandler;
import com.sudoplay.mc.pwcustom.modules.charcoal.recipe.KilnRecipe;
import com.sudoplay.mc.pwcustom.modules.charcoal.tile.*;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
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
    public static final BlockTarCollector TAR_COLLECTOR = new BlockTarCollector();
    public static final BlockTarDrain TAR_DRAIN = new BlockTarDrain();
    public static final BlockRefractoryBrick REFRACTORY_BRICK = new BlockRefractoryBrick();
    public static final BlockKiln KILN = new BlockKiln();
  }

  public static final class Items {

    public static final Item WOOD_ASH = new Item();
    public static final Item COAL_COKE = new Item();
    public static final Item STRAW = new Item();
    public static final Item COAL_ASH = new Item();
    public static final Item FLINT_CLAY_BALL = new Item();
    public static final Item REFRACTORY_CLAY_BALL = new Item();
    public static final Item REFRACTORY_BRICK = new Item();
  }

  public ModuleCharcoal() {

    super(0, MOD_ID);

    this.setRegistry(new Registry(MOD_ID, CREATIVE_TAB));
    this.enableAutoRegistry();
  }

  @Override
  public void onPreInitializationEvent(FMLPreInitializationEvent event) {

    super.onPreInitializationEvent(event);

    FMLInterModComms.sendMessage(
        "waila",
        "register",
        "com.sudoplay.mc.pwcustom.modules.charcoal.compat.waila.WailaRegistrar.wailaCallback"
    );
  }

  @Override
  public void onRegister(Registry registry) {

    registry.registerBlock(Blocks.LOG_PILE_ACTIVE, BlockLogPileActive.NAME);
    registry.registerBlock(Blocks.COAL_PILE_ACTIVE, BlockCoalPileActive.NAME);
    registry.registerBlock(Blocks.LOG_PILE_ASH, BlockLogPileAsh.NAME);
    registry.registerBlock(Blocks.COAL_PILE_ASH, BlockCoalPileAsh.NAME);
    registry.registerBlock(Blocks.KILN, BlockKiln.NAME);

    registry.registerBlockWithItem(Blocks.LOG_PILE, BlockLogPile.NAME);
    registry.registerBlockWithItem(Blocks.COAL_COKE_BLOCK, BlockCoalCokeBlock.NAME);
    registry.registerBlockWithItem(Blocks.THATCH, BlockThatch.NAME);
    registry.registerBlockWithItem(Blocks.TAR_COLLECTOR, BlockTarCollector.NAME);
    registry.registerBlockWithItem(Blocks.TAR_DRAIN, BlockTarDrain.NAME);
    registry.registerBlockWithItem(Blocks.REFRACTORY_BRICK, BlockRefractoryBrick.NAME);

    registry.registerItem(Items.WOOD_ASH, "wood_ash");
    registry.registerItem(Items.COAL_COKE, "coal_coke");
    registry.registerItem(Items.STRAW, "straw");
    registry.registerItem(Items.COAL_ASH, "coal_ash");
    registry.registerItem(Items.FLINT_CLAY_BALL, "flint_clay_ball");
    registry.registerItem(Items.REFRACTORY_CLAY_BALL, "refractory_clay_ball");
    registry.registerItem(Items.REFRACTORY_BRICK, "refractory_brick");
    registry.registerItem(new ItemBlock(Blocks.KILN), Blocks.KILN.getRegistryName());

    registry.registerTileEntities(
        TileActiveLogPile.class,
        TileActiveCoalPile.class,
        TileTarCollector.class,
        TileTarDrain.class,
        TileKiln.class
    );

    GameRegistry.registerFuelHandler(new FuelHandler());
  }

  @Override
  public void onClientRegister(Registry registry) {

    registry.registerClientModelRegistrationStrategy(() -> {

      ModelRegistrationHelper.registerBlockItemModels(
          Blocks.LOG_PILE,
          Blocks.LOG_PILE_ASH,
          Blocks.COAL_COKE_BLOCK,
          Blocks.THATCH,
          Blocks.COAL_PILE_ASH,
          Blocks.REFRACTORY_BRICK
      );

      ModelRegistrationHelper.registerItemModels(
          Items.WOOD_ASH,
          Items.COAL_ASH,
          Items.COAL_COKE,
          Items.STRAW,
          Items.REFRACTORY_BRICK,
          Items.REFRACTORY_CLAY_BALL,
          Items.FLINT_CLAY_BALL
      );

      ModelRegistrationHelper.registerBlockItemModel(Blocks.KILN.getDefaultState()
          .withProperty(BlockKiln.VARIANT, BlockKiln.EnumType.EMPTY));

      // tar collector
      ModelRegistrationHelper.registerVariantBlockItemModels(
          Blocks.TAR_COLLECTOR.getDefaultState(),
          BlockTarCollector.VARIANT
      );

      // tar drain
      ModelRegistrationHelper.registerVariantBlockItemModels(
          Blocks.TAR_DRAIN.getDefaultState(),
          BlockTarDrain.VARIANT
      );

      ClientRegistry.bindTileEntitySpecialRenderer(
          TileKiln.class,
          new TESRKiln()
      );
    });
  }

  @Override
  public void onRegisterRecipesEvent(RegistryEvent.Register<IRecipe> event) {

    super.onRegisterRecipesEvent(event);

    KilnRecipe.RECIPE_LIST.add(new KilnRecipe(
        Ingredient.fromStacks(new ItemStack(Items.REFRACTORY_CLAY_BALL)),
        new ItemStack(Items.REFRACTORY_BRICK)
    ));
  }
}
