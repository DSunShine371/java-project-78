package hexlet.code;

import hexlet.code.schemas.StringSchema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidatorTests {
    Validator validator;

    @BeforeEach
    void validatorPreparation() {
        validator = new Validator();
    }

    @Test
    void testValidatorString() {
        assertEquals(validator.string().getClass(), StringSchema.class);
    }

    @Test
    void testDefaultStringSchema() {
        StringSchema schema = validator.string();
        assertTrue(schema.isValid(null));
        assertTrue(schema.isValid(""));
        assertTrue(schema.isValid("some text"));
        assertTrue(schema.isValid("word"));
        assertTrue(schema.isValid("l"));
        assertTrue(schema.isValid("6"));
    }

    @Test
    void testStringSchemaRequiredRule() {
        StringSchema schema = validator.string().required();
        assertFalse(schema.isValid(""));
        assertFalse(schema.isValid(null));
        assertTrue(schema.isValid("some text"));
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
    void testStringSchemaMultiRule() {
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
}
