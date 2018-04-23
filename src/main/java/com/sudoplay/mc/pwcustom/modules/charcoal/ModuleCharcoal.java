package com.sudoplay.mc.pwcustom.modules.charcoal;

import com.codetaylor.mc.athenaeum.module.ModuleBase;
import com.codetaylor.mc.athenaeum.parser.recipe.item.MalformedRecipeItemException;
import com.codetaylor.mc.athenaeum.parser.recipe.item.RecipeItemParser;
import com.codetaylor.mc.athenaeum.registry.Registry;
import com.codetaylor.mc.athenaeum.util.ModelRegistrationHelper;
import com.sudoplay.mc.pwcustom.ModPWCustom;
import com.sudoplay.mc.pwcustom.modules.charcoal.block.*;
import com.sudoplay.mc.pwcustom.modules.charcoal.client.render.TESRKiln;
import com.sudoplay.mc.pwcustom.modules.charcoal.init.FuelHandler;
import com.sudoplay.mc.pwcustom.modules.charcoal.item.ItemQuicklime;
import com.sudoplay.mc.pwcustom.modules.charcoal.recipe.BurnRecipe;
import com.sudoplay.mc.pwcustom.modules.charcoal.recipe.KilnRecipe;
import com.sudoplay.mc.pwcustom.modules.charcoal.tile.*;
import com.sudoplay.mc.pwcustom.util.BlockMetaMatcher;
import com.sudoplay.mc.pwcustom.util.Util;
import net.minecraft.block.BlockDoor;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemDoor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModuleCharcoal
    extends ModuleBase {

  public static final String MOD_ID = ModPWCustom.MOD_ID;
  public static final CreativeTabs CREATIVE_TAB = ModPWCustom.CREATIVE_TAB;

  public static final Logger LOGGER = LogManager.getLogger(MOD_ID + "." + ModuleCharcoal.class.getSimpleName());

  static {
    FluidRegistry.enableUniversalBucket();
  }

  public static final class Blocks {

    public static final BlockLogPile LOG_PILE = new BlockLogPile();
    public static final BlockCoalCokeBlock COAL_COKE_BLOCK = new BlockCoalCokeBlock();
    public static final BlockThatch THATCH = new BlockThatch();
    public static final BlockTarCollector TAR_COLLECTOR = new BlockTarCollector();
    public static final BlockTarDrain TAR_DRAIN = new BlockTarDrain();
    public static final BlockRefractoryBrick REFRACTORY_BRICK = new BlockRefractoryBrick();
    public static final BlockKiln KILN = new BlockKiln();
    public static final BlockIgniter IGNITER = new BlockIgniter();
    public static final BlockPitAsh PIT_ASH_BLOCK = new BlockPitAsh();
    public static final BlockActivePile ACTIVE_PILE = new BlockActivePile();
    public static final BlockRefractoryDoor REFRACTORY_DOOR = new BlockRefractoryDoor();
    public static final BlockLimestone LIMESTONE = new BlockLimestone();
  }

  public static final class Items {

    public static final Item PIT_ASH = new Item();
    public static final Item COAL_COKE = new Item();
    public static final Item STRAW = new Item();
    public static final Item FLINT_CLAY_BALL = new Item();
    public static final Item REFRACTORY_CLAY_BALL = new Item();
    public static final Item REFRACTORY_BRICK = new Item();
    public static final Item POTTERY_FRAGMENTS = new Item();
    public static final Item POTTERY_SHARD = new Item();
    public static final Item REFRACTORY_DOOR = new ItemDoor(Blocks.REFRACTORY_DOOR);
    public static final Item QUICKLIME = new ItemQuicklime();
    public static final Item SLAKED_LIME = new Item();
  }

  public ModuleCharcoal() {

    super(0, MOD_ID);

    this.setRegistry(new Registry(MOD_ID, CREATIVE_TAB));
    this.enableAutoRegistry();

    MinecraftForge.EVENT_BUS.register(this);

    this.registerIntegrationPlugin(
        "crafttweaker",
        "com.sudoplay.mc.pwcustom.modules.charcoal.compat.crafttweaker.ZenPitKiln"
    );

    this.registerIntegrationPlugin(
        "crafttweaker",
        "com.sudoplay.mc.pwcustom.modules.charcoal.compat.crafttweaker.ZenBurn"
    );
  }

  @SubscribeEvent
  public void onNewRegistryEvent(RegistryEvent.NewRegistry event) {

    new RegistryBuilder<BurnRecipe>()
        .setName(new ResourceLocation(MOD_ID, "pit_recipe"))
        .setType(BurnRecipe.class)
        .create();

    new RegistryBuilder<KilnRecipe>()
        .setName(new ResourceLocation(MOD_ID, "kiln_recipe"))
        .setType(KilnRecipe.class)
        .create();
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

    registry.registerBlock(Blocks.ACTIVE_PILE, BlockActivePile.NAME);
    registry.registerBlock(Blocks.KILN, BlockKiln.NAME);
    registry.registerBlock(Blocks.PIT_ASH_BLOCK, BlockPitAsh.NAME);
    registry.registerBlock(Blocks.REFRACTORY_DOOR, "refractory_door");

    registry.registerBlockWithItem(Blocks.LOG_PILE, BlockLogPile.NAME);
    registry.registerBlockWithItem(Blocks.COAL_COKE_BLOCK, BlockCoalCokeBlock.NAME);
    registry.registerBlockWithItem(Blocks.THATCH, BlockThatch.NAME);
    registry.registerBlockWithItem(Blocks.TAR_COLLECTOR, BlockTarCollector.NAME);
    registry.registerBlockWithItem(Blocks.TAR_DRAIN, BlockTarDrain.NAME);
    registry.registerBlockWithItem(Blocks.REFRACTORY_BRICK, BlockRefractoryBrick.NAME);
    registry.registerBlockWithItem(Blocks.IGNITER, BlockIgniter.NAME);
    registry.registerBlockWithItem(Blocks.LIMESTONE, BlockLimestone.NAME);

    registry.registerItem(Items.PIT_ASH, "pit_ash");
    registry.registerItem(Items.COAL_COKE, "coal_coke");
    registry.registerItem(Items.STRAW, "straw");
    registry.registerItem(Items.FLINT_CLAY_BALL, "flint_clay_ball");
    registry.registerItem(Items.REFRACTORY_CLAY_BALL, "refractory_clay_ball");
    registry.registerItem(Items.REFRACTORY_BRICK, "refractory_brick");
    registry.registerItem(new ItemBlock(Blocks.KILN), Blocks.KILN.getRegistryName());
    registry.registerItem(Items.POTTERY_FRAGMENTS, "pottery_fragments");
    registry.registerItem(Items.POTTERY_SHARD, "pottery_shard");
    registry.registerItem(Items.REFRACTORY_DOOR, "refractory_door");
    registry.registerItem(Items.QUICKLIME, "quicklime");
    registry.registerItem(Items.SLAKED_LIME, "slaked_lime");

    registry.registerTileEntities(
        TileTarCollector.class,
        TileTarDrain.class,
        TileKiln.class,
        TilePitAsh.class,
        TileActivePile.class
    );

    GameRegistry.registerFuelHandler(new FuelHandler());
  }

  @Override
  public void onClientRegister(Registry registry) {

    registry.registerClientModelRegistrationStrategy(() -> {

      ModelRegistrationHelper.registerBlockItemModels(
          Blocks.LOG_PILE,
          Blocks.COAL_COKE_BLOCK,
          Blocks.THATCH,
          Blocks.REFRACTORY_BRICK,
          Blocks.LIMESTONE
      );

      ModelRegistrationHelper.registerItemModels(
          Items.PIT_ASH,
          Items.COAL_COKE,
          Items.STRAW,
          Items.REFRACTORY_BRICK,
          Items.REFRACTORY_CLAY_BALL,
          Items.FLINT_CLAY_BALL,
          Items.POTTERY_FRAGMENTS,
          Items.POTTERY_SHARD,
          Items.REFRACTORY_DOOR,
          Items.QUICKLIME,
          Items.SLAKED_LIME
      );

      ModelLoader.setCustomStateMapper(
          Blocks.REFRACTORY_DOOR,
          (new StateMap.Builder()).ignore(BlockDoor.POWERED).build()
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

      // igniter
      ModelRegistrationHelper.registerVariantBlockItemModels(
          Blocks.IGNITER.getDefaultState(),
          BlockIgniter.VARIANT
      );

      ClientRegistry.bindTileEntitySpecialRenderer(
          TileKiln.class,
          new TESRKiln()
      );
    });
  }

  @Override
  public void onPostInitializationEvent(FMLPostInitializationEvent event) {

    super.onPostInitializationEvent(event);

    RecipeItemParser parser = new RecipeItemParser();

    // ------------------------------------------------------------------------
    // - Refractory Blocks
    // ------------------------------------------------------------------------

    for (String blockString : ModuleCharcoalConfig.GENERAL.REFRACTORY_BRICKS) {
      try {
        Registries.REFRACTORY_BLOCK_LIST.add(Util.parseBlockStringWithWildcard(blockString, parser));

      } catch (MalformedRecipeItemException e) {
        LOGGER.error("", e);
      }
    }

    // ------------------------------------------------------------------------
    // - Additional Valid Coke Oven Structure Blocks
    // ------------------------------------------------------------------------

    {
      Registries.COKE_OVEN_VALID_STRUCTURE_BLOCK_LIST.add(new BlockMetaMatcher(
          Blocks.ACTIVE_PILE,
          0
      ));
      Registries.COKE_OVEN_VALID_STRUCTURE_BLOCK_LIST.add(new BlockMetaMatcher(
          Blocks.PIT_ASH_BLOCK,
          0
      ));
    }
  }
}
