package com.sudoplay.mc.pwcustom.init;

import com.sudoplay.mc.pwcustom.ModPWCustom;
import com.sudoplay.mc.pwcustom.block.BlockPortalDarklands;
import com.sudoplay.mc.pwcustom.block.BlockPortalFrame;
import com.sudoplay.mc.pwcustom.block.BlockSoulGravel;
import com.sudoplay.mc.pwcustom.util.BlockRegistrationUtil;
import com.sudoplay.mc.pwcustom.util.ModelRegistrationUtil;
import com.sudoplay.mc.pwcustom.workbench.block.BlockWorkbench;
import com.sudoplay.mc.pwcustom.workbench.tile.*;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

import static net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

public class ModBlocks {

  @ObjectHolder(ModPWCustom.MOD_ID + ":" + BlockSoulGravel.NAME)
  public static final BlockSoulGravel SOUL_GRAVEL;

  @ObjectHolder(ModPWCustom.MOD_ID + ":" + BlockPortalFrame.NAME)
  public static final BlockPortalFrame PORTAL_FRAME;

  @ObjectHolder(ModPWCustom.MOD_ID + ":" + BlockPortalDarklands.NAME)
  public static final BlockPortalDarklands PORTAL_DARKLANDS;

  @ObjectHolder(ModPWCustom.MOD_ID + ":" + BlockWorkbench.NAME)
  public static final BlockWorkbench WORKBENCH_BASIC;

  static {
    SOUL_GRAVEL = null;
    PORTAL_FRAME = null;
    PORTAL_DARKLANDS = null;
    WORKBENCH_BASIC = null;
  }

  @Mod.EventBusSubscriber(modid = ModPWCustom.MOD_ID)
  public static class RegistrationHandler {

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {

      event.getRegistry().registerAll(
          new BlockSoulGravel(),
          new BlockPortalFrame(),
          new BlockPortalDarklands(),
          new BlockWorkbench()
      );
    }

    @SubscribeEvent
    public static void registerItemBlocks(RegistryEvent.Register<Item> event) {

      event.getRegistry().registerAll(
          BlockRegistrationUtil.createItemBlocks(
              SOUL_GRAVEL,
              PORTAL_FRAME,
              WORKBENCH_BASIC
          )
      );

      RegistrationHandler.registerTileEntities();
    }

    private static void registerTileEntities() {

      RegistrationHandler.registerTileEntities(
          TileEntityWorkbenchBlacksmith.class,
          TileEntityWorkbenchCarpenter.class,
          TileEntityWorkbenchJeweler.class,
          TileEntityWorkbenchMason.class,
          TileEntityWorkbenchTailor.class
      );
    }

    @SafeVarargs
    private static void registerTileEntities(Class<? extends TileEntity>... tileEntityClasses) {

      for (Class<? extends TileEntity> tileEntityClass : tileEntityClasses) {
        GameRegistry.registerTileEntity(tileEntityClass, ModPWCustom.MOD_ID + "_" + tileEntityClass.getSimpleName());
      }
    }
  }

  @Mod.EventBusSubscriber(modid = ModPWCustom.MOD_ID, value = Side.CLIENT)
  public static class ClientRegistrationHandler {

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {

      // Single Variant Blocks
      ModelRegistrationUtil.registerBlockItemModels(
          SOUL_GRAVEL
      );

      // Portal Frame
      ModelRegistrationUtil.registerVariantBlockItemModels(
          PORTAL_FRAME.getDefaultState(),
          BlockPortalFrame.VARIANT
      );

      // Workbench Basic
      ModelRegistrationUtil.registerVariantBlockItemModels(
          WORKBENCH_BASIC.getDefaultState(),
          BlockWorkbench.VARIANT
      );

    }
  }

}
