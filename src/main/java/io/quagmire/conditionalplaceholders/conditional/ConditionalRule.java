package io.quagmire.conditionalplaceholders.conditional;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConditionalRule {
  private String condition;
  private String format;

  // Regular expression to split the condition and format
  private static final Pattern RULE_PATTERN = Pattern.compile("^([^;]+);(.+)$");

  public ConditionalRule(String ruleString) {
    parseRule(ruleString);
  }

  // Method to parse the rule string and extract the condition and format
  private void parseRule(String ruleString) {
    Matcher matcher = RULE_PATTERN.matcher(ruleString);

    if (matcher.matches()) {
      this.condition = matcher.group(1).trim();  // Condition part
      this.format = matcher.group(2).trim();     // Format part
    } else {
      this.condition = "*"; // Default condition
      this.format = ruleString;
    }
  }

  public String getCondition() {
    return condition;
  }

  public String getFormat() {
    return format;
  }

  public boolean matchesAll() {
    return condition.equals("*");
  }

  // Helper method to output the parsed rule (for testing purposes)
  @Override
  public String toString() {
    return "Condition: " + condition + ", Format: " + format;
  }
}
