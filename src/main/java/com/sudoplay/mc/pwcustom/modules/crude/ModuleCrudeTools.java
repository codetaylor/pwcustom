package com.sudoplay.mc.pwcustom.modules.crude;

import com.codetaylor.mc.athenaeum.helper.ModelRegistrationHelper;
import com.codetaylor.mc.athenaeum.module.ModuleBase;
import com.codetaylor.mc.athenaeum.registry.Registry;
import com.sudoplay.mc.pwcustom.ModPWCustom;
import com.sudoplay.mc.pwcustom.modules.crude.item.ItemCrudeAxe;
import com.sudoplay.mc.pwcustom.modules.crude.item.ItemCrudeHoe;
import com.sudoplay.mc.pwcustom.modules.crude.item.ItemCrudePickaxe;
import com.sudoplay.mc.pwcustom.modules.crude.item.ItemCrudeShovel;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;

public class ModuleCrudeTools
    extends ModuleBase {

  public static final String MOD_ID = ModPWCustom.MOD_ID;
  public static final CreativeTabs CREATIVE_TAB = ModPWCustom.CREATIVE_TAB;

  public ModuleCrudeTools() {

    super(0);
    this.setRegistry(new Registry(MOD_ID, CREATIVE_TAB));
    this.enableAutoRegistry();
  }

  public static class Items {

    public static final ItemCrudeAxe CRUDE_AXE = new ItemCrudeAxe();
    public static final ItemCrudeHoe CRUDE_HOE = new ItemCrudeHoe();
    public static final ItemCrudePickaxe CRUDE_PICKAXE = new ItemCrudePickaxe();
    public static final ItemCrudeShovel CRUDE_SHOVEL = new ItemCrudeShovel();
  }

  @Override
  public void onRegister(Registry registry) {

    super.onRegister(registry);

    registry.registerItem(Items.CRUDE_AXE, new ResourceLocation(MOD_ID, "crude_axe"));
    registry.registerItem(Items.CRUDE_HOE, new ResourceLocation(MOD_ID, "crude_hoe"));
    registry.registerItem(Items.CRUDE_PICKAXE, new ResourceLocation(MOD_ID, "crude_pickaxe"));
    registry.registerItem(Items.CRUDE_SHOVEL, new ResourceLocation(MOD_ID, "crude_shovel"));
  }

  @Override
  public void onClientRegisterModelsEvent(ModelRegistryEvent event) {

    super.onClientRegisterModelsEvent(event);

    ModelRegistrationHelper.registerItemModels(
        Items.CRUDE_AXE,
        Items.CRUDE_HOE,
        Items.CRUDE_PICKAXE,
        Items.CRUDE_SHOVEL
    );
  }
}
