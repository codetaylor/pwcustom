package com.sudoplay.mc.pwcustom.modules.world;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Arrays;

public class BlockMatchEntry {

  private String domain;
  private String path;
  private int meta;
  private int[] metas;

  public BlockMatchEntry(String domain, String path, int meta, int[] metas) {

    this.domain = domain;
    this.path = path;
    this.meta = meta;
    this.metas = metas;
  }

  public boolean matches(IBlockState blockState) {

    ResourceLocation registryName = blockState.getBlock().getRegistryName();

    if (registryName == null) {
      return false;
    }

    if (!registryName.getResourceDomain().equals(this.domain)) {
      return false;
    }

    if (!registryName.getResourcePath().equals(this.path)) {
      return false;
    }

    int metaFromState = blockState.getBlock().getMetaFromState(blockState);

    if (this.meta == OreDictionary.WILDCARD_VALUE
        || this.meta == metaFromState) {
      return true;
    }

    for (int meta : this.metas) {

      if (meta == OreDictionary.WILDCARD_VALUE
          || meta == metaFromState) {
        return true;
      }
    }

    return false;
  }

  @Override
  public String toString() {

    return "BlockMatchEntry{" +
        "domain='" + domain + '\'' +
        ", path='" + path + '\'' +
        ", meta=" + meta +
        ", metas=" + Arrays.toString(metas) +
        '}';
  }
}
