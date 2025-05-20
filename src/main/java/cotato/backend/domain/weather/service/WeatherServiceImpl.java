package cotato.backend.domain.weather.service;

import cotato.backend.domain.weather.dto.HourlyWeatherResponseDto;
import cotato.backend.domain.weather.dto.WeatherDetailResponseDto;
import cotato.backend.domain.weather.dto.WeeklyWeatherResponseDto;
import cotato.backend.infra.weather.WeatherClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {

    private final WeatherClient weatherClient;

    @Override
    public WeatherDetailResponseDto getTodayWeather(Long locationId) {
        WeatherDetailResponseDto response = weatherClient.getCurrentWeather(37.5665, 126.9780);
        log.info("🌤️ 날씨 API 응답 = {}", response);

        // 일단 목업(mock) 데이터로 리턴
        return new WeatherDetailResponseDto(
                12.2,
                "야간",
                "흐림",
                9.0,
                48,
                new WeatherDetailResponseDto.Wind("남동풍", 0.4),
                "보통",
                "보통",
                "위험",
                "05:44"
        );

    }

    @Override
    public HourlyWeatherResponseDto getHourlyWeather(Long locationId) {
        return weatherClient.getHourlyWeather(locationId);
    }

    @Override
    public WeeklyWeatherResponseDto getWeeklyWeather(Long locationId) {
        return weatherClient.getWeeklyWeather(locationId);
    }
}
