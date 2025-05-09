package hexlet.code;

import hexlet.code.schemas.StringSchema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidatorStringSchemaTests {
    private Validator validator;

    @BeforeEach
    void validatorPreparation() {
        validator = new Validator();
    }

    @ParameterizedTest
    @NullSource
    @EmptySource
    @ValueSource(strings = {"some text", "word", "l", "6", " "})
    void testDefaultStringSchema(String value) {
        StringSchema schema = validator.string();
        assertTrue(schema.isValid(value));
    }

    @ParameterizedTest
    @NullSource
    @EmptySource
    @ValueSource(strings = {"some text", " "})
    void testStringSchemaRequiredRule(String value) {
        StringSchema schema = validator.string().required();
        if (value == null || value.isEmpty()) {
            assertFalse(schema.isValid(value));
        } else {
            assertTrue(schema.isValid(value));
        }
    }

    @Test
    void testStringSchemaMinLengthRule() {
        StringSchema schema = validator.string().minLength(4);
        assertTrue(schema.isValid("something"));
        assertFalse(schema.isValid("x**"));

        schema.minLength(2);
        assertTrue(schema.isValid("something"));
        assertTrue(schema.isValid("x**"));
    }

    @Test
    void testStringSchemaContainsRule() {
        StringSchema schema = validator.string().contains("s");
        assertTrue(schema.isValid("something"));
        assertTrue(schema.isValid("s***thing"));

        schema.contains("some");
        assertTrue(schema.isValid("something"));
        assertFalse(schema.isValid("s***thing"));
    }

    @Test
    void testStringSchemaCombinedRule() {
        StringSchema schema = validator.string()
                .required()
                .minLength(6)
                .contains("text");
        assertTrue(schema.isValid("this text must be correct"));
        assertFalse(schema.isValid(""));
        assertFalse(schema.isValid(null));
        assertFalse(schema.isValid("this x** must not be correct"));

        schema.contains("x**")
                .minLength(9)
                .required();
        assertFalse(schema.isValid("this text must be correct"));
        assertFalse(schema.isValid(""));
        assertFalse(schema.isValid(null));
        assertTrue(schema.isValid("this x** text must be correct"));
        assertFalse(schema.isValid("x** text"));
    }

    @Test
    void testStringSchemaNonStringValue() {
        StringSchema schema = validator.string();
        int value1 = 5;
        assertFalse(schema.isValid(value1));
        boolean value2 = true;
        assertFalse(schema.isValid(value2));
    }
}
