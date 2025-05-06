package hexlet.code.schemas;

import java.util.Map;

public class MapSchema extends BaseSchema<MapSchema> {
    private Integer size;

    public MapSchema sizeof(int mapSize) {
        this.size = mapSize;
        return this;
    }

    @Override
    public boolean isValid(Object value) {
        if (value == null) {
            return !isRequired;
        }
        if (!(value instanceof Map<?, ?> map)) {
            return false;
        }
        if (map.isEmpty()) {
            return !isRequired;
        }
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            if (!(entry.getKey() instanceof String) || !(entry.getValue() instanceof String)) {
                return false;
            }
        }
        return this.size == null || map.size() == size;
    }
}
