package com.sudoplay.mc.pwcustom.modules.mortar.tile;

public class MortarDelegateFactory {

  private Runnable changeObserver;

  public MortarDelegateFactory(Runnable changeObserver) {

    this.changeObserver = changeObserver;
  }

  public IMortar create(EnumMortarMode mortarMode) {

    switch (mortarMode) {
      case MIXING:
        return new MortarDelegateMixing(this.changeObserver);
      case CRUSHING:
        return new MortarDelegateCrushing(this.changeObserver);
      default:
        throw new IllegalArgumentException("Unknown mortar mode: " + mortarMode);
    }

  }

}
