package com.sudoplay.mc.pwcustom.modules.toolparts;

import com.codetaylor.mc.athenaeum.helper.ModelRegistrationHelper;
import com.codetaylor.mc.athenaeum.module.ModuleBase;
import com.sudoplay.mc.pwcustom.ModPWCustom;
import com.sudoplay.mc.pwcustom.material.EnumMaterial;
import com.sudoplay.mc.pwcustom.material.ItemMaterialPart;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class ModuleToolParts
    extends ModuleBase {

  public static final String MOD_ID = ModPWCustom.MOD_ID;
  public static final CreativeTabs CREATIVE_TAB = ModPWCustom.CREATIVE_TAB;

  public ModuleToolParts() {

    super(0);
  }

  private static final EnumMaterial[] MATERIALS = new EnumMaterial[]{
      EnumMaterial.STONE,
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

  private static final RegistrationWrapper[] REGISTRATION_WRAPPERS = new RegistrationWrapper[]{
      new RegistrationWrapper("part_axe_head", MATERIALS, true),
      new RegistrationWrapper("part_pickaxe_head", MATERIALS, true),
      new RegistrationWrapper("part_shovel_head", MATERIALS, true),
      new RegistrationWrapper("part_hoe_head", MATERIALS, true),
      new RegistrationWrapper("part_tool_rod", MATERIALS, false),
      new RegistrationWrapper("part_tool_rod_wrapped", MATERIALS, false),
      new RegistrationWrapper("part_sword_blade", MATERIALS, true),
      new RegistrationWrapper("part_sword_guard", MATERIALS, false),
      new RegistrationWrapper("part_hammer_head", MATERIALS, false),
      new RegistrationWrapper("part_bow_limb", MATERIALS, false),
      new RegistrationWrapper("part_sickle_blade", MATERIALS, true),
      new RegistrationWrapper("part_rod", MATERIALS, false),
      new RegistrationWrapper("part_shear_blades", MATERIALS, false),
      new RegistrationWrapper("part_ring", MATERIALS, false),
      new RegistrationWrapper("part_paxel_head", MATERIALS, true),
      new RegistrationWrapper("part_knife_blade", MATERIALS, true)
  };

  @Override
  public void onRegisterItemEvent(RegistryEvent.Register<Item> event) {

    IForgeRegistry<Item> registry = event.getRegistry();

    for (RegistrationWrapper registrationWrapper : REGISTRATION_WRAPPERS) {
      ItemMaterialPart item = new ItemMaterialPart(MOD_ID, CREATIVE_TAB, registrationWrapper.name, registrationWrapper.materials);
      registrationWrapper.item = item;
      registry.register(item);
    }
  }

  @Override
  public void onClientRegisterModelsEvent(ModelRegistryEvent event) {

    for (RegistrationWrapper wrapper : REGISTRATION_WRAPPERS) {

      if (wrapper.highlighted) {
        this.registerModelsWithHighlight(wrapper.name, wrapper.item, wrapper.materials);

      } else {
        this.registerModels(wrapper.name, wrapper.item, wrapper.materials);
      }
    }
  }

  private void registerModels(
      String name,
      ItemMaterialPart item,
      EnumMaterial[] materials
  ) {

    for (int i = 0; i < materials.length; i++) {
      this.registerModel(name, item, i);
    }
  }

  private void registerModelsWithHighlight(
      String name,
      ItemMaterialPart item,
      EnumMaterial[] materials
  ) {

    for (int i = 0; i < materials.length; i++) {
      String resourcePath = materials[i].isHighlighted() ? name + "_highlight" : name;
      this.registerModel(resourcePath, item, i);
    }
  }

  private void registerModel(String name, ItemMaterialPart item, int i) {

    ResourceLocation location = new ResourceLocation(ModPWCustom.MOD_ID, name);
    ModelResourceLocation modelResourceLocation = new ModelResourceLocation(location, "inventory");
    ModelRegistrationHelper.registerItemModel(item, i, modelResourceLocation);
  }

  @Override
  public void onClientInitializationEvent(FMLInitializationEvent event) {

    ItemColors itemColors = Minecraft.getMinecraft().getItemColors();

    for (RegistrationWrapper wrapper : REGISTRATION_WRAPPERS) {
      itemColors.registerItemColorHandler(
          (stack, tintIndex) -> (tintIndex == 0) ? wrapper.materials[stack.getMetadata()].getColor() : 0xFFFFFF,
          wrapper.item
      );
    }
  }

  private static class RegistrationWrapper {

    private String name;
    private EnumMaterial[] materials;
    private ItemMaterialPart item;
    private boolean highlighted;

    private RegistrationWrapper(
        String name,
        EnumMaterial[] materials,
        boolean highlighted
    ) {

      this.name = name;
      this.materials = materials;
      this.highlighted = highlighted;
    }
  }

}























