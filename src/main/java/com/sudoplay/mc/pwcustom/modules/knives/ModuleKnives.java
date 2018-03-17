package com.sudoplay.mc.pwcustom.modules.knives;

import com.codetaylor.mc.athenaeum.module.ModuleBase;
import com.codetaylor.mc.athenaeum.reference.EnumMaterial;
import com.codetaylor.mc.athenaeum.util.ModelRegistrationHelper;
import com.sudoplay.mc.pwcustom.ModPWCustom;
import com.sudoplay.mc.pwcustom.modules.knives.item.ItemKnife;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

public class ModuleKnives
    extends ModuleBase {

  public static final String MOD_ID = ModPWCustom.MOD_ID;

  public ModuleKnives() {

    super(0, MOD_ID);
  }

  public static final EnumMaterial[] MATERIALS = new EnumMaterial[]{
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

  public static class Items {

    public static final ItemKnife[] KNIVES = new ItemKnife[MATERIALS.length];

  }

  @Override
  public void onRegisterItemEvent(RegistryEvent.Register<Item> event) {

    for (int i = 0; i < MATERIALS.length; i++) {
      Items.KNIVES[i] = new ItemKnife("knife_" + MATERIALS[i].getName(), MATERIALS[i]);
    }

    event.getRegistry().registerAll(Items.KNIVES);
  }

  @Override
  public void onClientRegisterModelsEvent(ModelRegistryEvent event) {

    for (ItemKnife knife : Items.KNIVES) {
      String resourcePath = knife.getMaterial().isHighlighted() ? "knife_highlight" : "knife";
      ResourceLocation location = new ResourceLocation(ModPWCustom.MOD_ID, resourcePath);
      ModelResourceLocation modelResourceLocation = new ModelResourceLocation(location, "inventory");
      ModelRegistrationHelper.registerItemModel(knife, 0, modelResourceLocation);
    }
  }

  @Override
  public void onClientInitializationEvent(FMLInitializationEvent event) {

    ItemColors itemColors = Minecraft.getMinecraft().getItemColors();

    itemColors.registerItemColorHandler(
        (stack, tintIndex) -> (tintIndex == 0) ? ((ItemKnife) stack.getItem()).getMaterial().getColor() : 0xFFFFFF,
        Items.KNIVES
    );
  }
}
