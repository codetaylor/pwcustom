package com.sudoplay.mc.pwcustom.modules.sawing;

import com.sudoplay.mc.pwcustom.ModPWCustom;
import com.sudoplay.mc.pwcustom.material.EnumMaterial;
import com.sudoplay.mc.pwcustom.lib.ItemMaterialPart;
import com.sudoplay.mc.pwcustom.lib.module.IModule;
import com.sudoplay.mc.pwcustom.modules.sawing.item.ItemSaw;
import com.sudoplay.mc.pwcustom.lib.util.ModelRegistrationUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

public class ModuleSawing
    implements IModule {

  public static final EnumMaterial[] MATERIALS = new EnumMaterial[]{
      EnumMaterial.FLINT,
      EnumMaterial.IRON,
      EnumMaterial.GOLD,
      EnumMaterial.DIAMOND,
      EnumMaterial.ALUMINUM,
      EnumMaterial.BRONZE,
      EnumMaterial.CONSTANTAN,
      EnumMaterial.COPPER,
      EnumMaterial.ELECTRUM,
      EnumMaterial.INVAR,
      EnumMaterial.LEAD,
      EnumMaterial.NICKEL,
      EnumMaterial.PLATINUM,
      EnumMaterial.SILVER,
      EnumMaterial.STEEL,
      EnumMaterial.TIN
  };

  @SuppressWarnings("WeakerAccess")
  public static class Items {

    public static final ItemSaw[] SAWS = new ItemSaw[MATERIALS.length];

    @GameRegistry.ObjectHolder(ModPWCustom.MOD_ID + ":part_saw_blade")
    public static final ItemMaterialPart PART_SAW_BLADE;

    static {
      PART_SAW_BLADE = null;
    }
  }

  @Override
  public void onRegisterItemsEvent(RegistryEvent.Register<Item> event) {

    IForgeRegistry<Item> registry = event.getRegistry();

    // Saws
    for (int i = 0; i < MATERIALS.length; i++) {
      Items.SAWS[i] = new ItemSaw("saw_" + MATERIALS[i].getName(), MATERIALS[i]);
    }
    registry.registerAll(Items.SAWS);

    // Part Saw Blade
    registry.register(new ItemMaterialPart("part_saw_blade", MATERIALS));
  }

  @Override
  public void onClientRegisterModelsEvent(ModelRegistryEvent event) {

    for (ItemSaw saw : Items.SAWS) {
      String resourcePath = saw.getMaterial().isHighlighted() ? "saw_highlight" : "saw";
      ResourceLocation location = new ResourceLocation(ModPWCustom.MOD_ID, resourcePath);
      ModelResourceLocation modelResourceLocation = new ModelResourceLocation(location, "inventory");
      ModelRegistrationUtil.registerItemModel(saw, 0, modelResourceLocation);
    }

    for (int i = 0; i < MATERIALS.length; i++) {
      String resourcePath = MATERIALS[i].isHighlighted() ? "part_saw_blade_highlight" : "part_saw_blade";
      ResourceLocation location = new ResourceLocation(ModPWCustom.MOD_ID, resourcePath);
      ModelResourceLocation modelResourceLocation = new ModelResourceLocation(location, "inventory");
      ModelRegistrationUtil.registerItemModel(Items.PART_SAW_BLADE, i, modelResourceLocation);
    }
  }

  @Override
  public void onClientInitialization(FMLInitializationEvent event) {

    ItemColors itemColors = Minecraft.getMinecraft().getItemColors();

    itemColors.registerItemColorHandler(
        (stack, tintIndex) -> (tintIndex == 0) ? ((ItemSaw) stack.getItem()).getMaterial().getColor() : 0xFFFFFF,
        Items.SAWS
    );

    itemColors.registerItemColorHandler(
        (stack, tintIndex) -> (tintIndex == 0) ? MATERIALS[stack.getMetadata()].getColor() : 0xFFFFFF,
        Items.PART_SAW_BLADE
    );
  }
}
