package hexlet.code.schemas;

public class IntRange {
    private final int min;
    private final int max;

    public IntRange(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("Минимальное значение диапазона не может быть больше максимального");
        }
        this.min = min;
        this.max = max;
    }

    public boolean isIncluded(Integer value) {
        if (value == null) {
            return false;
        }
        return value >= min && value <= max;
    }
}
