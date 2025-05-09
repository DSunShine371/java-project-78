package hexlet.code;

import hexlet.code.schemas.StringSchema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ValidatorSourceTests {
    private Validator validator;

    @BeforeEach
    void validatorPreparation() {
        validator = new Validator();
    }

    @Test
    void testValidatorString() {
        assertEquals(validator.string().getClass(), StringSchema.class);
    }
}
