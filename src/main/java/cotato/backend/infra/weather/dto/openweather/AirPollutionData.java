package cotato.backend.infra.weather.dto.openweather;

public record AirPollutionData(
	long dt,
	AirQualityIndex main,
	Components components
) {}