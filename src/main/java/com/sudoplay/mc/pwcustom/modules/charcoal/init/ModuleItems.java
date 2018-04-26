package com.sudoplay.mc.pwcustom.modules.charcoal.init;

import com.codetaylor.mc.athenaeum.registry.Registry;
import com.codetaylor.mc.athenaeum.util.ModelRegistrationHelper;
import com.sudoplay.mc.pwcustom.modules.charcoal.item.ItemBowDrill;
import com.sudoplay.mc.pwcustom.modules.charcoal.item.ItemMaterial;
import com.sudoplay.mc.pwcustom.modules.charcoal.item.ItemQuicklime;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemDoor;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModuleItems {

  public static final ItemMaterial MATERIAL = new ItemMaterial();
  public static final Item QUICKLIME = new ItemQuicklime();
  public static final ItemBowDrill BOW_DRILL = new ItemBowDrill();
  public static final ItemDoor REFRACTORY_DOOR = new ItemDoor(ModuleBlocks.REFRACTORY_DOOR);

  public static void onRegister(Registry registry) {

    registry.registerItem(ModuleItems.MATERIAL, ItemMaterial.NAME);
    registry.registerItem(ModuleItems.QUICKLIME, ItemQuicklime.NAME);
    registry.registerItem(ModuleItems.BOW_DRILL, ItemBowDrill.NAME);
    registry.registerItem(ModuleItems.REFRACTORY_DOOR, ModuleBlocks.REFRACTORY_DOOR.getRegistryName());
    registry.registerItem(new ItemBlock(ModuleBlocks.KILN), ModuleBlocks.KILN.getRegistryName());
  }

  @SideOnly(Side.CLIENT)
  public static void onClientRegister(Registry registry) {

    registry.registerClientModelRegistrationStrategy(() -> {

      ModelRegistrationHelper.registerItemModels(
          ModuleItems.QUICKLIME,
          ModuleItems.BOW_DRILL,
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
