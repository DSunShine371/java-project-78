package hexlet.code;

import hexlet.code.schemas.MapSchema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidatorMapSchemaTests {
    Validator validator;

    @BeforeEach
    void validatorPreparation() {
        validator = new Validator();
    }

    @Test
    void testDefaultMapSchema() {
        MapSchema schema = validator.map();

        assertTrue(schema.isValid(null));
        assertTrue(schema.isValid(new HashMap<>()));

        Map<String, String> emptyMap = new HashMap<>();
        assertTrue(schema.isValid(emptyMap));

        Map<String, String> data = new HashMap<>();
        data.put("key1", "value1");
        assertTrue(schema.isValid(data));

        Map<String, Integer> dataWithWrongValueType = new HashMap<>();
        dataWithWrongValueType.put("key", 123);
        assertFalse(schema.isValid(dataWithWrongValueType));

        Map<Integer, String> dataWithWrongKeyType = new HashMap<>();
        dataWithWrongKeyType.put(123, "value");
        assertFalse(schema.isValid(dataWithWrongKeyType));

        Map<Object, Object> dataWithObjectTypes = new HashMap<>();
        dataWithObjectTypes.put("key", 123);
        assertFalse(schema.isValid(dataWithObjectTypes));

        Map<Object, Object> dataWithObjectTypes2 = new HashMap<>();
        dataWithObjectTypes2.put(123, "value");
        assertFalse(schema.isValid(dataWithObjectTypes2));
    }


    @ParameterizedTest
    @NullSource
    @ValueSource(classes = {HashMap.class})
    void testMapSchemaRequiredRule(Object value) {
        MapSchema schema = validator.map().required();

        if (value == null || (value instanceof Map && ((Map<?, ?>) value).isEmpty())) {
            assertFalse(schema.isValid(value));
        } else {
            Map<String, String> data = new HashMap<>();
            data.put("key1", "value1");
            assertTrue(schema.isValid(data));

            Map<String, Integer> dataWithWrongValueType = new HashMap<>();
            dataWithWrongValueType.put("key", 123);
            assertFalse(schema.isValid(dataWithWrongValueType));

            Map<Integer, String> dataWithWrongKeyType = new HashMap<>();
            dataWithWrongKeyType.put(123, "value");
            assertFalse(schema.isValid(dataWithWrongKeyType));
        }
    }

    @Test
    void testMapSchemaSizeofRule() {
        MapSchema schema = validator.map().sizeof(2);
        assertTrue(schema.isValid(null));
        assertTrue(schema.isValid(new HashMap<>()));

        Map<String, String> data1 = new HashMap<>();
        data1.put("k1", "v1");
        assertFalse(schema.isValid(data1));

        Map<String, String> data2 = new HashMap<>();
        data2.put("k1", "v1");
        data2.put("k2", "v2");
        assertTrue(schema.isValid(data2));

        Map<String, String> data3 = new HashMap<>();
        data3.put("k1", "v1");
        data3.put("k2", "v2");
        data3.put("k3", "v3");
        assertFalse(schema.isValid(data3));

        schema.sizeof(0);
        assertTrue(schema.isValid(new HashMap<>()));
        assertFalse(schema.isValid(data1));
    }

    @Test
    void testMapSchemaCombinedRules() {
        MapSchema schema = validator.map().required().sizeof(2);
        assertFalse(schema.isValid(null));
        assertFalse(schema.isValid(new HashMap<>()));

        Map<String, String> data1 = new HashMap<>();
        data1.put("single1", "value1");
        assertFalse(schema.isValid(data1));
        data1.put("single2", "value2");
        assertTrue(schema.isValid(data1));

        Map<String, Integer> data2 = new HashMap<>();
        data2.put("key1", 123);
        data2.put("key2", 123);
        assertFalse(schema.isValid(data2));

        Map<Integer, String> data3 = new HashMap<>();
        data3.put(12, "key1");
        data3.put(123, "key2");
        assertFalse(schema.isValid(data3));
    }

    @Test
    void testMapSchemaNonMapValue() {
        MapSchema schema = validator.map();
        int value1 = 5;
        assertFalse(schema.isValid(value1));
        boolean value2 = true;
        assertFalse(schema.isValid(value2));
        String value3 = "not a map";
        assertFalse(schema.isValid(value3));
        List<String> value4 = new ArrayList<>();
        assertFalse(schema.isValid(value4));
    }
}
