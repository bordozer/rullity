package com.bordozer.rullity.core;

import java.util.List;

import com.bordozer.rullity.api.RuleAction;
import com.bordozer.rullity.api.RuleCondition;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode(of = {"ruleKey"})
@ToString(of = {"ruleKey"})
public class Rule<T, R> {

    private final String ruleKey;
    private final boolean fatal;
    private final RuleCondition<T> ruleCondition;
    private final List<RuleAction<T, R>> ruleActions;

    public boolean ifCondition(final T fact) {
        return ruleCondition.evaluate(fact);
    }
}
