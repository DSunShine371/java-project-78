package hexlet.code.schemas;

import java.util.Map;
import java.util.function.Predicate;

public final class MapSchema extends BaseSchema<Map> {
    public MapSchema() {
        addRules(val -> val == null || val instanceof Map);
    }

    public MapSchema required() {
        this.isRequired = true;
        Predicate<Object> requiredRule = value -> {
            if (value == null) {
                return false;
            }
            return value instanceof Map;
        };
        rules.addFirst(requiredRule);
        return this;
    }

    public MapSchema sizeof(int mapSize) {
        addRules(value -> {
            if (value == null) {
                return !this.isRequired;
            }
            return ((Map<?, ?>) value).size() == mapSize;
        });
        return this;
    }

    public MapSchema shape(Map<String, BaseSchema<String>> schemas) {
        addRules(value -> {
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
