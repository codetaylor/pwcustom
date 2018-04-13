package com.sudoplay.mc.pwcustom.modules.veins;

import com.codetaylor.mc.athenaeum.module.ModuleBase;
import com.codetaylor.mc.athenaeum.registry.Registry;
import com.codetaylor.mc.athenaeum.util.ModelRegistrationHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sudoplay.mc.pwcustom.ModPWCustom;
import com.sudoplay.mc.pwcustom.modules.veins.block.BlockDenseOreStone;
import com.sudoplay.mc.pwcustom.modules.veins.block.BlockGravelOre;
import com.sudoplay.mc.pwcustom.modules.veins.data.VeinData;
import com.sudoplay.mc.pwcustom.modules.veins.data.VeinDataList;
import com.sudoplay.mc.pwcustom.modules.veins.data.VeinDataSelector;
import com.sudoplay.mc.pwcustom.modules.veins.parse.VeinDataParser;
import com.sudoplay.mc.pwcustom.modules.veins.world.WorldGenLargeVein;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ModuleVeins
    extends ModuleBase {

  public static final String MOD_ID = ModPWCustom.MOD_ID;
  public static final CreativeTabs CREATIVE_TAB = ModPWCustom.CREATIVE_TAB;

  public static Logger LOGGER;
  public static Path DATA_PATH;

  private static final Gson GSON;

  private List<VeinDataList> veinDataLists;

  static {
    GSON = new GsonBuilder().setPrettyPrinting().create();
  }

  public static class Blocks {

    public static final BlockDenseOreStone DENSE_ORE_STONE = new BlockDenseOreStone();
    public static final BlockGravelOre GRAVEL_ORE = new BlockGravelOre();
  }

  public ModuleVeins() {

    super(0, MOD_ID);
    this.setRegistry(new Registry(MOD_ID, CREATIVE_TAB));
    this.enableAutoRegistry();

    this.veinDataLists = new ArrayList<>();
  }

  @Override
  public void onRegister(Registry registry) {

    registry.registerBlockWithItem(Blocks.DENSE_ORE_STONE, "ore_dense_stone");
    registry.registerBlockWithItem(Blocks.GRAVEL_ORE, "ore_gravel");
  }

  @Override
  public void onClientRegister(Registry registry) {

    registry.registerClientModelRegistrationStrategy(() -> {

      ModelRegistrationHelper.registerVariantBlockItemModels(
          Blocks.DENSE_ORE_STONE.getDefaultState(),
          BlockDenseOreStone.VARIANT
      );

      ModelRegistrationHelper.registerVariantBlockItemModels(
          Blocks.GRAVEL_ORE.getDefaultState(),
          BlockGravelOre.VARIANT
      );
    });
  }

  @Override
  public void onPreInitializationEvent(FMLPreInitializationEvent event) {

    super.onPreInitializationEvent(event);

    LOGGER = LogManager.getLogger(MOD_ID + "." + this.getClass().getSimpleName());

    File configDir = event.getModConfigurationDirectory();
    DATA_PATH = Paths.get(configDir.toPath().resolve(MOD_ID).toString(), "veins");

    try {
      Files.createDirectories(DATA_PATH);

    } catch (IOException e) {
      LOGGER.error("", e);
    }

    if (!Files.exists(DATA_PATH)) {

      try {
        Files.createDirectories(DATA_PATH);
        LOGGER.info("Created path: " + DATA_PATH);

      } catch (Exception e) {
        LOGGER.error("Unable to create path: " + DATA_PATH, e);
        return;
      }
    }

    if (!Files.isDirectory(DATA_PATH)) {
      LOGGER.error("Not a directory: " + DATA_PATH);
      return;
    }

    DirectoryStream<Path> stream;

    try {
      stream = Files.newDirectoryStream(
          DATA_PATH,
          entry -> Files.isRegularFile(entry) && entry.toFile().getName().endsWith(".json")
      );

    } catch (Exception e) {
      LOGGER.error("Unable to load json files in path: " + DATA_PATH, e);
      return;
    }

    List<Path> jsonFiles = new ArrayList<>();

    for (Path pathFile : stream) {
      jsonFiles.add(pathFile);
      LOGGER.info("Located data file: " + DATA_PATH.relativize(pathFile));
    }

    for (Path jsonFile : jsonFiles) {

      try {
        VeinDataList veinDataList = GSON.fromJson(new FileReader(jsonFile.toFile()), VeinDataList.class);
        this.veinDataLists.add(veinDataList);
        LOGGER.info("Data file loaded: " + DATA_PATH.relativize(jsonFile).toString());

      } catch (Exception e) {
        LOGGER.error("Unable to load data file: " + DATA_PATH.relativize(jsonFile).toString(), e);
      }
    }
  }

  @Override
  public void onLoadCompleteEvent(FMLLoadCompleteEvent event) {

    super.onLoadCompleteEvent(event);

    VeinDataParser parser = new VeinDataParser();
    List<VeinData> parsedVeinDataList = new ArrayList<>();

    for (VeinDataList veinDataList : this.veinDataLists) {
      parser.parse(veinDataList, parsedVeinDataList);
    }

    Random random = new Random();

    GameRegistry.registerWorldGenerator(
        new WorldGenLargeVein(
            new VeinDataSelector(
                parsedVeinDataList,
                random
            ),
            random
        ),
        1000
    );
  }

}
