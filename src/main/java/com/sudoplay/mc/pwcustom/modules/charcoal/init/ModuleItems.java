package com.sudoplay.mc.pwcustom.modules.charcoal.init;

import com.codetaylor.mc.athenaeum.registry.Registry;
import com.codetaylor.mc.athenaeum.util.ModelRegistrationHelper;
import com.sudoplay.mc.pwcustom.modules.charcoal.item.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemDoor;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModuleItems {

  public static final ItemMaterial MATERIAL = new ItemMaterial();
  public static final Item QUICKLIME = new ItemQuicklime();
  public static final ItemIgniterBase BOW_DRILL = new ItemBowDrill();
  public static final ItemIgniterBase FLINT_AND_TINDER = new ItemFlintAndTinder();
  public static final ItemDoor REFRACTORY_DOOR = new ItemDoor(ModuleBlocks.REFRACTORY_DOOR);

  public static void onRegister(Registry registry) {

    registry.registerItem(ModuleItems.MATERIAL, ItemMaterial.NAME);
    registry.registerItem(ModuleItems.QUICKLIME, ItemQuicklime.NAME);
    registry.registerItem(ModuleItems.BOW_DRILL, ItemBowDrill.NAME);
    registry.registerItem(ModuleItems.FLINT_AND_TINDER, ItemFlintAndTinder.NAME);
    registry.registerItem(ModuleItems.REFRACTORY_DOOR, ModuleBlocks.REFRACTORY_DOOR.getRegistryName());
    registry.registerItem(new ItemBlock(ModuleBlocks.KILN_PIT), ModuleBlocks.KILN_PIT.getRegistryName());
  }

  @SideOnly(Side.CLIENT)
  public static void onClientRegister(Registry registry) {

    registry.registerClientModelRegistrationStrategy(() -> {

      ModelRegistrationHelper.registerItemModels(
          ModuleItems.QUICKLIME,
          ModuleItems.BOW_DRILL,
          ModuleItems.FLINT_AND_TINDER,
          ModuleItems.REFRACTORY_DOOR
      );

      ModelRegistrationHelper.registerVariantItemModels(
          ModuleItems.MATERIAL,
          "variant",
          ItemMaterial.EnumType.values()
      );
    });
  }
}
