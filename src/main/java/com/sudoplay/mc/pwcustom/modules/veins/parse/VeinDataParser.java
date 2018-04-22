package com.sudoplay.mc.pwcustom.modules.veins.parse;

import com.codetaylor.mc.athenaeum.parser.recipe.item.MalformedRecipeItemException;
import com.codetaylor.mc.athenaeum.parser.recipe.item.ParseResult;
import com.codetaylor.mc.athenaeum.parser.recipe.item.RecipeItemParser;
import com.sudoplay.mc.pwcustom.modules.veins.ModuleVeins;
import com.sudoplay.mc.pwcustom.modules.veins.data.VeinData;
import com.sudoplay.mc.pwcustom.modules.veins.data.VeinDataList;
import com.sudoplay.mc.pwcustom.util.Util;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;

public class VeinDataParser {

  public List<VeinData> parse(VeinDataList veinDataList, List<VeinData> result) {

    for (VeinData data : veinDataList.veins) {
      RecipeItemParser parser = new RecipeItemParser();

      try {
        this.parseToReplace(data, parser);
        this.parseReplaceWith(data, parser);
        this.parseIndicator(data, parser);

        result.add(data);

      } catch (Throwable e) {
        ModuleVeins.LOGGER.error("", e);
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

  private void parseIndicator(VeinData data, RecipeItemParser parser) throws MalformedRecipeItemException {

    ParseResult parse = parser.parse(data.indicator);

    int meta = parse.getMeta();

    if (meta == OreDictionary.WILDCARD_VALUE) {
      throw new IllegalArgumentException("Can't use wildcard for indicator value");
    }

    ResourceLocation resourceLocation = new ResourceLocation(parse.getDomain(), parse.getPath());
    Block block = ForgeRegistries.BLOCKS.getValue(resourceLocation);

    if (block == null) {
      throw new IllegalArgumentException("Unable to locate block for [" + resourceLocation + "]");
    }

    data._indicator = block.getStateFromMeta(meta);
  }

  private void parseToReplace(VeinData data, RecipeItemParser parser) throws MalformedRecipeItemException {

    data._toReplace = Util.parseBlockStringWithWildcard(data.toReplace, parser);
  }

}
