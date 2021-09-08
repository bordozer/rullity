# Rullity - light rules engine

Ruller is a 'homemade' rules engine  

### Prerequisites

```
- Java 8 or later
- Gradle version > 4.6
- https://github.com/bordozer/sca-parent
```

### Installing

Add to gradle build script:

```
compile group: 'com.bordozer', name: 'ruller', version: '1.00'
```

### Example of using:

The object we need to check

```
@Getter
@RequiredArgsConstructor
@EqualsAndHashCode(of = {"name"})
@ToString
public class Item {
    private final String name;
    private final List<Price> prices;
}
```
Declare rules (JAVA)
```
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
```
Firing rules

```
    final List<RuleActionResult<Item, ActionError>> ruleActionResults = new RulesEngine<Item, ActionError>()
                .objects(item)
                .rules(
                        itemNameIsBlank(),
                        itemNameIsTooShort(),
                        itemPricesIsEmpty()
                )
                .fire();
```

Where *CheckResult* is an object that is returned after the rule check

## Authors

* **Borys Lukianov**

