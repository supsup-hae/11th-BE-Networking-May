package cotato.backend.domain.weather.converter;

import cotato.backend.domain.weather.dto.WeatherDetailResponseDto;
import cotato.backend.domain.weather.dto.Wind;
import cotato.backend.infra.weather.dto.openweather.DailyWeatherApiResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class WeatherConverter {

	public WeatherDetailResponseDto toDetailResponse(
		DailyWeatherApiResponse.Data data,
		String windDirection,
		String uvLevel,
		String timeOfDay
	) {
		return WeatherDetailResponseDto.builder()
			.temp(data.temp())
			.timeOfDay(timeOfDay)
			.status(DescriptionConverter.convert(data.weather().get(0).icon()))
			.feelsLike(data.feels_like())
			.humidity(data.humidity())
			.wind(Wind.builder()
				.direction(windDirection)
				.speed(data.wind_speed())
				.build())
			// .dust(dustResult.dust())
			// .ultraFineDust(dustResult.ultraFineDust())
			.uv(uvLevel)
			.sunrise(convertToTime(data.sunrise()))
			.build();
	}

	private String convertToTime(long unix) {
		return java.time.Instant.ofEpochSecond(unix)
			.atZone(java.time.ZoneId.of("Asia/Seoul"))
			.toLocalTime()
			.format(java.time.format.DateTimeFormatter.ofPattern("HH:mm"));
	}
}
