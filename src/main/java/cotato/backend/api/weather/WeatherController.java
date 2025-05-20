package cotato.backend.api.weather;

import cotato.backend.domain.weather.dto.HourlyWeatherResponseDto;
import cotato.backend.domain.weather.dto.WeatherDetailResponseDto;
import cotato.backend.domain.weather.service.WeatherService;
import cotato.backend.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/locations/{id}/weather")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherQueryService;

    @GetMapping
    public ResponseEntity<ApiResponse<WeatherDetailResponseDto>> getTodayWeather(@PathVariable("id") Long locationId) {
        WeatherDetailResponseDto result = weatherQueryService.getTodayWeather(locationId);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @GetMapping("/hourly")
    public ResponseEntity<ApiResponse<HourlyWeatherResponseDto>> getHourlyWeather(@PathVariable("id") Long locationId) {
        HourlyWeatherResponseDto result = weatherQueryService.getHourlyWeather(locationId);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @GetMapping("/weekly")
    public ResponseEntity<ApiResponse<?>> getWeeklyWeather(@PathVariable("id") Long locationId) {
        return ResponseEntity.ok(ApiResponse.success(weatherQueryService.getWeeklyWeather(locationId)));
    }





}
