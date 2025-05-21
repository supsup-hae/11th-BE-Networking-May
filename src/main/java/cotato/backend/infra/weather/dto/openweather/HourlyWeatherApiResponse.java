package cotato.backend.infra.weather.dto.openweather;

import java.util.List;

public record HourlyWeatherApiResponse(
	List<HourlyWeatherDto> hourly
) {}