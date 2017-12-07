package com.sudoplay.mc.pwcustom.inventory;

public class CraftingMatrixStackHandler
    extends ObservableStackHandler {

  private int width;
  private int height;

  public CraftingMatrixStackHandler(
      int width,
      int height
  ) {

    super(width * height);
    this.width = width;
    this.height = height;
  }

  public int getWidth() {

    return this.width;
  }

  public int getHeight() {

    return this.height;
  }
}
