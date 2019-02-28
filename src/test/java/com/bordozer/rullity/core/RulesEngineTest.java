package com.bordozer.rullity.core;

import static com.bordozer.rullity.data.check.rule.ItemRules.ITEM_NAME_IS_BLANK_FATAL_RULE;
import static com.bordozer.rullity.data.check.rule.ItemRules.ITEM_NAME_IS_TOO_SHORT_RULE;
import static com.bordozer.rullity.data.check.rule.ItemRules.ITEM_PRICES_IS_EMPTY_FATAL_RULE;
import static com.google.common.collect.Lists.newArrayList;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import com.bordozer.rullity.data.ActionError;
import com.bordozer.rullity.data.fact.Item;
import com.bordozer.rullity.data.fact.Price;

class RulesEngineTest {

    @Test
    void shouldFailIdFirstRuleIsFatal() {
        // given
        final Item item = new Item(null, null);

        //when
        final List<RuleActionResult<Item, ActionError>> ruleActionResults = new RulesEngine<Item, ActionError>()
                .facts(item)
                .rules(
                        ITEM_NAME_IS_BLANK_FATAL_RULE,
                        ITEM_NAME_IS_TOO_SHORT_RULE,
                        ITEM_PRICES_IS_EMPTY_FATAL_RULE
                )
                .fire();

        // then
        assertThat(ruleActionResults).isNotNull().hasSize(2);

        final RuleActionResult<Item, ActionError> ruleActionResult1 = ruleActionResults.get(0);
        assertThat(ruleActionResult1.getObject()).isEqualTo(item);
        assertThat(ruleActionResult1.getRule()).isEqualTo(ITEM_NAME_IS_BLANK_FATAL_RULE);

        final ActionError actionError1 = ruleActionResult1.getActionResult();
        assertThat(actionError1.getError()).isEqualTo("Item name is blank");
        assertThat(actionError1.getWarning()).isNull();

        final RuleActionResult<Item, ActionError> ruleActionResult2 = ruleActionResults.get(1);
        assertThat(ruleActionResult2.getObject()).isEqualTo(item);
        assertThat(ruleActionResult2.getRule()).isEqualTo(ITEM_PRICES_IS_EMPTY_FATAL_RULE);

        final ActionError actionError2 = ruleActionResult2.getActionResult();
        assertThat(actionError2.getError()).isEqualTo("Item prices is empty");
        assertThat(actionError2.getWarning()).isNull();
    }

    @Test
    void shouldProcessMultipleObjectsOtTgeSameType() {
        // given
        final Item item1 = new Item("Hu", null);
        final Item item2 = new Item("Archi", newArrayList());

        //when
        final RulesEngine<Item, ActionError> rulesEngine = new RulesEngine<>();
        final List<RuleActionResult<Item, ActionError>> ruleActionResults = rulesEngine
                .facts(item1, item2)
                .rules(
                        ITEM_NAME_IS_BLANK_FATAL_RULE,
                        ITEM_NAME_IS_TOO_SHORT_RULE,
                        ITEM_PRICES_IS_EMPTY_FATAL_RULE
                )
                .fire();

        // then
        assertThat(ruleActionResults).isNotNull().hasSize(3);

        final ActionError actionError1 = ruleActionResults.get(0).getActionResult();
        assertThat(actionError1.getError()).isNull();
        assertThat(actionError1.getWarning()).isEqualTo("Item name is too short");

        final ActionError actionError2 = ruleActionResults.get(1).getActionResult();
        assertThat(actionError2.getError()).isEqualTo("Item prices is empty");
        assertThat(actionError2.getWarning()).isNull();

        final ActionError actionError3 = ruleActionResults.get(2).getActionResult();
        assertThat(actionError3.getError()).isEqualTo("Item prices is empty");
        assertThat(actionError3.getWarning()).isNull();
    }

    @Test
    void shouldFireParallel() {
        // given
        final List<Item> items = IntStream.range(10, 20)
                .mapToObj(i -> new Item(String.format("Item #%s", i), newArrayList(new Price(LocalDate.of(2018, 12, i), (double) i))))
                .collect(Collectors.toList());

        //when
        final RulesEngine<Item, ActionError> rulesEngine = new RulesEngine<>();
        final List<RuleActionResult<Item, ActionError>> ruleActionResults = rulesEngine
                .facts(items)
                .rules(
                        ITEM_NAME_IS_BLANK_FATAL_RULE,
                        ITEM_NAME_IS_TOO_SHORT_RULE,
                        ITEM_PRICES_IS_EMPTY_FATAL_RULE
                )
                .fireParallel();


        // then
        assertThat(ruleActionResults).isNotNull().hasSize(0);
    }
}
