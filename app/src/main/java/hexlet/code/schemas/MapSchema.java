package hexlet.code.schemas;

import java.util.Map;
import java.util.function.Predicate;

import static hexlet.code.schemas.RuleNames.REQUIRED;
import static hexlet.code.schemas.RuleNames.SHAPE;
import static hexlet.code.schemas.RuleNames.SIZEOF;

public final class MapSchema extends BaseSchema<Map> {
    public MapSchema() {
        setDefaultRule(value -> value == null || value instanceof Map);
    }

    public MapSchema required() {
        this.isRequired = true;
        Predicate<Object> requiredRule = value -> {
            if (value == null) {
                return false;
            }
            return value instanceof Map;
        };
        addRules(REQUIRED, requiredRule);
        return this;
    }

    public MapSchema sizeof(int mapSize) {
        addRules(SIZEOF, value -> {
            if (value == null) {
                return !this.isRequired;
            }
            return ((Map<?, ?>) value).size() == mapSize;
        });
        return this;
    }

    public MapSchema shape(Map<String, BaseSchema<String>> schemas) {
        addRules(SHAPE, value -> {
            if (value == null) {
                return !this.isRequired;
            }
            Map<?, ?> mapValue = (Map<?, ?>) value;
            for (Map.Entry<String, BaseSchema<String>> entry : schemas.entrySet()) {
                String fieldName = entry.getKey();
                BaseSchema<String> fieldSchema = entry.getValue();
                Object mapFieldValue = mapValue.get(fieldName);

                if (!fieldSchema.isValid(mapFieldValue)) {
                    return false;
                }
            }
            return true;
        });
        return this;
    }
}
