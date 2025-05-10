package hexlet.code.schemas;

import java.util.function.Predicate;

import static hexlet.code.schemas.RuleNames.CONTAINS;
import static hexlet.code.schemas.RuleNames.DEFAULT;
import static hexlet.code.schemas.RuleNames.MIN_LENGTH;
import static hexlet.code.schemas.RuleNames.REQUIRED;

public final class StringSchema extends BaseSchema<String> {
    public StringSchema() {
        addRules(DEFAULT, value -> value == null || value instanceof String);
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
        addRules(REQUIRED, requiredRule);
        return this;
    }

    public StringSchema minLength(int length) {
        addRules(MIN_LENGTH, value -> {
            if (value == null) {
                return !this.isRequired;
            }
            return ((String) value).length() >= length;
        });
        return this;
    }

    public StringSchema contains(String substring) {
        addRules(CONTAINS, value -> {
            if (value == null) {
                return !this.isRequired;
            }
            return ((String) value).contains(substring);
        });
        return this;
    }
}
