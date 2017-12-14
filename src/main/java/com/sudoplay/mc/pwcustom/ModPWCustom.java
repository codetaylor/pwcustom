package com.sudoplay.mc.pwcustom;

import com.sudoplay.mc.pwcustom.integration.PluginCraftTweaker;
import com.sudoplay.mc.pwcustom.lib.module.ModuleRegistry;
import com.sudoplay.mc.pwcustom.modules.blocks.ModuleBlocks;
import com.sudoplay.mc.pwcustom.modules.craftingparts.ModuleCraftingParts;
import com.sudoplay.mc.pwcustom.modules.enchanting.ModuleEnchanting;
import com.sudoplay.mc.pwcustom.modules.casts.ModuleCasts;
import com.sudoplay.mc.pwcustom.modules.mortar.ModuleMortar;
import com.sudoplay.mc.pwcustom.modules.portals.ModulePortals;
import com.sudoplay.mc.pwcustom.modules.sawing.ModuleSawing;
import com.sudoplay.mc.pwcustom.modules.toolparts.ModuleToolParts;
import com.sudoplay.mc.pwcustom.modules.workbench.ModuleWorkbench;
import com.sudoplay.mc.pwcustom.proxy.ProxyCommon;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import org.apache.logging.log4j.Logger;

@Mod(
    modid = ModPWCustom.MOD_ID,
    version = ModPWCustom.VERSION,
    name = ModPWCustom.NAME,
    dependencies = ModPWCustom.DEPENDENCIES
)
public class ModPWCustom {

  public static final String MOD_ID = Reference.MOD_ID;
  public static final String VERSION = Reference.VERSION;
  public static final String NAME = Reference.NAME;
  public static final String PROXY_CLIENT = Reference.PROXY_CLIENT;
  public static final String PROXY_SERVER = Reference.PROXY_SERVER;
  public static final String DEPENDENCIES = Reference.DEPENDENCIES;

  public static final boolean IS_DEV = Reference.IS_DEV;

  @SuppressWarnings("unused")
  @Mod.Instance
  public static ModPWCustom INSTANCE;

  @SidedProxy(clientSide = PROXY_CLIENT, serverSide = PROXY_SERVER)
  public static ProxyCommon PROXY;

  public static final CreativeTabs CREATIVE_TAB = new CreativeTabs(MOD_ID) {

    @Override
    public ItemStack getTabIconItem() {

      return new ItemStack(ModulePortals.Items.PORTAL_WAND, 1, 0);
    }
  };

  public static Logger LOG;

  public static final ModuleRegistry MODULE_REGISTRY = new ModuleRegistry();

  @Mod.EventHandler
  protected void onServerStarting(FMLServerStartingEvent event) {

    //
  }

  @Mod.EventHandler
  protected void onPreInitialization(FMLPreInitializationEvent event) {

    LOG = event.getModLog();
    MODULE_REGISTRY.registerModules(
        new ModuleWorkbench(),
        new ModulePortals(),
        new ModuleSawing(),
        new ModuleToolParts(),
        new ModuleCasts(),
        new ModuleEnchanting(),
        new ModuleBlocks(),
        new ModuleCraftingParts(),
        new ModuleMortar()
    );
    PROXY.onPreInitialization(event);
  }

  @Mod.EventHandler
  protected void onInitialization(FMLInitializationEvent event) {

    PROXY.onInitialization(event);
  }

  @Mod.EventHandler
  protected void onPostInitialization(FMLPostInitializationEvent event) {

    PROXY.onPostInitialization(event);
  }

  @Mod.EventHandler
  protected void onLoadComplete(FMLLoadCompleteEvent event) {

    if (Loader.isModLoaded("crafttweaker")) {
      PluginCraftTweaker.apply();
    }
  }

}
