package com.bordozer.rullity.api;

@FunctionalInterface
public interface RuleCondition<T> {

    boolean evaluate(T fact);
}
