package hexlet.code.schemas;

import java.util.function.Predicate;

import static hexlet.code.schemas.RuleNames.POSITIVE;
import static hexlet.code.schemas.RuleNames.RANGE;
import static hexlet.code.schemas.RuleNames.REQUIRED;

public final class NumberSchema extends BaseSchema<Number>  {
    public NumberSchema() {
        setDefaultRule(value -> value == null || value instanceof Integer);
    }

    public NumberSchema required() {
        this.isRequired = true;
        Predicate<Object> requiredRule = value -> {
            if (value == null) {
                return false;
            }
            return value instanceof Integer;
        };
        addRules(REQUIRED, requiredRule);
        return this;
    }

    public NumberSchema positive() {
        addRules(POSITIVE, value -> {
            if (value == null) {
                return !this.isRequired;
            }
            return ((Integer) value) > 0;
        });
        return this;
    }

    public NumberSchema range(int min, int max) {
        IntRange range = new IntRange(min, max);
        addRules(RANGE, value -> {
            if (value == null) {
                return !this.isRequired;
            }
            return range.isIncluded((Integer) value);
        });
        return this;
    }
}
