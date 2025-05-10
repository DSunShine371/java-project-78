package hexlet.code.schemas;

import java.util.function.Predicate;

public final class StringSchema extends BaseSchema<String> {
    public StringSchema() {
        addRules(value -> value == null || value instanceof String);
    }

    public StringSchema required() {
        this.isRequired = true;
        Predicate<Object> requiredRule = value -> {
            if (value == null) {
                return false;
            }
            if (!(value instanceof String)) {
                return false;
            }
            return !((String) value).isEmpty();
        };
        rules.addFirst(requiredRule);
        return this;
    }

    public StringSchema minLength(int length) {
        addRules(value -> {
            if (value == null) {
                return !this.isRequired;
            }
            return ((String) value).length() >= length;
        });
        return this;
    }

    public StringSchema contains(String substring) {
        addRules(value -> {
            if (value == null) {
                return !this.isRequired;
            }
            return ((String) value).contains(substring);
        });
        return this;
    }
}
