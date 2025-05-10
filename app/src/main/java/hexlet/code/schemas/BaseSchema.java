package hexlet.code.schemas;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public abstract class BaseSchema<T> {
    protected Map<String, Predicate<Object>> rules = new HashMap<>();
    protected Predicate<Object> defaultRule;
    protected boolean isRequired;

    protected final void addDefaultRules(Predicate<Object> dRule) {
        this.defaultRule = dRule;
    }

    protected final void addRules(String ruleName, Predicate<Object> predicate) {
        this.rules.put(ruleName, predicate);
    }

    public final boolean isValid(Object value) {
        if (value == null && isRequired) {
            Predicate<Object> requiredRule = rules.get("required");
            return requiredRule == null || requiredRule.test(null);
        }
        if (defaultRule != null && !defaultRule.test(value)) {
            return false;
        }
        for (Predicate<Object> rule : rules.values()) {
            if (!rule.test(value)) {
                return false;
            }
        }
        return true;
    }
}
