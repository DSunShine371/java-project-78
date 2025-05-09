package hexlet.code;

import hexlet.code.schemas.IntRange;
import hexlet.code.schemas.NumberSchema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidatorNumberSchemaTests {
    private Validator validator;

    @BeforeEach
    void validatorPreparation() {
        validator = new Validator();
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(ints = {52, 0, -52})
    void testDefaultNumberSchema(Integer value) {
        NumberSchema schema = validator.number();
        assertTrue(schema.isValid(value));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(ints = {52, 0, -52})
    void testNumberSchemaRequiredRule(Integer value) {
        NumberSchema schema = validator.number().required();
        if (value == null) {
            assertFalse(schema.isValid(value));
        } else {
            assertTrue(schema.isValid(value));
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {52, 0, -52})
    void testNumberSchemaPositiveRule(Integer value) {
        NumberSchema schema = validator.number().positive();
        if (value <= 0) {
            assertFalse(schema.isValid(value));
        } else {
            assertTrue(schema.isValid(value));
        }
    }

    @ParameterizedTest
    @CsvSource(value = {
        "1, 10, 4",
        "-10, 3, -10",
        "-10, 3, 0",
        "-10, 3, 3",
        "4, 8, -2",
        "4, 8, 10"
    })
    void testIntRange(int min, int max, int testValue) {
        IntRange range = new IntRange(min, max);
        assertFalse(range.isIncluded(null));

        if (testValue >= min && testValue <= max) {
            assertTrue(range.isIncluded(testValue));
        } else {
            assertFalse(range.isIncluded(testValue));
        }
    }

    @ParameterizedTest
    @CsvSource(value = {
        "10, 1",
        "3, -10",
    })
    void testIntRangeThrowable(int min, int max) {
        assertThrows(IllegalArgumentException.class, () -> {
            new IntRange(min, max);
        });
    }

    @ParameterizedTest
    @CsvSource(value = {
        "1, 10, 4",
        "-10, 3, -10",
        "-10, 3, 0",
        "-10, 3, 3",
        "4, 8, -2",
        "4, 8, 10"
    })
    void testNumberSchemaRangeRule(int min, int max, int testValue) {
        NumberSchema schema = validator.number().range(min, max);
        if (testValue >= min && testValue <= max) {
            assertTrue(schema.isValid(testValue));
        } else {
            assertFalse(schema.isValid(testValue));
        }
    }

    @ParameterizedTest
    @CsvSource(value = {
        "10, 1",
        "3, -10",
    })
    void testNumberSchemaRangeThrowable(int min, int max) {
        NumberSchema schema = validator.number();
        assertThrows(IllegalArgumentException.class, () -> {
            schema.range(min, max);
        });
    }

    @Test
    void testNumberSchemaCombinedRule() {
        int testValue1 = 0;
        int min1 = -5;
        int max1 = 10;
        NumberSchema schema = validator.number().range(min1, max1);
        assertTrue(schema.isValid(testValue1));
        assertTrue(schema.isValid(null));

        int min2 = 0;
        int max2 = 5;
        schema.range(min2, max2).required();
        assertTrue(schema.isValid(testValue1));
        assertFalse(schema.isValid(null));

        schema.positive();
        assertFalse(schema.isValid(testValue1));
        assertFalse(schema.isValid(null));

        schema.required().positive();
        assertFalse(schema.isValid(testValue1));
        assertFalse(schema.isValid(null));
    }

    @ParameterizedTest
    @ValueSource(strings = {"abc", ""})
    void isValidShouldBeFalseForNonNumericStrings(String value) {
        NumberSchema schema = validator.number();
        assertFalse(schema.isValid(value));
    }

    @Test
    void testNumberSchemaNonIntegerValue() {
        NumberSchema schema = validator.number();
        double value1 = 5.0;
        assertFalse(schema.isValid(value1));
        String value2 = "55";
        assertFalse(schema.isValid(value2));
        boolean value3 = true;
        assertFalse(schema.isValid(value3));
    }
}
