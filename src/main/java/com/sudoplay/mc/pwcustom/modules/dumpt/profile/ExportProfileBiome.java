package com.sudoplay.mc.pwcustom.modules.dumpt.profile;

import com.sudoplay.mc.pwcustom.modules.dumpt.serializer.SerializerForgeRegistryEntryDomain;
import com.sudoplay.mc.pwcustom.modules.dumpt.serializer.SerializerForgeRegistryEntryPath;
import com.sudoplay.mc.pwcustom.modules.dumpt.serializer.biome.*;
import com.sudoplay.mc.pwcustom.modules.dumpt.spi.line.ILineWriter;
import com.sudoplay.mc.pwcustom.modules.dumpt.spi.profile.ExportProfileBase;
import com.sudoplay.mc.pwcustom.modules.dumpt.spi.serializer.IElementSerializer;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import scala.actors.threadpool.Arrays;

import java.io.IOException;
import java.io.Writer;
import java.util.function.Predicate;

public class ExportProfileBiome
    extends ExportProfileBase<Biome> {

  public ExportProfileBiome() {

    //noinspection unchecked
    super(Arrays.asList(new IElementSerializer[]{
        new SerializerBiomeName(),
        new SerializerForgeRegistryEntryDomain(),
        new SerializerForgeRegistryEntryPath(),
        new SerializerBiomeClass(),
        new SerializerBiomeIsMutation(),
        new SerializerBiomeSpawningChance(),
        new SerializerBiomeBaseHeight(),
        new SerializerBiomeHeightVariation(),
        new SerializerBiomeDefaultTemperature(),
        new SerializerBiomeTempCategory(),
        new SerializerBiomeCanRain(),
        new SerializerBiomeEnableSnow(),
        new SerializerBiomeRainfall(),
        new SerializerBiomeIsHighHumidity(),
        new SerializerBiomeSpawnableListAmbient(),
        new SerializerBiomeSpawnableListCreature(),
        new SerializerBiomeSpawnableListMonster(),
        new SerializerBiomeSpawnableListWaterCreature()
    }));
  }

  @Override
  public String getFilename() {

    return "biome.csv";
  }

  @Override
  public int write(Writer writer) throws IOException {

    Predicate<Biome> filter = this.getFilter();
    ILineWriter<Biome> lineWriter = this.getLineWriter();
    int elementCount = 0;

    for (Biome biome : ForgeRegistries.BIOMES.getValuesCollection()) {

      if (filter.test(biome)) {
        lineWriter.write(writer, biome);
        elementCount += 1;
      }
    }

    return elementCount;
  }

  protected Predicate<Biome> getFilter() {

    return biome -> true;
  }

}
