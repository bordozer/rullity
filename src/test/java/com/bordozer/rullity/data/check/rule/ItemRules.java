package com.bordozer.rullity.data.check.rule;

import com.bordozer.rullity.core.Rule;
import com.bordozer.rullity.core.RuleBuilder;
import com.bordozer.rullity.data.ActionError;
import com.bordozer.rullity.data.fact.Item;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

@SuppressWarnings("checkstyle:MagicNumber")
public final class ItemRules {

    public static final Rule<Item, ActionError> ITEM_NAME_IS_BLANK_FATAL_RULE = itemNameIsBlank();
    public static final Rule<Item, ActionError> ITEM_NAME_IS_TOO_SHORT_RULE = itemNameIsTooShort();
    public static final Rule<Item, ActionError> ITEM_PRICES_IS_EMPTY_FATAL_RULE = itemPricesIsEmpty();

    private ItemRules() {
    }

    public static Rule<Item, ActionError> itemNameIsBlank() {
        return RuleBuilder.<Item, ActionError>of()
                .key("ITEM_NAME_IS_BLANK")
                .fatal(true)
                .when(item -> StringUtils.isBlank(item.getName()))
                .then(item -> ActionError.error("Item name is blank"))
                .build();
    }

    public static Rule<Item, ActionError> itemNameIsTooShort() {
        return RuleBuilder.<Item, ActionError>of()
                .key("ITEM_NAME_IS_TOO_SHORT")
                .when(item -> Optional.ofNullable(item.getName()).map(name -> name.length() < 3).orElse(false))
                .then(item -> ActionError.warning("Item name is too short"))
                .build();
    }

    public static Rule<Item, ActionError> itemPricesIsEmpty() {
        return RuleBuilder.<Item, ActionError>of()
                .key("ITEM_PRICES_IS_EMPTY")
                .fatal(true)
                .when(item -> CollectionUtils.isEmpty(item.getPrices()))
                .then(item -> ActionError.error("Item prices is empty"))
                .build();
    }
}
