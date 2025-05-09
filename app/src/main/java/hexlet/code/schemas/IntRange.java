package hexlet.code.schemas;

public final class IntRange {
    private final int min;
    private final int max;

    public IntRange(int pMin, int pMax) {
        if (pMin > pMax) {
            throw new IllegalArgumentException("Минимальное значение диапазона не может быть больше максимального");
        }
        this.min = pMin;
        this.max = pMax;
    }

    public boolean isIncluded(Integer value) {
        if (value == null) {
            return false;
        }
        return value >= min && value <= max;
    }
}
