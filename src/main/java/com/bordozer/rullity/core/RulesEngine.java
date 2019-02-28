package com.bordozer.rullity.core;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@SuppressWarnings("PMD.AvoidFieldNameMatchingMethodName")
@Slf4j
@Getter
public class RulesEngine<T, R> {

    private final List<T> facts = newArrayList();
    private final List<Rule<T, R>> rules = newArrayList();
    private final List<RuleActionResult<T, R>> ruleCheckResultsContainer = newArrayList();

    @Getter(value = AccessLevel.PRIVATE)
    private Boolean stoppedByFatal = Boolean.FALSE;

    public RulesEngine<T, R> facts(final T fact) {
        this.facts.add(fact);
        return this;
    }

    @SafeVarargs
    public final RulesEngine<T, R> facts(final T... facts) {
        this.facts.addAll(Stream.of(facts).collect(Collectors.toList()));
        return this;
    }

    public RulesEngine<T, R> facts(final List<T> facts) {
        this.facts.addAll(facts);
        return this;
    }

    @SafeVarargs
    public final RulesEngine<T, R> rules(final Rule<T, R>... rules) {
        this.rules.addAll(Stream.of(rules).collect(Collectors.toList()));
        return this;
    }

    public RulesEngine<T, R> rules(final List<Rule<T, R>> rules) {
        this.rules.addAll(rules);
        return this;
    }

    public List<RuleActionResult<T, R>> fire() {
        return doFire(facts.stream());
    }

    public List<RuleActionResult<T, R>> fireParallel() {
        return doFire(facts.parallelStream());
    }

    private List<RuleActionResult<T, R>> doFire(final Stream<T> stream) {
        return stream
                .peek(ob -> LOGGER.info("Processing fact {} in thread {}", ob, Thread.currentThread().getName()))
                .map(obj -> applyRulesToFact(obj, this.rules))
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    private List<RuleActionResult<T, R>> applyRulesToFact(final T fact, final List<Rule<T, R>> rules) {
        return rules.stream()
                .map(rule -> applyRuleToFact(fact, rule))
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    private List<RuleActionResult<T, R>> applyRuleToFact(final T fact, final Rule<T, R> rule) {
        if (rule.ifCondition(fact)) {
            return rule.getRuleActions().stream()
                    .map(action -> action.execute(fact))
                    .map(actionResult -> new RuleActionResult<>(fact, rule, actionResult))
                    .collect(Collectors.toList());
        }
        return newArrayList();
    }
}
