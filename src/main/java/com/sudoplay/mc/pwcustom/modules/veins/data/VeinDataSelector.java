package com.sudoplay.mc.pwcustom.modules.veins.data;

import com.codetaylor.mc.athenaeum.util.WeightedPicker;
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
          if (veinData.dimensions.type == EnumListType.BLACKLIST) {
            // assert list does not contain dimension id
            for (int id : veinData.dimensions.ids) {
              if (dimensionId == id) {
                continue veinData;
              }
            }
          } else if (veinData.dimensions.type == EnumListType.WHITELIST) {
            // assert list contains dimension id
            boolean allowed = false;
            for (int id : veinData.dimensions.ids) {
              if (dimensionId == id) {
                allowed = true;
                break;
              }
            }
            if (!allowed) {
              continue; // veinData
            }
          }
        }

        if (veinData.biomes.ids.size() > 0) {
          if (veinData.biomes.type == EnumListType.BLACKLIST) {
            if (veinData.biomes.ids.contains(biome.toString())) {
              continue; // veinData
            }
          } else if (veinData.biomes.type == EnumListType.WHITELIST) {
            if (!veinData.biomes.ids.contains(biome.toString())) {
              continue; // veinData
            }
          }
        }

        // add to picker
        weightedPicker.add(veinData.weight, veinData);
      }
    }

    if (weightedPicker.getSize() == 0) {
      return null;
    }

    return weightedPicker.get();
  }
}
