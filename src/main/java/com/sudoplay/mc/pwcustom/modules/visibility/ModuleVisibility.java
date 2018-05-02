package com.sudoplay.mc.pwcustom.modules.visibility;

import com.codetaylor.mc.athenaeum.module.ModuleBase;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.sudoplay.mc.pwcustom.ModPWCustom;
import com.sudoplay.mc.pwcustom.modules.visibility.compat.jei.PluginJEI;
import net.darkhax.bookshelf.lib.ItemStackMap;
import net.darkhax.bookshelf.util.GameUtils;
import net.darkhax.bookshelf.util.PlayerUtils;
import net.darkhax.gamestages.event.StagesSyncedEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class ModuleVisibility
    extends ModuleBase {

  public static final String MOD_ID = ModPWCustom.MOD_ID;
  public static final CreativeTabs CREATIVE_TAB = ModPWCustom.CREATIVE_TAB;

  public static final ItemStackMap<String> VISIBILITY_STAGES = new ItemStackMap<>(StageCompare.INSTANCE);
  public static final ListMultimap<String, ItemStack> SORTED_STAGES = ArrayListMultimap.create();
  public static final ListMultimap<String, FluidStack> FLUID_STAGES = ArrayListMultimap.create();

  public static Logger LOGGER;

  public ModuleVisibility() {

    super(0, MOD_ID);

    this.registerIntegrationPlugin(
        "jei",
        "com.sudoplay.mc.pwcustom.modules.visibility.compat.jei.PluginJEI"
    );

    this.registerIntegrationPlugin(
        "crafttweaker",
        "com.sudoplay.mc.pwcustom.modules.visibility.compat.crafttweaker.ZenVisibilityStages"
    );
  }

  @Override
  public void onPreInitializationEvent(FMLPreInitializationEvent event) {

    super.onPreInitializationEvent(event);

    LOGGER = LogManager.getLogger(MOD_ID + "." + this.getClass().getSimpleName());

    MinecraftForge.EVENT_BUS.register(this);
  }

  @SideOnly(Side.CLIENT)
  @SubscribeEvent
  public void onClientSync(StagesSyncedEvent event) {

    if (Loader.isModLoaded("jei") && GameUtils.isClient()) {

      PluginJEI.syncHiddenItems(event.getEntityPlayer());
    }
  }

  @Override
  public void onLoadCompleteEvent(FMLLoadCompleteEvent event) {

    super.onLoadCompleteEvent(event);

    LOGGER.info("Sorting {} staged items.", VISIBILITY_STAGES.size());
    final long time = System.currentTimeMillis();

    for (final Map.Entry<ItemStack, String> entry : VISIBILITY_STAGES.entrySet()) {

      SORTED_STAGES.put(entry.getValue(), entry.getKey());
      //SORTED_ITEM_STAGES.put(entry.getKey().getItem(), new Tuple<>(entry.getKey(), entry.getValue()));
    }

    LOGGER.info(
        "Sorting complete. Found {} stages. Took {}ms",
        SORTED_STAGES.keySet().size(),
        System.currentTimeMillis() - time
    );

    if (event.getSide() == Side.CLIENT) {
      this.onClientLoadComplete();
    }
  }

  @SideOnly(Side.CLIENT)
  private void onClientLoadComplete() {

    // Add a resource reload listener to keep up to sync with JEI.
    ((IReloadableResourceManager) Minecraft.getMinecraft().getResourceManager()).registerReloadListener(listener -> {

      if (Loader.isModLoaded("jei") && GameUtils.isClient()) {

        LOGGER.info("Resyncing JEI info.");
        PluginJEI.syncHiddenItems(PlayerUtils.getClientPlayer());
      }
    });
  }
}
