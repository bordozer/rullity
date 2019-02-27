package com.bordozer.rullity.core;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;
import java.util.stream.Stream;

import lombok.Getter;

@Getter
public class RuleSet<T, R> {

    private final List<Rule<T, R>> rules = newArrayList();
    private final List<RuleSet<T, R>> ruleSets = newArrayList();

    private RuleSet() {
    }

    public static <T> RuleSet of() {
        return new RuleSet();
    }

    public static <T, R> RuleSet<T, R> of(final List<Rule<T, R>> rules) {
        final RuleSet<T, R> ruleSet = new RuleSet<>();
        rules.forEach(ruleSet::addRule);
        return ruleSet;
    }

    public static <T, R> RuleSet<T, R> of(final Rule<T, R>... rules) {
        final RuleSet<T, R> ruleSet = new RuleSet<>();
        Stream.of(rules).forEach(ruleSet::addRule);
        return ruleSet;
    }

    public static <T, R> RuleSet<T, R> of(final RuleSet<T, R>... ruleSets) {
        final RuleSet<T, R> ruleSet = new RuleSet<>();
        Stream.of(ruleSets).forEach(ruleSet::addRuleSet);
        return ruleSet;
    }

    public RuleSet<T, R> addRule(final Rule<T, R> rule) {
        rules.add(rule);
        return this;
    }

    public RuleSet<T, R> addRuleSet(final RuleSet<T, R> ruleSet) {
        ruleSets.add(ruleSet);
        return this;
    }
}
