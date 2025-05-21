package cotato.backend.domain.weather.service;

import org.springframework.stereotype.Service;

import cotato.backend.domain.location.entity.Location;
import cotato.backend.domain.location.service.query.LocationQueryService;
import cotato.backend.domain.weather.dto.HourlyWeatherResponseDto;
import cotato.backend.domain.weather.dto.WeatherDetailResponseDto;
import cotato.backend.domain.weather.dto.WeeklyWeatherResponseDto;
import cotato.backend.infra.weather.WeatherClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {

	private final WeatherClient weatherClient;
	private final LocationQueryService locationQueryService;

	@Override
	public WeatherDetailResponseDto getTodayWeather(Long locationId) {
		Location location = locationQueryService.findLocation(locationId);

		return weatherClient.getCurrentWeather(
			location.getLatitude().doubleValue(),
			location.getLongitude().doubleValue()
		);
	}

	@Override
	public HourlyWeatherResponseDto getHourlyWeather(Long locationId) {
		Location location = locationQueryService.findLocation(locationId);

		return weatherClient.getHourlyWeather(
			location.getLatitude().doubleValue(),
			location.getLongitude().doubleValue());
	}

	@Override
	public WeeklyWeatherResponseDto getWeeklyWeather(Long locationId) {
		Location location = locationQueryService.findLocation(locationId);

		return weatherClient.getWeeklyWeather(
			location.getLatitude().doubleValue(),
			location.getLongitude().doubleValue());
	}
}
