package com.sudoplay.mc.pwcustom.integration;

import com.sudoplay.mc.pwcustom.integration.workbench.JEIPluginDelegateWorkbench;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.ISubtypeRegistry;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;

import java.util.ArrayList;
import java.util.List;

@mezz.jei.api.JEIPlugin
public class JEIPlugin
    implements IModPlugin {

  private List<IModPlugin> pluginDelegateList;

  public JEIPlugin() {

    this.pluginDelegateList = new ArrayList<>();
    this.pluginDelegateList.add(new JEIPluginDelegateWorkbench());
  }

  @Override
  public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {

    for (IModPlugin delegate : this.pluginDelegateList) {
      delegate.registerItemSubtypes(subtypeRegistry);
    }
  }

  @Override
  public void registerIngredients(IModIngredientRegistration registry) {

    for (IModPlugin delegate : this.pluginDelegateList) {
      delegate.registerIngredients(registry);
    }
  }

  @Override
  public void registerCategories(IRecipeCategoryRegistration registry) {

    for (IModPlugin delegate : this.pluginDelegateList) {
      delegate.registerCategories(registry);
    }
  }

  @Override
  public void register(IModRegistry registry) {

    for (IModPlugin delegate : this.pluginDelegateList) {
      delegate.register(registry);
    }
  }

  @Override
  public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {

    for (IModPlugin delegate : this.pluginDelegateList) {
      delegate.onRuntimeAvailable(jeiRuntime);
    }
  }
}
