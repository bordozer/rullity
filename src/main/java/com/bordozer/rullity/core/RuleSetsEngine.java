package com.bordozer.rullity.core;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class RuleSetsEngine<T, R> {

    private final List<RuleActionResult<T, R>> ruleCheckResultsContainer = newArrayList();

    public RuleSetsEngine<T, R> fire(final T object, final RuleSet<T, R> ruleSet) {
        final RulesEngine<T, R> rulesEngine = new RulesEngine<>();
        final List<RuleActionResult<T, R>> rulesResults = rulesEngine
                .facts(object)
                .rules(ruleSet.getRules())
                .fire();
        rulesResults.addAll(rulesEngine.getRuleCheckResultsContainer());

        ruleSet.getRuleSets().forEach(aRuleSet -> fire(object, aRuleSet));

        return this;
    }
}
