package com.sudoplay.mc.pwcustom.modules.tools;

import com.codetaylor.mc.athenaeum.helper.ModelRegistrationHelper;
import com.codetaylor.mc.athenaeum.module.ModuleBase;
import com.codetaylor.mc.athenaeum.registry.Registry;
import com.sudoplay.mc.pwcustom.ModPWCustom;
import com.sudoplay.mc.pwcustom.modules.tools.item.*;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;

public class ModuleTools
    extends ModuleBase {

  public static final String MOD_ID = ModPWCustom.MOD_ID;
  public static final CreativeTabs CREATIVE_TAB = ModPWCustom.CREATIVE_TAB;

  public ModuleTools() {

    super(0, MOD_ID);
    this.setRegistry(new Registry(MOD_ID, CREATIVE_TAB));
    this.enableAutoRegistry();
  }

  public static class Items {

    public static final ItemCrudeAxe CRUDE_AXE = new ItemCrudeAxe();
    public static final ItemCrudeHoe CRUDE_HOE = new ItemCrudeHoe();
    public static final ItemCrudePickaxe CRUDE_PICKAXE = new ItemCrudePickaxe();
    public static final ItemCrudeShovel CRUDE_SHOVEL = new ItemCrudeShovel();

    public static final ItemBoneAxe BONE_AXE = new ItemBoneAxe();
    public static final ItemBoneHoe BONE_HOE = new ItemBoneHoe();
    public static final ItemBonePickaxe BONE_PICKAXE = new ItemBonePickaxe();
    public static final ItemBoneShovel BONE_SHOVEL = new ItemBoneShovel();
  }

  @Override
  public void onRegister(Registry registry) {

    registry.registerItem(Items.CRUDE_AXE, new ResourceLocation(MOD_ID, "crude_axe"));
    registry.registerItem(Items.CRUDE_HOE, new ResourceLocation(MOD_ID, "crude_hoe"));
    registry.registerItem(Items.CRUDE_PICKAXE, new ResourceLocation(MOD_ID, "crude_pickaxe"));
    registry.registerItem(Items.CRUDE_SHOVEL, new ResourceLocation(MOD_ID, "crude_shovel"));

    registry.registerItem(Items.BONE_AXE, new ResourceLocation(MOD_ID, "bone_axe"));
    registry.registerItem(Items.BONE_HOE, new ResourceLocation(MOD_ID, "bone_hoe"));
    registry.registerItem(Items.BONE_PICKAXE, new ResourceLocation(MOD_ID, "bone_pickaxe"));
    registry.registerItem(Items.BONE_SHOVEL, new ResourceLocation(MOD_ID, "bone_shovel"));
  }

  @Override
  public void onClientRegisterModelsEvent(ModelRegistryEvent event) {

    super.onClientRegisterModelsEvent(event);

    ModelRegistrationHelper.registerItemModels(
        Items.CRUDE_AXE,
        Items.CRUDE_HOE,
        Items.CRUDE_PICKAXE,
        Items.CRUDE_SHOVEL,

        Items.BONE_AXE,
        Items.BONE_HOE,
        Items.BONE_PICKAXE,
        Items.BONE_SHOVEL
    );
  }
}
