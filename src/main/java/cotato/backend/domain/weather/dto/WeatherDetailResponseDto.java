package cotato.backend.domain.weather.dto;

import lombok.Builder;

@Builder
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
}
