package hexlet.code.schemas;

import java.util.function.Predicate;

public final class NumberSchema extends BaseSchema<Number>  {
    public NumberSchema() {
        addRules(val -> val == null || val instanceof Integer);
    }

    public NumberSchema required() {
        this.isRequired = true;
        Predicate<Object> requiredRule = value -> {
            if (value == null) {
                return false;
            }
            return value instanceof Integer;
        };
        rules.addFirst(requiredRule);
        return this;
    }

    public NumberSchema positive() {
        addRules(value -> {
            if (value == null) {
                return !this.isRequired;
            }
            return ((Integer) value) > 0;
        });
        return this;
    }

    public NumberSchema range(int min, int max) {
        IntRange range = new IntRange(min, max);
        addRules(value -> {
            if (value == null) {
                return !this.isRequired;
            }
            return range.isIncluded((Integer) value);
        });
        return this;
    }
}
