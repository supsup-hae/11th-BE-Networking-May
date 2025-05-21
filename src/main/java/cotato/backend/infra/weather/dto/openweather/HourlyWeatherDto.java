// src/main/java/cotato/backend/infra/weather/dto/openweather/HourlyWeatherDto.java
package cotato.backend.infra.weather.dto.openweather;

import java.util.List;

public record HourlyWeatherDto(
	long dt,
	double temp,
	double feelsLike,
	int pressure,
	int humidity,
	double dewPoint,
	double uvi,
	int clouds,
	int visibility,
	double windSpeed,
	int windDeg,
	double windGust,
	double pop,
	List<Weather> weather
) {
	public record Weather(
		int id,
		String main,
		String description,
		String icon
	) {}
}

