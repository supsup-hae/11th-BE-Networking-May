package cotato.backend.domain.weather.enums;

import java.util.Arrays;

public enum UvIndexLevel {
    LOW(0, 2, "낮음"),
    MODERATE(3, 5, "보통"),
    HIGH(6, 7, "높음"),
    VERY_HIGH(8, 10, "매우 높음"),
    EXTREME(11, Integer.MAX_VALUE, "위험");

    private final int min;
    private final int max;
    private final String label;

    UvIndexLevel(int min, int max, String label) {
        this.min = min;
        this.max = max;
        this.label = label;
    }

    public static String fromValue(double value) {
        return Arrays.stream(values())
                .filter(level -> value >= level.min && value <= level.max)
                .findFirst()
                .map(UvIndexLevel::getLabel)
                .orElse("정보 없음");
    }

    public String getLabel() {
        return label;
    }
}
