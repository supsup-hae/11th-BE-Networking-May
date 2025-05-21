package cotato.backend.domain.weather.enums;

import java.util.Arrays;

public enum UltraFineDustLevel {
    GOOD(0, 15, "좋음"),
    MODERATE(16, 35, "보통"),
    BAD(36, 75, "나쁨"),
    VERY_BAD(76, Integer.MAX_VALUE, "매우 나쁨");

    private final int min;
    private final int max;
    private final String label;

    UltraFineDustLevel(int min, int max, String label) {
        this.min = min;
        this.max = max;
        this.label = label;
    }

    public static String fromValue(String value) {
        try {
            int v = Integer.parseInt(value);
            return Arrays.stream(values())
                    .filter(level -> v >= level.min && v <= level.max)
                    .findFirst()
                    .map(UltraFineDustLevel::getLabel)
                    .orElse("정보 없음");
        } catch (NumberFormatException e) {
            return "정보 없음";
        }
    }

    public String getLabel() {
        return label;
    }
}
