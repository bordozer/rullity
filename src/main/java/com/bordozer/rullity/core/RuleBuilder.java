package com.bordozer.rullity.core;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import com.bordozer.rullity.api.RuleAction;
import com.bordozer.rullity.api.RuleCondition;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RuleBuilder<T, R> {

    private String ruleKey;
    private boolean fatal;
    private RuleCondition<T> ruleCondition;
    private final List<RuleAction<T, R>> ruleActions = newArrayList();

    public static <T, R> RuleBuilder<T, R> of() {
        return new RuleBuilder<>();
    }

    public RuleBuilder<T, R> key(final String ruleKey) {
        this.ruleKey = ruleKey;
        return this;
    }

    public RuleBuilder<T, R> fatal(final boolean isFatal) {
        this.fatal = isFatal;
        return this;
    }

    public RuleBuilder<T, R> when(final RuleCondition<T> condition) {
        this.ruleCondition = condition;
        return this;
    }

    public RuleBuilder<T, R> then(final RuleAction<T, R> action) {
        this.ruleActions.add(action);
        return this;
    }

    public Rule<T, R> build() {
        return new Rule<>(ruleKey, fatal, ruleCondition, ruleActions);
    }
}
