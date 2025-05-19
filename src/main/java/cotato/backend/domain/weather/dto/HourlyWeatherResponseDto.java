package cotato.backend.domain.weather.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class HourlyWeatherResponseDto {
    private List<HourlyWeather> data;
    private String timestamp;

    @Getter
    @Builder
    public static class HourlyWeather {
        private String time;     // 예: "00:00"
        private double temp;     // 예: 13.2
        private String status;   // 예: "흐림"
    }
}
