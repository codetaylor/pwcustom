package com.sudoplay.mc.pwcustom.material;

import net.minecraft.item.Item;

import java.awt.*;

// Thermal Foundation tool material stats
// https://github.com/CoFH/ThermalFoundation/blob/master/src/main/java/cofh/thermalfoundation/init/TFEquipment.java

public enum EnumMaterial {

  WOOD("wood", Item.ToolMaterial.WOOD, new Color(0x493615), false),
  STONE("stone", Item.ToolMaterial.STONE, new Color(0xFFFFFF), false),
  IRON("iron", Item.ToolMaterial.IRON, new Color(0x969696), true),
  GOLD("gold", Item.ToolMaterial.GOLD, new Color(0xEAEE57), false),
  DIAMOND("diamond", Item.ToolMaterial.DIAMOND, new Color(0x33EBCB), false),

  FLINT("flint", ModMaterials.FLINT, new Color(0x070707), true),
  ALUMINUM("aluminum", ModMaterials.ALUMINUM, new Color(0xC5C6D0), true),
  BRONZE("bronze", ModMaterials.BRONZE, new Color(0xE8983F), true),
  CONSTANTAN("constantan", ModMaterials.CONSTANTAN, new Color(0xBD8D46), true),
  COPPER("copper", ModMaterials.COPPER, new Color(0xDA842F), true),
  ELECTRUM("electrum", ModMaterials.ELECTRUM, new Color(0xAF9931), true),
  INVAR("invar", ModMaterials.INVAR, new Color(0x8E9A95), true),
  LEAD("lead", ModMaterials.LEAD, new Color(0x8A93B1), true),
  NICKEL("nickel", ModMaterials.NICKEL, new Color(0xA2975D), true),
  PLATINUM("platinum", ModMaterials.PLATINUM, new Color(0x4BACD8), true),
  SILVER("silver", ModMaterials.SILVER, new Color(0x7B9DA4), true),
  STEEL("steel", ModMaterials.STEEL, new Color(0x686868), false),
  TIN("tin", ModMaterials.TIN, new Color(0x7C9AB2), true);

  private String name;
  private Item.ToolMaterial toolMaterial;
  private int color;
  private boolean highlighted;

  EnumMaterial(String name, Item.ToolMaterial toolMaterial, Color color, boolean highlighted) {

    this.name = name;
    this.toolMaterial = toolMaterial;
    this.color = color.getRGB();
    this.highlighted = highlighted;
  }

  public String getName() {

    return this.name;
  }

  public Item.ToolMaterial getToolMaterial() {

    return this.toolMaterial;
  }

  public int getColor() {

    switch (this) {
      case WOOD:
        return new Color(0x493615).getRGB();
      case STONE:
        return new Color(0x969696).getRGB();
      case IRON:
        return new Color(0xD4D4D4).getRGB();
      case GOLD:
        return new Color(0xFFE947).getRGB();
      case DIAMOND:
        return new Color(0x33EBCB).getRGB();

      case FLINT:
        return new Color(0x1A1A1A).getRGB();
      case ALUMINUM:
        return new Color(0xC5C6D0).getRGB();
      case BRONZE:
        return new Color(0xE8983F).getRGB();
      case CONSTANTAN:
        return new Color(0xBD8D46).getRGB();
      case COPPER:
        return new Color(0xFFA131).getRGB();
      case ELECTRUM:
        return new Color(0xFFE947).getRGB();
      case INVAR:
        return new Color(0x8E9A95).getRGB();
      case LEAD:
        return new Color(0x8A93B1).getRGB();
      case NICKEL:
        return new Color(0xA2975D).getRGB();
      case PLATINUM:
        return new Color(0x4BACD8).getRGB();
      case SILVER:
        return new Color(0x7B9DA4).getRGB();
      case STEEL:
        return new Color(0x858585).getRGB();
      case TIN:
        return new Color(0x7C9AB2).getRGB();
    }

    return this.color;
  }

  public boolean isHighlighted() {

    return this.highlighted;
  }
}
