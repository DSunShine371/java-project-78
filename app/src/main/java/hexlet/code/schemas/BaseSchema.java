package hexlet.code.schemas;

public abstract class BaseSchema<T> {
    protected boolean isRequired;

    public abstract boolean isValid(Object value);
}
