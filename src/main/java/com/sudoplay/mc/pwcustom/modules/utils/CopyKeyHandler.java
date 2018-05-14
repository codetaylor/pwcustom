package com.sudoplay.mc.pwcustom.modules.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.awt.datatransfer.StringSelection;

public class CopyKeyHandler {

  private boolean keyDown;

  @SubscribeEvent
  public void onToolTipEvent(ItemTooltipEvent event) {

    if (Keyboard.isKeyDown(Keyboard.KEY_GRAVE)) {

      if (this.keyDown) {
        return;
      }

      this.keyDown = true;
      this.outputStack(event.getItemStack());

    } else {
      this.keyDown = false;
    }
  }

  private void outputStack(ItemStack stack) {

    if (stack.isEmpty()) {
      return;
    }

    String output;

    if (stack.getMetadata() > 0) {
      output = "<" + stack.getItem().getRegistryName() + ":" + stack.getMetadata() + ">";

    } else {
      output = "<" + stack.getItem().getRegistryName() + ">";
    }

    //System.out.println(output);
    Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new TextComponentString(output));
    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(output), null);
  }
}
