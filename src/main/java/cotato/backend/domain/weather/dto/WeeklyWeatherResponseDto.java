package cotato.backend.domain.weather.dto;

import java.util.List;

public record WeeklyWeatherResponseDto(
        List<DailyWeather> weeklyWeather,
        String timestamp
) {
    public record DailyWeather(
            String date,
            String dayOfWeek,
            TimeWeather morning,
            TimeWeather afternoon
    ) {}

    public record TimeWeather(
            double temp,
            String status,
            int humidity
    ) {}
}
