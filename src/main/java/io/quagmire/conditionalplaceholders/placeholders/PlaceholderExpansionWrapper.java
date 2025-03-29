package io.quagmire.conditionalplaceholders.placeholders;

import io.quagmire.conditionalplaceholders.ConditionalPlaceholdersPlugin;

public class PlaceholderExpansionWrapper {
  private final ConditionalPlaceholdersPlugin plugin;
  private ConditionalPlaceholderExpansion placeholderExpansion;

  public PlaceholderExpansionWrapper(ConditionalPlaceholdersPlugin plugin) {
    this.plugin = plugin;
  }

  public void register() {
    placeholderExpansion = new ConditionalPlaceholderExpansion(plugin);
    if (placeholderExpansion.canRegister()) {
      placeholderExpansion.register();
      plugin.getLogger().info("PlaceholderAPI found! Registering placeholders...");
    }
  }

  public void unregister() {
    if (placeholderExpansion != null) placeholderExpansion.unregister();
  }
}
