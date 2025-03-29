package io.quagmire.conditionalplaceholders.placeholders;

import io.quagmire.conditionalplaceholders.ConditionalPlaceholdersPlugin;
import io.quagmire.conditionalplaceholders.conditional.ConditionalRuleSet;
import io.quagmire.core.colors.Colors;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ConditionalPlaceholderExpansion extends PlaceholderExpansion {
  private final ConditionalPlaceholdersPlugin plugin;

  public ConditionalPlaceholderExpansion(ConditionalPlaceholdersPlugin plugin) {
    super();
    this.plugin = plugin;
  }

  @Override
  public @NotNull String getIdentifier() {
    return "conditional";
  }

  @Override
  public @NotNull String getAuthor() {
    return plugin.getDescription().getAuthors().getFirst();
  }

  @Override
  public @NotNull String getVersion() {
    return plugin.getDescription().getVersion();
  }

  @Override
  public String onPlaceholderRequest(Player player, String param) {
    ConditionalRuleSet rule = plugin.getRuleManager().getRule(param);
    if (rule == null) return Colors.convert("&cno-match");
    return rule.applyRules(player);
  }
}
