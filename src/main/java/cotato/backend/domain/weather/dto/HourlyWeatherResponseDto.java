// src/main/java/cotato/backend/domain/weather/dto/HourlyWeatherResponseDto.java
package cotato.backend.domain.weather.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;

@Builder
public record HourlyWeatherResponseDto(
    List<HourlyWeather> hourlyWeather,
    String timestamp
) {
    @Builder
    public record HourlyWeather(
        LocalDateTime time,
        double temp,
        String status,
        int humidity,
        double windSpeed,
        String windDirection
    ) {}
}