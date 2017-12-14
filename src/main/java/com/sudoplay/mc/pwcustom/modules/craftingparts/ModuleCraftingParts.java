package com.sudoplay.mc.pwcustom.modules.craftingparts;

import com.sudoplay.mc.pwcustom.ModPWCustom;
import com.sudoplay.mc.pwcustom.lib.module.ModuleBase;
import com.sudoplay.mc.pwcustom.modules.craftingparts.item.ItemCraftingPart;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModuleCraftingParts
    extends ModuleBase {

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

    this.getModelRegistrationHelper().registerVariantItemModels(
        Items.CRAFTING_PART,
        "variant",
        ItemCraftingPart.EnumType.values()
    );
  }
}
