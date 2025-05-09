package hexlet.code.schemas;

import java.util.Map;

public class MapSchema extends BaseSchema<Map> {
    private Map<String, BaseSchema<String>> shapeSchemas;
    private Integer size;

    public MapSchema required() {
        this.isRequired = true;
        return this;
    }

    public MapSchema sizeof(int mapSize) {
        this.size = mapSize;
        return this;
    }

    public MapSchema shape(Map<String, BaseSchema<String>> schemas) {
        this.shapeSchemas = schemas;
        return this;
    }

    @Override
    public boolean isValid(Object value) {
        if (value == null) {
            return !isRequired;
        } else {
            if (!(value instanceof Map<?, ?> map)) {
                return false;
            }
            if (this.size != null && map.size() != size) {
                return false;
            }
            if (shapeSchemas != null && !shapeSchemas.isEmpty()) {
                for (Map.Entry<String, BaseSchema<String>> entry : shapeSchemas.entrySet()) {
                    String fieldName = entry.getKey();
                    BaseSchema<String> fieldSchema = entry.getValue();
                    Object mapValue = map.get(fieldName);

                    if (!fieldSchema.isValid(mapValue)) {
                        return false;
                    }
                }
            }
            return true;
        }
    }
}
