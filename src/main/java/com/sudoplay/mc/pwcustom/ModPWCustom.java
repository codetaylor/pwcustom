package com.sudoplay.mc.pwcustom;

import com.codetaylor.mc.athenaeum.module.ModuleManager;
import com.sudoplay.mc.pwcustom.modules.block.ModuleBlock;
import com.sudoplay.mc.pwcustom.modules.casts.ModuleCasts;
import com.sudoplay.mc.pwcustom.modules.charcoal.ModuleCharcoal;
import com.sudoplay.mc.pwcustom.modules.craftingparts.ModuleCraftingParts;
import com.sudoplay.mc.pwcustom.modules.dumpt.ModuleDumpt;
import com.sudoplay.mc.pwcustom.modules.enchanting.ModuleEnchanting;
import com.sudoplay.mc.pwcustom.modules.portals.ModulePortals;
import com.sudoplay.mc.pwcustom.modules.toolparts.ModuleToolParts;
import com.sudoplay.mc.pwcustom.modules.tools.ModuleTools;
import com.sudoplay.mc.pwcustom.modules.utils.ModuleUtils;
import com.sudoplay.mc.pwcustom.modules.veins.ModuleVeins;
import com.sudoplay.mc.pwcustom.modules.visibility.ModuleVisibility;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.*;

@Mod(
    modid = ModPWCustom.MOD_ID,
    version = ModPWCustom.VERSION,
    name = ModPWCustom.NAME
    //@@DEPENDENCIES@@
)
public class ModPWCustom {

  public static final String MOD_ID = Reference.MOD_ID;
  public static final String VERSION = Reference.VERSION;
  public static final String NAME = Reference.NAME;

  @SuppressWarnings("unused")
  @Mod.Instance
  public static ModPWCustom INSTANCE;

  public static final CreativeTabs CREATIVE_TAB = new CreativeTabs(MOD_ID) {

    @Override
    public ItemStack getTabIconItem() {

      return new ItemStack(ModulePortals.Items.PORTAL_WAND, 1, 0);
    }
  };

  private final ModuleManager moduleManager;

  public ModPWCustom() {

    this.moduleManager = new ModuleManager(MOD_ID);
    this.moduleManager.registerModules(
        ModulePortals.class,
        ModuleToolParts.class,
        ModuleCasts.class,
        ModuleEnchanting.class,
        ModuleBlock.class,
        ModuleCraftingParts.class,
        ModuleTools.class,
        ModuleVeins.class,
        ModuleUtils.class,
        ModuleCharcoal.class,
        ModuleDumpt.class
        //ModuleVisibility.class
    );
  }

  @Mod.EventHandler
  public void onConstructionEvent(FMLConstructionEvent event) {

    this.moduleManager.onConstructionEvent();
    this.moduleManager.routeFMLStateEvent(event);
  }

  @Mod.EventHandler
  public void onPreInitializationEvent(FMLPreInitializationEvent event) {

    this.moduleManager.routeFMLStateEvent(event);
  }

  @Mod.EventHandler
  public void onInitializationEvent(FMLInitializationEvent event) {

    this.moduleManager.routeFMLStateEvent(event);
  }

  @Mod.EventHandler
  public void onPostInitializationEvent(FMLPostInitializationEvent event) {

    this.moduleManager.routeFMLStateEvent(event);
  }

  @Mod.EventHandler
  public void onLoadCompleteEvent(FMLLoadCompleteEvent event) {

    this.moduleManager.routeFMLStateEvent(event);
  }

  @Mod.EventHandler
  public void onServerAboutToStartEvent(FMLServerAboutToStartEvent event) {

    this.moduleManager.routeFMLStateEvent(event);
  }

  @Mod.EventHandler
  public void onServerStartingEvent(FMLServerStartingEvent event) {

    this.moduleManager.routeFMLStateEvent(event);
  }

  @Mod.EventHandler
  public void onServerStartedEvent(FMLServerStartedEvent event) {

    this.moduleManager.routeFMLStateEvent(event);
  }

  @Mod.EventHandler
  public void onServerStoppingEvent(FMLServerStoppingEvent event) {

    this.moduleManager.routeFMLStateEvent(event);
  }

  @Mod.EventHandler
  public void onServerStoppedEvent(FMLServerStoppedEvent event) {

    this.moduleManager.routeFMLStateEvent(event);
  }

}
