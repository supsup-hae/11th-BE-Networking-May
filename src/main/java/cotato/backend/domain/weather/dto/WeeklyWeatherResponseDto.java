package cotato.backend.domain.weather.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class WeeklyWeatherResponseDto {

    private List<DailyWeather> data;
    private String timestamp;

    @Getter
    @Builder
    @AllArgsConstructor
    public static class DailyWeather {
        private String date;   // 예: 2025-05-20
        private double temp;
        private String status;
    }
}
