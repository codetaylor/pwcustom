package com.sudoplay.mc.pwcustom.modules.knives.item;

import com.codetaylor.mc.athenaeum.reference.EnumMaterial;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sudoplay.mc.pwcustom.ModPWCustom;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemSword;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class ItemKnife
    extends ItemSword {

  private float attackDamage;
  private EnumMaterial material;

  public ItemKnife(String name, EnumMaterial material) {

    super(material.getToolMaterial());
    this.attackDamage = (3f + material.getToolMaterial().getAttackDamage()) / 2f;
    this.material = material;
    this.setRegistryName(new ResourceLocation(ModPWCustom.MOD_ID, name));
    this.setUnlocalizedName(ModPWCustom.MOD_ID + "." + name);
    this.setCreativeTab(ModPWCustom.CREATIVE_TAB);
  }

  public EnumMaterial getMaterial() {

    return this.material;
  }

  @Override
  public float getAttackDamage() {

    return Math.max(super.getAttackDamage() / 2f, 1);
  }

  @Nonnull
  @Override
  public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {

    Multimap<String, AttributeModifier> multimap = HashMultimap.create();

    if (equipmentSlot == EntityEquipmentSlot.MAINHAND) {
      multimap.put(
          SharedMonsterAttributes.ATTACK_DAMAGE.getName(),
          new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double) this.attackDamage, 0)
      );
      multimap.put(
          SharedMonsterAttributes.ATTACK_SPEED.getName(),
          new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.4, 0)
      );
    }

    return multimap;
  }
}
