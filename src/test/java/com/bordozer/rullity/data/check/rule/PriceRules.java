package com.bordozer.rullity.data.check.rule;

import com.bordozer.rullity.core.Rule;
import com.bordozer.rullity.core.RuleBuilder;
import com.bordozer.rullity.data.ActionError;
import com.bordozer.rullity.data.obj.Price;

public final class PriceRules {

    public static final Rule<Price, ActionError> PRICE_DATE_IS_NULL_FATAL_RULE = priceDateIsNull();
    public static final Rule<Price, ActionError> PRICE_IS_NULL_FATAL_RULE = priceIsNull();
    public static final Rule<Price, ActionError> PRICE_IS_NEGATIVE_FATAL_RULE = priceIsNegative();
    public static final Rule<Price, ActionError> PRICE_IS_TOO_LOW_RULE = priceIsTooLow();

    private PriceRules() {
    }

    public static Rule<Price, ActionError> priceDateIsNull() {
        return RuleBuilder.<Price, ActionError>of()
                .key("PRICE_DATE_IS_NULL")
                .fatal(true)
                .when(price -> price.getDate() == null)
                .then(price -> ActionError.error("Price date is null"))
                .build();
    }

    public static Rule<Price, ActionError> priceIsNull() {
        return RuleBuilder.<Price, ActionError>of()
                .key("PRICE_IS_NULL")
                .when(price -> price.getPrice() == null)
                .then(price -> ActionError.error("Price value is null"))
                .build();
    }

    public static Rule<Price, ActionError> priceIsNegative() {
        return RuleBuilder.<Price, ActionError>of()
                .key("PRICE_IS_NEGATIVE")
                .fatal(true)
                .when(price -> price.getPrice() < 0)
                .then(price -> ActionError.error("Price is negative"))
                .build();
    }

    public static Rule<Price, ActionError> priceIsTooLow() {
        return RuleBuilder.<Price, ActionError>of()
                .key("PRICE_IS_TOO_LOW")
                .when(price -> price.getPrice() < 10)
                .then(price -> ActionError.warning("Price is too low"))
                .build();
    }
}
