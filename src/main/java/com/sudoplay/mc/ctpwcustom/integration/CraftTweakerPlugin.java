package com.sudoplay.mc.ctpwcustom.integration;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;

import java.util.LinkedList;
import java.util.List;

public class CraftTweakerPlugin {

  public static final List<IAction> LATE_REMOVALS = new LinkedList<>();
  public static final List<IAction> LATE_ADDITIONS = new LinkedList<>();

  public static void apply() {

    LATE_REMOVALS.forEach(CraftTweakerAPI::apply);
    LATE_ADDITIONS.forEach(CraftTweakerAPI::apply);
  }
}
