package com.sudoplay.mc.pwcustom.modules.veins;

import com.codetaylor.mc.athenaeum.util.WeightedPicker;
import com.sudoplay.mc.pwcustom.modules.veins.data.EnumListType;
import com.sudoplay.mc.pwcustom.modules.veins.data.VeinData;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class VeinDataSelector {

  private final Map<Integer, Map<ResourceLocation, WeightedPicker<VeinData>>> pickerCache;
  private final List<VeinData> veinDataList;
  private final Random random;

  public VeinDataSelector(List<VeinData> veinDataList, Random random) {

    this.pickerCache = new HashMap<>();
    this.veinDataList = veinDataList;
    this.random = random;
  }

  @Nullable
  public VeinData selectVeinData(int dimensionId, ResourceLocation biome) {

    Map<ResourceLocation, WeightedPicker<VeinData>> weightedPickerMap = this.pickerCache.computeIfAbsent(
        dimensionId,
        k -> new HashMap<>()
    );

    WeightedPicker<VeinData> weightedPicker = weightedPickerMap.get(biome);

    if (weightedPicker == null) {
      weightedPicker = new WeightedPicker<>(this.random);
      weightedPickerMap.put(biome, weightedPicker);

      veinData:
      for (VeinData veinData : this.veinDataList) {

        if (veinData.dimensions.ids.length > 0) {

          for (int id : veinData.dimensions.ids) {

            if (dimensionId == id) {

              if (veinData.dimensions.type == EnumListType.BLACKLIST) {
                continue veinData;

              } else if (veinData.dimensions.type == EnumListType.WHITELIST) {
                break;
              }
            }
          }
        }

        if (veinData.biomes.ids.size() > 0) {

          if (veinData.biomes.type == EnumListType.BLACKLIST) {

            if (veinData.biomes.ids.contains(biome.toString())) {
              continue;
            }

          } else if (veinData.biomes.type == EnumListType.WHITELIST) {

            if (!veinData.biomes.ids.contains(biome.toString())) {
              continue;
            }
          }
        }

        weightedPicker.add(veinData.weight, veinData);
      }
    }

    if (weightedPicker.getSize() == 0) {
      return null;
    }

    return weightedPicker.get();
  }
}
