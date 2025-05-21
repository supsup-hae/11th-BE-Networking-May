package cotato.backend.infra.weather;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import cotato.backend.domain.weather.converter.WeatherConverter;
import cotato.backend.domain.weather.dto.HourlyWeatherResponseDto;
import cotato.backend.domain.weather.dto.WeatherDetailResponseDto;
import cotato.backend.domain.weather.dto.WeeklyWeatherResponseDto;
import cotato.backend.domain.weather.enums.UvIndexLevel;
import cotato.backend.global.error.ErrorCode;
import cotato.backend.infra.weather.dto.openweather.DailyWeatherApiResponse;
import cotato.backend.infra.weather.dto.openweather.HourlyWeatherApiResponse;
import cotato.backend.infra.weather.dto.openweather.WeeklyWeatherApiResponse;
import cotato.backend.infra.weather.excpetion.WeatherException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class WeatherClient {

	private final RestTemplate restTemplate;
	private final FineDustClient fineDustClient;

	@Value("${external.openweather.key}")
	private String apiKey;

	public WeatherDetailResponseDto getCurrentWeather(double lat, double lon) {
		String uri = UriComponentsBuilder.fromUriString("https://api.openweathermap.org/data/3.0/onecall/timemachine")
			.queryParam("lat", lat)
			.queryParam("lon", lon)
			.queryParam("dt", Instant.now().getEpochSecond())
			.queryParam("appid", apiKey)
			.queryParam("units", "metric")
			.queryParam("lang", "kr")
			.toUriString();
		DailyWeatherApiResponse response =
			Optional.ofNullable(restTemplate.getForObject(uri, DailyWeatherApiResponse.class))
				.orElseThrow(() -> new WeatherException(ErrorCode.EXTERNAL_API_FAIL));

		log.info(String.valueOf(response));
		DailyWeatherApiResponse.Data data = response.data().get(0);
		String windDirection = convertWindDirection(data.wind_deg());
		String uvLevel = UvIndexLevel.fromValue(data.uvi());
		String timeOfDay = resolveTimeOfDay(LocalTime.now(ZoneId.of("Asia/Seoul")));
		// DustResult dustResult = fineDustClient.getDustLevels("종로구");

		return WeatherConverter.toDetailResponse(data, windDirection, uvLevel, timeOfDay);
	}

	private String resolveTimeOfDay(LocalTime now) {
		int hour = now.getHour();
		if (hour >= 5 && hour < 12)
			return "오전";
		else if (hour >= 12 && hour < 18)
			return "오후";
		else
			return "야간";
	}

	private String convertToTime(long unix) {
		return Instant.ofEpochSecond(unix)
			.atZone(ZoneId.of("Asia/Seoul"))
			.toLocalTime()
			.format(DateTimeFormatter.ofPattern("HH:mm"));
	}

	private String convertWindDirection(int deg) {
		String[] directions = {
			"북풍", "북동풍", "동풍", "남동풍",
			"남풍", "남서풍", "서풍", "북서풍"
		};
		return directions[(int)Math.round(((double)deg % 360) / 45) % 8];
	}

	public HourlyWeatherResponseDto getHourlyWeather(double lat, double lon) {
		String uri = UriComponentsBuilder.fromUriString("https://api.openweathermap.org/data/3.0/onecall")
			.queryParam("lat", lat)
			.queryParam("lon", lon)
			.queryParam("exclude", "current,minutely,daily")
			.queryParam("appid", apiKey)
			.queryParam("units", "metric")
			.queryParam("lang", "kr")
			.toUriString();

		HourlyWeatherApiResponse response = Optional.ofNullable(
				restTemplate.getForObject(uri, HourlyWeatherApiResponse.class))
			.orElseThrow(() -> new WeatherException(ErrorCode.EXTERNAL_API_FAIL));

		List<HourlyWeatherResponseDto.HourlyWeather> hourlyWeather = response.hourly().stream()
			.limit(24) // 24시간치만 가져오기
			.map(hourData -> {
				LocalDateTime time = Instant.ofEpochSecond(hourData.dt())
					.atZone(ZoneId.of("Asia/Seoul"))
					.toLocalDateTime();

				return HourlyWeatherResponseDto.HourlyWeather.builder()
					.time(time)
					.temp(hourData.temp())
					.status(hourData.weather().get(0).description())
					.humidity(hourData.humidity())
					.windSpeed(hourData.windSpeed())
					.windDirection(convertWindDirection(hourData.windDeg()))
					.build();
			})
			.toList();

		return HourlyWeatherResponseDto.builder()
			.hourlyWeather(hourlyWeather)
			.timestamp(Instant.now().toString())
			.build();
	}

	public WeeklyWeatherResponseDto getWeeklyWeather(double lat, double lon) {
		String uri = UriComponentsBuilder.fromUriString("https://api.openweathermap.org/data/3.0/onecall")
			.queryParam("lat", lat)
			.queryParam("lon", lon)
			.queryParam("exclude", "current,minutely,hourly")
			.queryParam("appid", apiKey)
			.queryParam("units", "metric")
			.queryParam("lang", "kr")
			.toUriString();

		WeeklyWeatherApiResponse response = Optional.ofNullable(
				restTemplate.getForObject(uri, WeeklyWeatherApiResponse.class))
			.orElseThrow(() -> new WeatherException(ErrorCode.EXTERNAL_API_FAIL));

		List<WeeklyWeatherResponseDto.DailyWeather> dailyWeather = response.daily().stream()
			.limit(5) // 5일치만 가져오기
			.map(dayData -> {
				LocalDate date = Instant.ofEpochSecond(dayData.dt())
					.atZone(ZoneId.of("Asia/Seoul"))
					.toLocalDate();

				WeeklyWeatherResponseDto.WeatherInfo morningWeather = WeeklyWeatherResponseDto.WeatherInfo.builder()
					.temp(dayData.temp().morn())
					.status(dayData.weather().get(0).description())
					.humidity(dayData.humidity())
					.windSpeed(dayData.windSpeed())
					.windDirection(convertWindDirection(dayData.windDeg()))
					.build();

				WeeklyWeatherResponseDto.WeatherInfo afternoonWeather = WeeklyWeatherResponseDto.WeatherInfo.builder()
					.temp(dayData.temp().day())
					.status(dayData.weather().get(0).description())
					.humidity(dayData.humidity())
					.windSpeed(dayData.windSpeed())
					.windDirection(convertWindDirection(dayData.windDeg()))
					.build();

				return WeeklyWeatherResponseDto.DailyWeather.builder()
					.date(date)
					.dayOfWeek(date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREAN))
					.morning(morningWeather)
					.afternoon(afternoonWeather)
					.build();
			})
			.collect(Collectors.toList());

		return WeeklyWeatherResponseDto.builder()
			.dailyWeather(dailyWeather)
			.timestamp(Instant.now().toString())
			.build();
	}
}
