package com.sudoplay.mc.ctpwcustom.init;

import com.sudoplay.mc.ctpwcustom.ModPWCustom;
import com.sudoplay.mc.ctpwcustom.block.BlockPortalFrame;
import com.sudoplay.mc.ctpwcustom.item.ItemPortalWand;
import com.sudoplay.mc.ctpwcustom.item.ItemSaw;
import com.sudoplay.mc.ctpwcustom.util.ModelRegistrationUtil;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import static net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

public class ModItems {

  @ObjectHolder(ModPWCustom.MOD_ID + ":" + ItemPortalWand.NAME)
  public static final ItemPortalWand PORTAL_WAND;

  @ObjectHolder(ModPWCustom.MOD_ID + ":" + ItemSaw.NAME_FLINT)
  public static final ItemSaw SAW_FLINT;

  @ObjectHolder(ModPWCustom.MOD_ID + ":" + ItemSaw.NAME_IRON)
  public static final ItemSaw SAW_IRON;

  @ObjectHolder(ModPWCustom.MOD_ID + ":" + ItemSaw.NAME_DIAMOND)
  public static final ItemSaw SAW_DIAMOND;

  static {
    PORTAL_WAND = null;
    SAW_FLINT = null;
    SAW_IRON = null;
    SAW_DIAMOND = null;
  }

  @Mod.EventBusSubscriber
  public static class RegistrationHandler {

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {

      event.getRegistry().registerAll(
          new ItemPortalWand(),
          new ItemSaw(ItemSaw.NAME_FLINT, ModToolMaterials.FLINT),
          new ItemSaw(ItemSaw.NAME_IRON, Item.ToolMaterial.IRON),
          new ItemSaw(ItemSaw.NAME_DIAMOND, Item.ToolMaterial.DIAMOND)
      );
    }
  }

  @Mod.EventBusSubscriber(modid = ModPWCustom.MOD_ID, value = Side.CLIENT)
  public static class ClientRegistrationHandler {

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {

      ModelRegistrationUtil.registerItemModels(
          SAW_FLINT,
          SAW_IRON,
          SAW_DIAMOND
      );

      ModelRegistrationUtil.registerVariantItemModels(
          ModItems.PORTAL_WAND,
          "variant",
          BlockPortalFrame.EnumType.values()
      );

    }
  }

}
