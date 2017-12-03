package com.sudoplay.mc.pwcustom;

import com.sudoplay.mc.pwcustom.init.ModItems;
import com.sudoplay.mc.pwcustom.integration.CraftTweakerPlugin;
import com.sudoplay.mc.pwcustom.proxy.ProxyCommon;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
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
    dependencies = "after:*"
)
public class ModPWCustom {

  public static final String MOD_ID = "pwcustom";
  public static final String VERSION = "@@VERSION@@";
  public static final String NAME = "PlanesWalker Modpack Custom Content";
  public static final String PROXY_CLIENT = "com.sudoplay.mc.pwcustom.proxy.ProxyClient";
  public static final String PROXY_SERVER = "com.sudoplay.mc.pwcustom.proxy.ProxyServer";

  public static final boolean IS_DEV = VERSION.equals("@@" + "VERSION" + "@@");

  @SuppressWarnings("unused")
  @Mod.Instance
  public static ModPWCustom INSTANCE;

  @SidedProxy(clientSide = PROXY_CLIENT, serverSide = PROXY_SERVER)
  public static ProxyCommon PROXY;

  public static final CreativeTabs CREATIVE_TAB = new CreativeTabs(MOD_ID) {

    @Override
    public ItemStack getTabIconItem() {

      return new ItemStack(ModItems.PORTAL_WAND, 1, 0);
    }
  };

  public static Logger LOG;

  @Mod.EventHandler
  protected void onServerStarting(FMLServerStartingEvent event) {

    if (IS_DEV) {
      //event.registerServerCommand(new CommandTeleportDimension());
    }
  }

  @Mod.EventHandler
  protected void onPreInitialization(FMLPreInitializationEvent event) {

    LOG = event.getModLog();
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
      CraftTweakerPlugin.apply();
    }
  }

}
