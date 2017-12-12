package com.sudoplay.mc.pwcustom.lib.util;

import com.google.common.base.Preconditions;
import com.sudoplay.mc.pwcustom.ModPWCustom;
import com.sudoplay.mc.pwcustom.lib.spi.IBlockVariant;
import com.sudoplay.mc.pwcustom.lib.spi.IVariant;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.Side;

import java.util.function.ToIntFunction;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ModelRegistrationUtil {

  /**
   * A {@link StateMapperBase} used to create property strings.
   */
  public static final StateMapperBase PROPERTY_STRING_MAPPER = new StateMapperBase() {

    @Override
    protected ModelResourceLocation getModelResourceLocation(IBlockState state) {

      return new ModelResourceLocation("minecraft:air");
    }
  };

  // --------------------------------------------------------------------------
  // - ItemBlock
  // --------------------------------------------------------------------------

  public static void registerBlockItemModels(Block... blocks) {

    for (Block block : blocks) {

      if (block instanceof IBlockVariant) {
        //noinspection unchecked
        ModelRegistrationUtil.registerVariantBlockItemModels(
            block.getDefaultState(),
            ((IBlockVariant) block).getVariant()
        );

      } else {
        ModelRegistrationUtil.registerBlockItemModel(block.getDefaultState());
      }
    }
  }

  public static void registerBlockItemModel(IBlockState blockState) {

    Block block = blockState.getBlock();
    Item item = Item.getItemFromBlock(block);

    ModelRegistrationUtil.registerItemModel(
        item,
        new ModelResourceLocation(
            Preconditions.checkNotNull(block.getRegistryName(), "Block %s has null registry name", block),
            PROPERTY_STRING_MAPPER.getPropertyString(blockState.getProperties())
        )
    );
  }

  public static <T extends IVariant & Comparable<T>> void registerVariantBlockItemModels(
      IBlockState baseState,
      IProperty<T> property
  ) {

    ModelRegistrationUtil.registerVariantBlockItemModels(baseState, property, IVariant::getMeta);
  }

  public static <T extends IVariant & Comparable<T>> void registerVariantBlockItemModelsSeparately(
      IBlockState state,
      IProperty<T> property
  ) {

    ModelRegistrationUtil.registerVariantBlockItemModelsSeparately(state, property, "");
  }

  public static <T extends IVariant & Comparable<T>> void registerVariantBlockItemModelsSeparately(
      IBlockState state,
      IProperty<T> property,
      String suffix
  ) {

    for (T value : property.getAllowedValues()) {

      Item item = Item.getItemFromBlock(state.getBlock());

      if (item != Items.AIR) {
        ModelRegistrationUtil.registerItemModel(
            item,
            value.getMeta(),
            new ModelResourceLocation(ModPWCustom.MOD_ID + ":" + value.getName() + suffix, "inventory")
        );
      }

    }
  }

  public static <T extends Comparable<T>> void registerVariantBlockItemModels(
      IBlockState baseState,
      IProperty<T> property,
      ToIntFunction<T> getMeta
  ) {

    property.getAllowedValues()
        .forEach(value -> ModelRegistrationUtil.registerBlockItemModelForMeta(
            baseState.withProperty(property, value),
            getMeta.applyAsInt(value)
        ));
  }

  public static void registerBlockItemModelForMeta(final IBlockState state, final int metadata) {

    Item item = Item.getItemFromBlock(state.getBlock());

    if (item != Items.AIR) {
      ModelRegistrationUtil.registerItemModel(
          item,
          metadata,
          PROPERTY_STRING_MAPPER.getPropertyString(state.getProperties())
      );
    }
  }

  // --------------------------------------------------------------------------
  // - Item
  // --------------------------------------------------------------------------

  public static void registerItemModels(Item... items) {

    for (Item item : items) {
      ModelRegistrationUtil.registerItemModel(item, item.getRegistryName().toString());
    }
  }

  public static void registerItemModel(Item item, String modelLocation) {

    ModelResourceLocation resourceLocation = new ModelResourceLocation(modelLocation, "inventory");
    ModelRegistrationUtil.registerItemModel(item, 0, resourceLocation);
  }

  public static void registerItemModel(Item item, ModelResourceLocation resourceLocation) {

    ModelRegistrationUtil.registerItemModel(item, 0, resourceLocation);
  }

  public static void registerItemModel(final Item item, final int metadata, final String variant) {

    ModelRegistrationUtil.registerItemModel(
        item,
        metadata,
        new ModelResourceLocation(item.getRegistryName(), variant)
    );
  }

  public static void registerItemModel(Item item, int meta, ModelResourceLocation resourceLocation) {

    ModelLoader.setCustomModelResourceLocation(item, meta, resourceLocation);
  }

  public static <T extends IVariant> void registerVariantItemModels(Item item, String variantName, T[] values) {

    for (T value : values) {
      ModelRegistrationUtil.registerItemModel(item, value.getMeta(), variantName + "=" + value.getName());
    }
  }

}
