// src/main/java/cotato/backend/domain/weather/dto/WeeklyWeatherResponseDto.java
package cotato.backend.domain.weather.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Builder;

@Builder
public record WeeklyWeatherResponseDto(
	List<DailyWeather> dailyWeather,
	String timestamp
) {
	@Builder
	public record DailyWeather(
		LocalDate date,
		String dayOfWeek,
		WeatherInfo morning,
		WeatherInfo afternoon
	) {
	}

	@Builder
	public record WeatherInfo(
		double temp,
		String status,
		int humidity,
		double windSpeed,
		String windDirection
	) {
	}
}