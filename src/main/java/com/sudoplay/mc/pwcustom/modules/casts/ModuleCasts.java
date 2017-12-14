package com.sudoplay.mc.pwcustom.modules.casts;

import com.sudoplay.mc.pwcustom.ModPWCustom;
import com.sudoplay.mc.pwcustom.lib.module.ModuleBase;
import com.sudoplay.mc.pwcustom.modules.casts.item.ItemCast;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModuleCasts
    extends ModuleBase {

  @SuppressWarnings("WeakerAccess")
  public static class Items {

    @GameRegistry.ObjectHolder(ModPWCustom.MOD_ID + ":" + ItemCast.NAME)
    public static final ItemCast CAST;

    static {
      CAST = null;
    }
  }

  @Override
  public void onRegisterItemEvent(RegistryEvent.Register<Item> event) {

    event.getRegistry().registerAll(
        new ItemCast()
    );
  }

  @Override
  public void onClientRegisterModelsEvent(ModelRegistryEvent event) {

    this.getModelRegistrationHelper().registerVariantItemModels(
        Items.CAST,
        "variant",
        ItemCast.EnumType.values()
    );
  }
}
