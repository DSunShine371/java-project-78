package hexlet.code.schemas;

import java.util.ArrayList;
import java.util.List;

public final class StringSchema extends BaseSchema<String> {
    private Integer minLength;
    private final List<String> substrings = new ArrayList<>();

    public StringSchema required() {
        this.isRequired = true;
        return this;
    }

    public StringSchema minLength(int length) {
        this.minLength = length;
        return this;
    }

    public StringSchema contains(String substring) {
        this.substrings.add(substring);
        return this;
    }

    @Override
    public boolean isValid(Object value) {
        if (value == null) {
            return !isRequired;
        } else {
            if (!(value instanceof String stringValue)) {
                return false;
            }
            if (stringValue.isEmpty()) {
                return !isRequired;
            }
            if (minLength != null && stringValue.length() < minLength) {
                return false;
            }
            for (String substring : substrings) {
                if (!stringValue.contains(substring)) {
                    return false;
                }
            }
            return true;
        }
    }
}
