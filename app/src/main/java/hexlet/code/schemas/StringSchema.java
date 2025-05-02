package hexlet.code.schemas;

import java.util.ArrayList;
import java.util.List;

public class StringSchema {
    private boolean isRequired;
    private Integer minLength;
    private List<String> substrings = new ArrayList<>();

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

    public boolean isValid(String value) {
        if (value == null || value.isEmpty()) {
            return !isRequired;
        }
        if (minLength != null) {
            if (value.length() < minLength) {
                return false;
            }
        }
        for (String substring : substrings) {
            if (!value.contains(substring)) {
                return false;
            }
        }
        return true;
    }
}
