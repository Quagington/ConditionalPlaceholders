package io.quagmire.conditionalplaceholders.conditional;

import io.quagmire.core.colors.Colors;
import lombok.Getter;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConditionalRuleSet {
  private static final Pattern CONDITION_PATTERN = Pattern.compile("([<>=!]+)(.*)"); // e.g. >=10, !=Running

  @Getter private final String id;
  private final String placeholder;
  private final List<ConditionalRule> rules;

  private ConditionalRule baseRule; // Base rule (for fallthrough)

  // Map to store operator logic
  private static final Map<String, Operator> operators = new HashMap<>();

  // Static block to initialize the operators map
  static {
    operators.put("=", String::equalsIgnoreCase);
    operators.put("!=", (input, conditionValue) -> !input.equals(conditionValue));
    operators.put(">", (input, conditionValue) -> compareNumerically(input, conditionValue) > 0);
    operators.put(">=", (input, conditionValue) -> compareNumerically(input, conditionValue) >= 0);
    operators.put("<", (input, conditionValue) -> compareNumerically(input, conditionValue) < 0);
    operators.put("<=", (input, conditionValue) -> compareNumerically(input, conditionValue) <= 0);
  }

  public ConditionalRuleSet(String placeholder, List<ConditionalRule> rules, String id) {
    this.placeholder = placeholder;
    this.id = id;
    this.rules = new ArrayList<>();
    setRulesWithFallback(rules);
  }

  // Method to handle adding base rule separately
  private void setRulesWithFallback(List<ConditionalRule> rules) {
    for (ConditionalRule rule : rules) {
      if (rule.matchesAll()) {
        baseRule = rule;  // Identify the base (fallthrough) rule
      } else {
        this.rules.add(rule);  // Add regular rules
      }
    }

    // If no explicit base rule was defined, create a default fallback rule
    if (baseRule == null) {
      baseRule = new ConditionalRule("*;");  // Default fallthrough rule
    }
  }

  public String applyRules(@Nullable OfflinePlayer player) {
    for (ConditionalRule rule : rules) {
      String condition = rule.getCondition();
      String format = rule.getFormat();

      String rendered = PlaceholderAPI.setPlaceholders(player, placeholder);

      if (evaluateCondition(condition, rendered)) {
        return formatInput(player, format);
      }
    }

    // If no conditions match, return the base rule's format
    return formatInput(player, baseRule.getFormat());
  }

  private boolean evaluateCondition(String condition, String input) {
    Matcher matcher = CONDITION_PATTERN.matcher(condition);
    if (matcher.matches()) {
      String operator = matcher.group(1);
      String conditionValue = matcher.group(2);

      Operator op = operators.get(operator);
      if (op != null) {
        return op.apply(input, conditionValue);
      }
    }
    return false; // Default return if no valid operator or pattern is found
  }

  private static int compareNumerically(String input, String conditionValue) {
    try {
      int inputVal = Integer.parseInt(input);
      int conditionVal = Integer.parseInt(conditionValue);
      return Integer.compare(inputVal, conditionVal);
    } catch (NumberFormatException e) {
      return input.compareTo(conditionValue); // Fallback to string comparison if not numbers
    }
  }

  private String formatInput(@Nullable OfflinePlayer player, String format) {
    return Colors.convert(PlaceholderAPI.setPlaceholders(player, format));
  }

  public static @Nullable ConditionalRuleSet deserialize(String id, @Nullable ConfigurationSection section) {
    if (section == null) return null;

    String placeholder = section.getString("placeholder", "");
    List<ConditionalRule> rules = new ArrayList<>();

    List<String> raw = section.getStringList("rules");
    for (String key : raw) {
      ConditionalRule rule = new ConditionalRule(key);
      rules.add(rule);
    }

    return new ConditionalRuleSet(placeholder, rules, id);
  }
}
