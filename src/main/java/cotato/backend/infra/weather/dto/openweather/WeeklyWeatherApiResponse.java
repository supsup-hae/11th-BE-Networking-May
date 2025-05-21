package cotato.backend.infra.weather.dto.openweather;

import java.util.List;

public record WeeklyWeatherApiResponse(
	List<WeeklyWeatherDto> daily
) {
}