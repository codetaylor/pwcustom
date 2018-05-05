package com.sudoplay.mc.pwcustom.modules.casts;

import com.codetaylor.mc.athenaeum.module.ModuleBase;
import com.codetaylor.mc.athenaeum.util.ModelRegistrationHelper;
import com.sudoplay.mc.pwcustom.ModPWCustom;
import com.sudoplay.mc.pwcustom.modules.casts.item.ItemCast;
import com.sudoplay.mc.pwcustom.modules.casts.item.ItemCastClay;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;

import static com.sudoplay.mc.pwcustom.modules.casts.ModuleCasts.Items.CAST;
import static com.sudoplay.mc.pwcustom.modules.casts.ModuleCasts.Items.CAST_CLAY;
import static com.sudoplay.mc.pwcustom.modules.casts.ModuleCasts.Items.CAST_CLAY_UNFIRED;

public class ModuleCasts
    extends ModuleBase {

  public static final String MOD_ID = ModPWCustom.MOD_ID;
  public static final CreativeTabs CREATIVE_TAB = ModPWCustom.CREATIVE_TAB;

  public ModuleCasts() {

    super(0, MOD_ID);
  }

  @SuppressWarnings("WeakerAccess")
  public static class Items {

    public static final ItemCast CAST = new ItemCast();
    public static final ItemCastClay CAST_CLAY = new ItemCastClay("cast_clay");
    public static final ItemCastClay CAST_CLAY_UNFIRED = new ItemCastClay("cast_clay_unfired");

  }

  @Override
  public void onRegisterItemEvent(RegistryEvent.Register<Item> event) {

    event.getRegistry().registerAll(
        Items.CAST,
        Items.CAST_CLAY,
        Items.CAST_CLAY_UNFIRED
    );
  }

  @Override
  public void onClientRegisterModelsEvent(ModelRegistryEvent event) {

    ModelRegistrationHelper.registerVariantItemModels(
        CAST,
        "variant",
        ItemCast.EnumType.values()
    );

    ModelRegistrationHelper.registerVariantItemModels(
        CAST_CLAY,
        "variant",
        ItemCastClay.EnumType.values()
    );

    ModelRegistrationHelper.registerVariantItemModels(
        CAST_CLAY_UNFIRED,
        "variant",
        ItemCastClay.EnumType.values()
    );
  }
}
