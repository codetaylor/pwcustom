package com.sudoplay.mc.pwcustom.modules.craftingparts;

import com.codetaylor.mc.athenaeum.helper.ModelRegistrationHelper;
import com.codetaylor.mc.athenaeum.module.ModuleBase;
import com.sudoplay.mc.pwcustom.ModPWCustom;
import com.sudoplay.mc.pwcustom.modules.craftingparts.item.ItemCraftingPart;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModuleCraftingParts
    extends ModuleBase {

  public static final String MOD_ID = ModPWCustom.MOD_ID;
  public static final CreativeTabs CREATIVE_TAB = ModPWCustom.CREATIVE_TAB;

  public ModuleCraftingParts() {

    super(0);
  }

  @SuppressWarnings("WeakerAccess")
  public static class Items {

    @GameRegistry.ObjectHolder(ModPWCustom.MOD_ID + ":" + ItemCraftingPart.NAME)
    public static final ItemCraftingPart CRAFTING_PART;

    static {
      CRAFTING_PART = null;
    }
  }

  @Override
  public void onRegisterItemEvent(RegistryEvent.Register<Item> event) {

    event.getRegistry().registerAll(
        new ItemCraftingPart()
    );
  }

  @Override
  public void onClientRegisterModelsEvent(ModelRegistryEvent event) {

    ModelRegistrationHelper.registerVariantItemModels(
        Items.CRAFTING_PART,
        "variant",
        ItemCraftingPart.EnumType.values()
    );
  }
}
