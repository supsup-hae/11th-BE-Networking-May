package cotato.backend.domain.weather.dto;

import java.util.List;

public record HourlyWeatherResponseDto(
        List<HourlyWeather> data,
        String timestamp
) {
    public record HourlyWeather(
            String time,
            double temp,
            String status
    ) {}
}