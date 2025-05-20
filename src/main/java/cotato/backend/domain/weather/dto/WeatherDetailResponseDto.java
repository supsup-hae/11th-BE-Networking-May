package cotato.backend.domain.weather.dto;

public record WeatherDetailResponseDto(

    double temp,
    String timeOfDay,
    String status,
    double feelsLike,
    int humidity,
    Wind wind,
    String dust,
    String ultraFineDust,
    String uv,
    String sunrise
) {
    public record Wind(
            String direction,
            double speed
    ) {}
}
