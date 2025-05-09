package hexlet.code;

import hexlet.code.schemas.BaseSchema;
import hexlet.code.schemas.MapSchema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    }


    @Test
    void testMapSchemaRequiredRule() {
        MapSchema schema = validator.map().required();

        assertFalse(schema.isValid(null));

        Map<String, String> data = new HashMap<>();
        assertTrue(schema.isValid(data));

        data.put("key1", "value1");
        assertTrue(schema.isValid(data));
    }

    @Test
    void testMapSchemaSizeofRule() {
        MapSchema schema = validator.map().sizeof(2);
        assertTrue(schema.isValid(null));
        assertFalse(schema.isValid(new HashMap<>()));

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
    void testMapSchemaShapeRule() {
        Map<String, BaseSchema<?>> schemas = new HashMap<>();
        schemas.put("firstName", validator.string().required());
        schemas.put("lastName", validator.string().required().minLength(2));
        schemas.put("age", validator.number().range(18, 60));

        MapSchema schema = validator.map().shape(schemas);
        Map<String, Object> date = new HashMap<>();
        date.put("firstName", "Helen");
        assertFalse(schema.isValid(date));

        date.put("lastName", "Garden");
        assertTrue(schema.isValid(date));

        date.put("age", 12);
        assertFalse(schema.isValid(date));

        date.put("age", 22);
        assertTrue(schema.isValid(date));

        date.put("sex", "female");
        assertTrue(schema.isValid(date));
    }

    @Test
    void testMapSchemaEmptyShapeRule() {
        Map<String, BaseSchema<?>> schemas = new HashMap<>();
        MapSchema schema = validator.map().shape(schemas);
        Map<String, String> date = new HashMap<>();
        date.put("key", "value");
        assertTrue(schema.isValid(date));
    }

    @Test
    void testMapSchemaCombinedRules() {
        Map<String, BaseSchema<?>> schemas = new HashMap<>();
        schemas.put("key1", validator.string().required().minLength(5).contains("1"));

        MapSchema schema = validator.map().required().shape(schemas).sizeof(2);
        assertFalse(schema.isValid(null));
        assertFalse(schema.isValid(new HashMap<>()));

        Map<String, String> data1 = new HashMap<>();
        data1.put("key1", "value1");
        assertFalse(schema.isValid(data1));
        data1.put("key2", "value2");
        assertTrue(schema.isValid(data1));
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
