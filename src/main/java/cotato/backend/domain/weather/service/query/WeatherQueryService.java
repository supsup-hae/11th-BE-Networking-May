package cotato.backend.domain.weather.service.query;

import cotato.backend.domain.weather.dto.HourlyWeatherResponseDto;
import cotato.backend.domain.weather.dto.WeatherDetailResponseDto;

public interface WeatherQueryService {
    WeatherDetailResponseDto getTodayWeather(Long locationId);
    HourlyWeatherResponseDto getHourlyWeather(Long locationId);

}
