package com.sudoplay.mc.pwcustom.modules.charcoal.init;

import com.codetaylor.mc.athenaeum.registry.Registry;
import com.codetaylor.mc.athenaeum.util.ModelRegistrationHelper;
import com.sudoplay.mc.pwcustom.modules.charcoal.item.ItemBowDrill;
import com.sudoplay.mc.pwcustom.modules.charcoal.item.ItemQuicklime;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModuleItems {

  public static final Item PIT_ASH = new Item();
  public static final Item COAL_COKE = new Item();
  public static final Item STRAW = new Item();
  public static final Item FLINT_CLAY_BALL = new Item();
  public static final Item REFRACTORY_CLAY_BALL = new Item();
  public static final Item REFRACTORY_BRICK = new Item();
  public static final Item POTTERY_FRAGMENTS = new Item();
  public static final Item POTTERY_SHARD = new Item();
  public static final Item QUICKLIME = new ItemQuicklime();
  public static final Item SLAKED_LIME = new Item();
  public static final ItemBowDrill BOW_DRILL = new ItemBowDrill();

  public static void onRegister(Registry registry) {

    registry.registerItem(ModuleItems.PIT_ASH, "pit_ash");
    registry.registerItem(ModuleItems.COAL_COKE, "coal_coke");
    registry.registerItem(ModuleItems.STRAW, "straw");
    registry.registerItem(ModuleItems.FLINT_CLAY_BALL, "flint_clay_ball");
    registry.registerItem(ModuleItems.REFRACTORY_CLAY_BALL, "refractory_clay_ball");
    registry.registerItem(ModuleItems.REFRACTORY_BRICK, "refractory_brick");
    registry.registerItem(ModuleItems.POTTERY_FRAGMENTS, "pottery_fragments");
    registry.registerItem(ModuleItems.POTTERY_SHARD, "pottery_shard");
    registry.registerItem(ModuleItems.QUICKLIME, "quicklime");
    registry.registerItem(ModuleItems.SLAKED_LIME, "slaked_lime");
    registry.registerItem(ModuleItems.BOW_DRILL, ItemBowDrill.NAME);
  }

  @SideOnly(Side.CLIENT)
  public static final void onClientRegister(Registry registry) {

    registry.registerClientModelRegistrationStrategy(() -> {

      ModelRegistrationHelper.registerItemModels(
          ModuleItems.PIT_ASH,
          ModuleItems.COAL_COKE,
          ModuleItems.STRAW,
          ModuleItems.REFRACTORY_BRICK,
          ModuleItems.REFRACTORY_CLAY_BALL,
          ModuleItems.FLINT_CLAY_BALL,
          ModuleItems.POTTERY_FRAGMENTS,
          ModuleItems.POTTERY_SHARD,
          ModuleItems.QUICKLIME,
          ModuleItems.SLAKED_LIME,
          ModuleItems.BOW_DRILL
      );
    });
  }
}
