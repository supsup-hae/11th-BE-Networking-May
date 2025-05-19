package cotato.backend.domain.weather.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class WeeklyWeatherResponseDto {

    private List<DailyWeather> weeklyWeather;  // 기능명세에 맞게 수정
    private String timestamp;

    @Getter
    @Builder
    @AllArgsConstructor
    public static class DailyWeather {
        private String date;         // 예: 2025-05-20
        private String dayOfWeek;    // 예: 월, 화, 수...
        private TimeWeather morning; // 오전 날씨 정보
        private TimeWeather afternoon; // 오후 날씨 정보
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class TimeWeather {
        private double temp;      // 온도
        private String status;    // 날씨 상태 (맑음, 흐림 등)
        private int humidity;     // 습도 (%)
    }
}
