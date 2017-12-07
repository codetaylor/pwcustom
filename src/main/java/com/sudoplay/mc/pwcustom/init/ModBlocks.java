package com.sudoplay.mc.pwcustom.init;

import com.sudoplay.mc.pwcustom.ModPWCustom;
import com.sudoplay.mc.pwcustom.block.BlockPortalDarklands;
import com.sudoplay.mc.pwcustom.block.BlockPortalFrame;
import com.sudoplay.mc.pwcustom.block.BlockSoulGravel;
import com.sudoplay.mc.pwcustom.block.BlockWorkbenchBasic;
import com.sudoplay.mc.pwcustom.tile.TileEntityWorkbenchTailoring;
import com.sudoplay.mc.pwcustom.util.BlockRegistrationUtil;
import com.sudoplay.mc.pwcustom.util.ModelRegistrationUtil;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
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

  @ObjectHolder(ModPWCustom.MOD_ID + ":" + BlockWorkbenchBasic.NAME)
  public static final BlockWorkbenchBasic WORKBENCH_BASIC;

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
          new BlockWorkbenchBasic()
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

      GameRegistry.registerTileEntity(
          TileEntityWorkbenchTailoring.class,
          ModPWCustom.MOD_ID + "_TileEntityWorkbenchTailoring"
      );
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
          BlockWorkbenchBasic.VARIANT
      );

    }
  }

}
