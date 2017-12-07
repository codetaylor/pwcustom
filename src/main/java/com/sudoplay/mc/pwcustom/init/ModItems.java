package com.sudoplay.mc.pwcustom.init;

import com.sudoplay.mc.pwcustom.ModPWCustom;
import com.sudoplay.mc.pwcustom.block.BlockPortalFrame;
import com.sudoplay.mc.pwcustom.item.*;
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

  @ObjectHolder(ModPWCustom.MOD_ID + ":" + ItemCraftingToolPartFlint.NAME)
  public static final ItemCraftingToolPartFlint CRAFTING_TOOL_PART_FLINT;

  @ObjectHolder(ModPWCustom.MOD_ID + ":" + ItemCast.NAME)
  public static final ItemCast CAST;

  @ObjectHolder(ModPWCustom.MOD_ID + ":" + ItemCraftingPart.NAME)
  public static final ItemCraftingPart CRAFTING_PART;

  @ObjectHolder(ModPWCustom.MOD_ID + ":" + ItemBookGoldEmbossed.NAME)
  public static final ItemBookGoldEmbossed BOOK_GOLD_EMBOSSED;

  @ObjectHolder(ModPWCustom.MOD_ID + ":" + ItemTomeGoldEmbossed.NAME)
  public static final ItemTomeGoldEmbossed TOME_GOLD_EMBOSSED;

  static ItemSaw[] ITEM_SAWS;
  static ItemCraftingToolPart[] TOOL_PARTS;

  static {
    PORTAL_WAND = null;
    CRAFTING_TOOL_PART_FLINT = null;
    CAST = null;
    CRAFTING_PART = null;
    BOOK_GOLD_EMBOSSED = null;
    TOME_GOLD_EMBOSSED = null;
  }

  @Mod.EventBusSubscriber
  public static class RegistrationHandler {

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {

      ITEM_SAWS = new ItemSaw[]{
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
      };
      event.getRegistry().registerAll(ITEM_SAWS);

      TOOL_PARTS = new ItemCraftingToolPart[]{
          new ItemCraftingToolPart(ItemCraftingToolPart.NAME_SAW_BLADE),
          new ItemCraftingToolPart(ItemCraftingToolPart.NAME_AXE_HEAD),
          new ItemCraftingToolPart(ItemCraftingToolPart.NAME_PICKAXE_HEAD),
          new ItemCraftingToolPart(ItemCraftingToolPart.NAME_SHOVEL_HEAD),
          new ItemCraftingToolPart(ItemCraftingToolPart.NAME_HOE_HEAD),
          new ItemCraftingToolPart(ItemCraftingToolPart.NAME_TOOL_ROD),
          new ItemCraftingToolPart(ItemCraftingToolPart.NAME_SWORD_BLADE),
          new ItemCraftingToolPart(ItemCraftingToolPart.NAME_SWORD_GUARD),
          new ItemCraftingToolPart(ItemCraftingToolPart.NAME_WRAPPED_TOOL_ROD),
          new ItemCraftingToolPart(ItemCraftingToolPart.NAME_HAMMER_HEAD),
          new ItemCraftingToolPart(ItemCraftingToolPart.NAME_BOW_LIMB),
          new ItemCraftingToolPart(ItemCraftingToolPart.NAME_SICKLE_BLADE),
          new ItemCraftingToolPart(ItemCraftingToolPart.NAME_ROD),
          new ItemCraftingToolPart(ItemCraftingToolPart.NAME_SHEAR_BLADES),
          new ItemCraftingToolPart(ItemCraftingToolPart.NAME_RING),
          new ItemCraftingToolPart(ItemCraftingToolPart.NAME_PAXEL_HEAD)
      };
      event.getRegistry().registerAll(TOOL_PARTS);

      event.getRegistry().registerAll(
          new ItemPortalWand(),
          new ItemCraftingToolPartFlint(),
          new ItemCast(),
          new ItemCraftingPart(),
          new ItemBookGoldEmbossed(),
          new ItemTomeGoldEmbossed()
      );
    }
  }

  @Mod.EventBusSubscriber(modid = ModPWCustom.MOD_ID, value = Side.CLIENT)
  public static class ClientRegistrationHandler {

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {

      ModelRegistrationUtil.registerItemModels(ITEM_SAWS);

      ModelRegistrationUtil.registerItemModels(
          BOOK_GOLD_EMBOSSED,
          TOME_GOLD_EMBOSSED
      );

      ModelRegistrationUtil.registerVariantItemModels(
          ModItems.PORTAL_WAND,
          "variant",
          BlockPortalFrame.EnumType.values()
      );

      ModelRegistrationUtil.registerVariantItemModels(
          ModItems.CRAFTING_TOOL_PART_FLINT,
          "variant",
          EnumCraftingToolPartTypeFlint.values()
      );

      ModelRegistrationUtil.registerVariantItemModels(
          ModItems.CAST,
          "variant",
          EnumCastType.values()
      );

      ModelRegistrationUtil.registerVariantItemModels(
          ModItems.CRAFTING_PART,
          "variant",
          EnumCraftingPartType.values()
      );

      for (ItemCraftingToolPart toolPart : TOOL_PARTS) {
        ModelRegistrationUtil.registerVariantItemModels(
            toolPart,
            "material",
            EnumCraftingToolPartMaterialType.values()
        );
      }

    }
  }

}
