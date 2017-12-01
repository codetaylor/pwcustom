package com.sudoplay.mc.pwcustom.init;

import com.sudoplay.mc.pwcustom.ModPWCustom;
import com.sudoplay.mc.pwcustom.block.BlockPortalFrame;
import com.sudoplay.mc.pwcustom.item.EnumCraftingMaterialType;
import com.sudoplay.mc.pwcustom.item.ItemCraftingToolPart;
import com.sudoplay.mc.pwcustom.item.ItemPortalWand;
import com.sudoplay.mc.pwcustom.item.ItemSaw;
import com.sudoplay.mc.pwcustom.util.ModelRegistrationUtil;
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

  @ObjectHolder(ModPWCustom.MOD_ID + ":" + ItemCraftingToolPart.NAME_SAW_BLADE)
  public static final ItemCraftingToolPart CRAFTING_MATERIAL_SAW_BLADE;

  @ObjectHolder(ModPWCustom.MOD_ID + ":" + ItemCraftingToolPart.NAME_AXE_HEAD)
  public static final ItemCraftingToolPart CRAFTING_MATERIAL_AXE_HEAD;

  @ObjectHolder(ModPWCustom.MOD_ID + ":" + ItemCraftingToolPart.NAME_PICKAXE_HEAD)
  public static final ItemCraftingToolPart CRAFTING_MATERIAL_PICKAXE_HEAD;

  @ObjectHolder(ModPWCustom.MOD_ID + ":" + ItemCraftingToolPart.NAME_SHOVEL_HEAD)
  public static final ItemCraftingToolPart CRAFTING_MATERIAL_SHOVEL_HEAD;

  @ObjectHolder(ModPWCustom.MOD_ID + ":" + ItemCraftingToolPart.NAME_HOE_HEAD)
  public static final ItemCraftingToolPart CRAFTING_MATERIAL_HOE_HEAD;

  @ObjectHolder(ModPWCustom.MOD_ID + ":saw_flint")
  public static final ItemSaw SAW_FLINT;

  @ObjectHolder(ModPWCustom.MOD_ID + ":saw_iron")
  public static final ItemSaw SAW_IRON;

  @ObjectHolder(ModPWCustom.MOD_ID + ":saw_gold")
  public static final ItemSaw SAW_GOLD;

  @ObjectHolder(ModPWCustom.MOD_ID + ":saw_diamond")
  public static final ItemSaw SAW_DIAMOND;

  @ObjectHolder(ModPWCustom.MOD_ID + ":saw_aluminum")
  public static final ItemSaw SAW_ALUMINUM;

  @ObjectHolder(ModPWCustom.MOD_ID + ":saw_bronze")
  public static final ItemSaw SAW_BRONZE;

  @ObjectHolder(ModPWCustom.MOD_ID + ":saw_constantan")
  public static final ItemSaw SAW_CONSTANTAN;

  @ObjectHolder(ModPWCustom.MOD_ID + ":saw_copper")
  public static final ItemSaw SAW_COPPER;

  @ObjectHolder(ModPWCustom.MOD_ID + ":saw_electrum")
  public static final ItemSaw SAW_ELECTRUM;

  @ObjectHolder(ModPWCustom.MOD_ID + ":saw_invar")
  public static final ItemSaw SAW_INVAR;

  @ObjectHolder(ModPWCustom.MOD_ID + ":saw_lead")
  public static final ItemSaw SAW_LEAD;

  @ObjectHolder(ModPWCustom.MOD_ID + ":saw_nickel")
  public static final ItemSaw SAW_NICKEL;

  @ObjectHolder(ModPWCustom.MOD_ID + ":saw_platinum")
  public static final ItemSaw SAW_PLATINUM;

  @ObjectHolder(ModPWCustom.MOD_ID + ":saw_silver")
  public static final ItemSaw SAW_SILVER;

  @ObjectHolder(ModPWCustom.MOD_ID + ":saw_steel")
  public static final ItemSaw SAW_STEEL;

  @ObjectHolder(ModPWCustom.MOD_ID + ":saw_tin")
  public static final ItemSaw SAW_TIN;

  static {
    PORTAL_WAND = null;

    CRAFTING_MATERIAL_SAW_BLADE = null;
    CRAFTING_MATERIAL_AXE_HEAD = null;
    CRAFTING_MATERIAL_PICKAXE_HEAD = null;
    CRAFTING_MATERIAL_SHOVEL_HEAD = null;
    CRAFTING_MATERIAL_HOE_HEAD = null;

    SAW_FLINT = null;
    SAW_IRON = null;
    SAW_GOLD = null;
    SAW_DIAMOND = null;
    SAW_ALUMINUM = null;
    SAW_BRONZE = null;
    SAW_CONSTANTAN = null;
    SAW_COPPER = null;
    SAW_ELECTRUM = null;
    SAW_INVAR = null;
    SAW_LEAD = null;
    SAW_NICKEL = null;
    SAW_PLATINUM = null;
    SAW_SILVER = null;
    SAW_STEEL = null;
    SAW_TIN = null;
  }

  @Mod.EventBusSubscriber
  public static class RegistrationHandler {

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {

      event.getRegistry().registerAll(

          new ItemPortalWand(),

          new ItemCraftingToolPart(ItemCraftingToolPart.NAME_SAW_BLADE),
          new ItemCraftingToolPart(ItemCraftingToolPart.NAME_AXE_HEAD),
          new ItemCraftingToolPart(ItemCraftingToolPart.NAME_PICKAXE_HEAD),
          new ItemCraftingToolPart(ItemCraftingToolPart.NAME_SHOVEL_HEAD),
          new ItemCraftingToolPart(ItemCraftingToolPart.NAME_HOE_HEAD),

          new ItemSaw("saw_flint", ModToolMaterials.FLINT),
          new ItemSaw("saw_iron", Item.ToolMaterial.IRON),
          new ItemSaw("saw_gold", Item.ToolMaterial.GOLD),
          new ItemSaw("saw_diamond", Item.ToolMaterial.DIAMOND),
          new ItemSaw("saw_aluminum", ModToolMaterials.ALUMINUM),
          new ItemSaw("saw_bronze", ModToolMaterials.BRONZE),
          new ItemSaw("saw_constantan", ModToolMaterials.CONSTANTAN),
          new ItemSaw("saw_copper", ModToolMaterials.COPPER),
          new ItemSaw("saw_electrum", ModToolMaterials.ELECTRUM),
          new ItemSaw("saw_invar", ModToolMaterials.INVAR),
          new ItemSaw("saw_lead", ModToolMaterials.LEAD),
          new ItemSaw("saw_nickel", ModToolMaterials.NICKEL),
          new ItemSaw("saw_platinum", ModToolMaterials.PLATINUM),
          new ItemSaw("saw_silver", ModToolMaterials.SILVER),
          new ItemSaw("saw_steel", ModToolMaterials.STEEL),
          new ItemSaw("saw_tin", ModToolMaterials.TIN)
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
          SAW_GOLD,
          SAW_DIAMOND,
          SAW_ALUMINUM,
          SAW_BRONZE,
          SAW_CONSTANTAN,
          SAW_COPPER,
          SAW_ELECTRUM,
          SAW_INVAR,
          SAW_LEAD,
          SAW_NICKEL,
          SAW_PLATINUM,
          SAW_SILVER,
          SAW_STEEL,
          SAW_TIN
      );

      ModelRegistrationUtil.registerVariantItemModels(
          ModItems.PORTAL_WAND,
          "variant",
          BlockPortalFrame.EnumType.values()
      );

      ModelRegistrationUtil.registerVariantItemModels(
          ModItems.CRAFTING_MATERIAL_SAW_BLADE,
          "material",
          EnumCraftingMaterialType.values()
      );

      ModelRegistrationUtil.registerVariantItemModels(
          ModItems.CRAFTING_MATERIAL_AXE_HEAD,
          "material",
          EnumCraftingMaterialType.values()
      );

      ModelRegistrationUtil.registerVariantItemModels(
          ModItems.CRAFTING_MATERIAL_PICKAXE_HEAD,
          "material",
          EnumCraftingMaterialType.values()
      );

      ModelRegistrationUtil.registerVariantItemModels(
          ModItems.CRAFTING_MATERIAL_SHOVEL_HEAD,
          "material",
          EnumCraftingMaterialType.values()
      );

      ModelRegistrationUtil.registerVariantItemModels(
          ModItems.CRAFTING_MATERIAL_HOE_HEAD,
          "material",
          EnumCraftingMaterialType.values()
      );

    }
  }

}
