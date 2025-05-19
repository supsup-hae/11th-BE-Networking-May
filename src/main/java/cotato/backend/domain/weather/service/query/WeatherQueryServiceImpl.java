package cotato.backend.domain.weather.service.query;

import cotato.backend.domain.weather.dto.HourlyWeatherResponseDto;
import cotato.backend.domain.weather.dto.WeatherDetailResponseDto;
import cotato.backend.infra.weather.WeatherClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeatherQueryServiceImpl implements WeatherQueryService {

    private final WeatherClient weatherClient;

    @Override
    public WeatherDetailResponseDto getTodayWeather(Long locationId) {
        String response = weatherClient.getCurrentWeather(37.5665, 126.9780); // 서울
        System.out.println("🌤️ 날씨 API 응답 = " + response);

        // 일단 목업(mock) 데이터로 리턴
        return WeatherDetailResponseDto.builder()
                .temp(12.2)
                .timeOfDay("야간")
                .status("흐림")
                .feelsLike(9.0)
                .humidity(48)
                .wind(WeatherDetailResponseDto.Wind.builder()
                        .direction("남동풍")
                        .speed(0.4)
                        .build())
                .dust("보통")
                .ultraFineDust("보통")
                .uv("위험")
                .sunrise("05:44")
                .build();
    }
    @Override
    public HourlyWeatherResponseDto getHourlyWeather(Long locationId) {
        return weatherClient.getHourlyWeather(locationId);
    }

}
