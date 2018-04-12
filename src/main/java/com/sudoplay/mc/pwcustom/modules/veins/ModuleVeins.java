package com.sudoplay.mc.pwcustom.modules.veins;

import com.codetaylor.mc.athenaeum.module.ModuleBase;
import com.codetaylor.mc.athenaeum.parser.recipe.item.MalformedRecipeItemException;
import com.codetaylor.mc.athenaeum.parser.recipe.item.ParseResult;
import com.codetaylor.mc.athenaeum.parser.recipe.item.RecipeItemParser;
import com.codetaylor.mc.athenaeum.registry.Registry;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sudoplay.mc.pwcustom.ModPWCustom;
import com.sudoplay.mc.pwcustom.modules.veins.data.VeinData;
import com.sudoplay.mc.pwcustom.modules.veins.data.VeinDataList;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockStateMatcher;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
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
import java.util.function.Predicate;

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

  public ModuleVeins() {

    super(0, MOD_ID);
    this.setRegistry(new Registry(MOD_ID, CREATIVE_TAB));
    this.enableAutoRegistry();

    this.veinDataLists = new ArrayList<>();
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

    List<VeinData> parsedVeinDataList = new ArrayList<>();

    for (VeinDataList veinDataList : this.veinDataLists) {
      this.parse(veinDataList, parsedVeinDataList);
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

  private List<VeinData> parse(VeinDataList veinDataList, List<VeinData> result) {

    for (VeinData data : veinDataList.veins) {

      // TODO: parse data

      RecipeItemParser parser = new RecipeItemParser();

      try {
        this.parseToReplace(data, parser);
        this.parseReplaceWith(data, parser);

        result.add(data);

      } catch (Throwable e) {
        LOGGER.error("", e);
      }
    }

    return result;
  }

  private void parseReplaceWith(VeinData data, RecipeItemParser parser) throws MalformedRecipeItemException {

    ParseResult parse = parser.parse(data.replaceWith);

    int meta = parse.getMeta();

    if (meta == OreDictionary.WILDCARD_VALUE) {
      throw new IllegalArgumentException("Can't use wildcard for replace with value");
    }

    ResourceLocation resourceLocation = new ResourceLocation(parse.getDomain(), parse.getPath());
    Block block = ForgeRegistries.BLOCKS.getValue(resourceLocation);

    if (block == null) {
      throw new IllegalArgumentException("Unable to locate block for [" + resourceLocation + "]");
    }

    data._replaceWith = block.getStateFromMeta(meta);
  }

  private void parseToReplace(VeinData data, RecipeItemParser parser) throws MalformedRecipeItemException {

    ParseResult parse = parser.parse(data.toReplace);
    ResourceLocation resourceLocation = new ResourceLocation(parse.getDomain(), parse.getPath());
    Block block = ForgeRegistries.BLOCKS.getValue(resourceLocation);

    if (block == null) {
      throw new IllegalArgumentException("Unable to locate block for [" + resourceLocation + "]");
    }

    int meta = parse.getMeta();

    if (meta == OreDictionary.WILDCARD_VALUE) {
      data._toReplace = BlockStateMatcher.forBlock(block);

    } else {
      data._toReplace = new BlockMetaMatcher(block, meta);
    }
  }

  public static class BlockMetaMatcher
      implements Predicate<IBlockState> {

    private final Block block;
    private final int meta;

    public BlockMetaMatcher(Block block, int meta) {

      this.block = block;
      this.meta = meta;
    }

    @Override
    public boolean test(IBlockState blockState) {

      Block blockCandidate = blockState.getBlock();

      return blockCandidate == this.block
          && this.block.getStateFromMeta(this.meta) == blockState;
    }
  }

}
