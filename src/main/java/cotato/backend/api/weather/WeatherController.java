package cotato.backend.api.weather;

import cotato.backend.domain.weather.dto.WeatherDetailResponseDto;
import cotato.backend.domain.weather.service.query.WeatherQueryService;
import cotato.backend.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/locations/{id}/weather")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherQueryService weatherQueryService;

    @GetMapping
    public ApiResponse<WeatherDetailResponseDto> getTodayWeather(@PathVariable("id") Long locationId) {
        WeatherDetailResponseDto result = weatherQueryService.getTodayWeather(locationId);
        return ApiResponse.success(result);
    }
}
