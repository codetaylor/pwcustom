package com.sudoplay.mc.pwcustom.modules.veins.util;

import net.minecraft.util.math.MathHelper;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

public class PerlinWrapper {

  private static final double MIN = -14.375499954247172;
  private static final double MAX = 13.922339784150655;
  private static final double INVERSE_RANGE = 1.0 / (MAX - MIN);
  private NoiseGeneratorPerlin generatorPerlin;

  public PerlinWrapper(NoiseGeneratorPerlin generatorPerlin) {

    this.generatorPerlin = generatorPerlin;
  }

  public double getValue(double x, double y) {

    return MathHelper.clamp((this.generatorPerlin.getValue(x, y) - MIN) * INVERSE_RANGE, 0, 1);
  }

}
