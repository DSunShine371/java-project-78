package hexlet.code.schemas;

public class NumberSchema extends BaseSchema<NumberSchema>  {
    private boolean isPositive;
    private IntRange range;

    public NumberSchema positive() {
        this.isPositive = true;
        return this;
    }

    public NumberSchema range(int min, int max) {
        range = new IntRange(min, max);
        return this;
    }

    @Override
    public boolean isValid(Object value) {
        if (value == null) {
            return !isRequired;
        }
        if (!(value instanceof Integer intValue)) {
            return false;
        }
        if (isPositive && intValue <= 0) {
            return false;
        }
        return this.range == null || this.range.isIncluded(intValue);
    }
}
