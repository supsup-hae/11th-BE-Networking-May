package cotato.backend.domain.weather.service.query;

import cotato.backend.domain.weather.dto.HourlyWeatherResponseDto;
import cotato.backend.domain.weather.dto.WeatherDetailResponseDto;
import cotato.backend.domain.weather.dto.WeeklyWeatherResponseDto;

public interface WeatherQueryService {
    WeatherDetailResponseDto getTodayWeather(Long locationId);
    HourlyWeatherResponseDto getHourlyWeather(Long locationId);
    WeeklyWeatherResponseDto getWeeklyWeather(Long locationId);

}
