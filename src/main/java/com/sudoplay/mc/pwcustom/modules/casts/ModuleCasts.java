package com.sudoplay.mc.pwcustom.modules.casts;

import com.sudoplay.mc.pwcustom.ModPWCustom;
import com.sudoplay.mc.pwcustom.lib.module.IModule;
import com.sudoplay.mc.pwcustom.modules.casts.item.ItemCast;
import com.sudoplay.mc.pwcustom.lib.util.ModelRegistrationUtil;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModuleCasts
    implements IModule {

  @SuppressWarnings("WeakerAccess")
  public static class Items {

    @GameRegistry.ObjectHolder(ModPWCustom.MOD_ID + ":" + ItemCast.NAME)
    public static final ItemCast CAST;

    static {
      CAST = null;
    }
  }

  @Override
  public void onRegisterItemsEvent(RegistryEvent.Register<Item> event) {

    event.getRegistry().registerAll(
        new ItemCast()
    );
  }

  @Override
  public void onClientRegisterModelsEvent(ModelRegistryEvent event) {

    ModelRegistrationUtil.registerVariantItemModels(
        Items.CAST,
        "variant",
        ItemCast.EnumType.values()
    );
  }
}
