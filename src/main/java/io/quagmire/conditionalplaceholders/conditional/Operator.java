package io.quagmire.conditionalplaceholders.conditional;

@FunctionalInterface
interface Operator {
  boolean apply(String input, String conditionValue);
}