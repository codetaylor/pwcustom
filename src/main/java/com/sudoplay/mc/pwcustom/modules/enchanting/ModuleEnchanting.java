package com.sudoplay.mc.pwcustom.modules.enchanting;

import com.sudoplay.mc.pwcustom.ModPWCustom;
import com.sudoplay.mc.pwcustom.modules.enchanting.event.ArcaneAffinityTooltipEventHandler;
import com.sudoplay.mc.pwcustom.modules.enchanting.item.ItemBookGoldEmbossed;
import com.sudoplay.mc.pwcustom.modules.enchanting.item.ItemTomeGoldEmbossed;
import com.sudoplay.mc.pwcustom.lib.module.IModule;
import com.sudoplay.mc.pwcustom.lib.util.ModelRegistrationUtil;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModuleEnchanting
    implements IModule {

  @SuppressWarnings("WeakerAccess")
  public static class Items {

    @GameRegistry.ObjectHolder(ModPWCustom.MOD_ID + ":" + ItemBookGoldEmbossed.NAME)
    public static final ItemBookGoldEmbossed BOOK_GOLD_EMBOSSED;

    @GameRegistry.ObjectHolder(ModPWCustom.MOD_ID + ":" + ItemTomeGoldEmbossed.NAME)
    public static final ItemTomeGoldEmbossed TOME_GOLD_EMBOSSED;

    static {
      BOOK_GOLD_EMBOSSED = null;
      TOME_GOLD_EMBOSSED = null;
    }
  }

  @Override
  public void onPreInitialization(FMLPreInitializationEvent event) {

    MinecraftForge.EVENT_BUS.register(new ArcaneAffinityTooltipEventHandler());
  }

  @Override
  public void onRegisterItemsEvent(RegistryEvent.Register<Item> event) {

    event.getRegistry().registerAll(
        new ItemBookGoldEmbossed(),
        new ItemTomeGoldEmbossed()
    );
  }

  @Override
  public void onClientRegisterModelsEvent(ModelRegistryEvent event) {

    ModelRegistrationUtil.registerItemModels(
        Items.BOOK_GOLD_EMBOSSED,
        Items.TOME_GOLD_EMBOSSED
    );
  }
}
