package com.sudoplay.mc.pwcustom.modules.world.util;

import java.util.Random;

public class MathUtil {

  public static final Random RANDOM = new Random();

  public static double distanceSquared(double x1, double y1, double x2, double y2) {

    return (x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1);
  }

  public static double distanceSquared(double x1, double y1, double z1, double x2, double y2, double z2) {

    return (x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1) + (z2 - z1) * (z2 - z1);
  }

  public static double distance(double x1, double y1, double z1, double x2, double y2, double z2) {

    return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1) + (z2 - z1) * (z2 - z1));
  }

  public static int getRandomIntFromRange(Random random, int min, int max) {

    return random.nextInt(max - min) + min;
  }

}
