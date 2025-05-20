package cotato.backend.infra.weather.dto.openweather;

import java.util.List;

public record OpenWeatherOneCallResponseDto(
        Current current
) {
    public record Current(
            double temp,
            double feels_like,
            int humidity,
            double uvi,
            double wind_speed,
            int wind_deg,
            long sunrise,
            List<Weather> weather
    ) {}

    public record Weather(String description) {}
}
