package hexlet.code.schemas;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public abstract class BaseSchema<T> {
    protected List<Predicate<Object>> rules = new ArrayList<>();
    protected boolean isRequired;

    protected void addRules(Predicate<Object> predicate) {
        this.rules.add(predicate);
    }

    public boolean isValid(Object value) {
        if (value == null && isRequired) {
            for (Predicate<Object> rule : rules) {
                if (!rule.test(null)) {
                    return false;
                }
            }
            return true;
        }
        for (Predicate<Object> rule : rules) {
            if (!rule.test(value)) {
                return false;
            }
        }
        return true;
    }
}
