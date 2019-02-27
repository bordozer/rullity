package com.bordozer.rullity.api;

@FunctionalInterface
public interface RuleAction<T, R> {

    R execute(T fact);
}
