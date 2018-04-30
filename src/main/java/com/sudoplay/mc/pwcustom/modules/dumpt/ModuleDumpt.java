package com.sudoplay.mc.pwcustom.modules.dumpt;

import com.codetaylor.mc.athenaeum.module.ModuleBase;
import com.sudoplay.mc.pwcustom.ModPWCustom;
import com.sudoplay.mc.pwcustom.modules.dumpt.command.Command;
import com.sudoplay.mc.pwcustom.modules.dumpt.profile.ExportProfileBiome;
import com.sudoplay.mc.pwcustom.modules.dumpt.profile.ExportProfileFood;
import com.sudoplay.mc.pwcustom.modules.dumpt.spi.profile.IExportProfile;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ModuleDumpt
    extends ModuleBase {

  public static final String MOD_ID = ModPWCustom.MOD_ID;
  public static final CreativeTabs CREATIVE_TAB = ModPWCustom.CREATIVE_TAB;

  public static Logger LOGGER;
  public static Path DATA_PATH;

  public ModuleDumpt() {

    super(0, MOD_ID);
  }

  @Override
  public void onPreInitializationEvent(FMLPreInitializationEvent event) {

    super.onPreInitializationEvent(event);

    LOGGER = LogManager.getLogger(MOD_ID + "." + this.getClass().getSimpleName());

    File configDir = event.getModConfigurationDirectory();
    DATA_PATH = Paths.get(configDir.toPath().resolve(MOD_ID).toString(), "dumpt");
  }

  @Override
  public void onServerStartingEvent(FMLServerStartingEvent event) {

    super.onServerStartingEvent(event);

    Map<String, IExportProfile> exportProfileMap = new HashMap<>();

    exportProfileMap.put("food", new ExportProfileFood());
    exportProfileMap.put("biome", new ExportProfileBiome());

    event.registerServerCommand(new Command(DATA_PATH, LOGGER, exportProfileMap));
  }
}
