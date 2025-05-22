package cotato.backend.infra.weather;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import cotato.backend.global.error.ErrorCode;
import cotato.backend.infra.weather.dto.openweather.Components;
import cotato.backend.infra.weather.dto.openweather.DustResult;
import cotato.backend.infra.weather.dto.openweather.OpenWeatherMapAirPollutionResponse;
import cotato.backend.infra.weather.excpetion.WeatherException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FineDustClient {
	private final RestTemplate restTemplate;

	@Value("${external.openweather.key}")
	private String apiKey;

	public DustResult getDustLevels(double lat, double lon) {
		String uri = UriComponentsBuilder
			.fromUriString("http://api.openweathermap.org/data/2.5/air_pollution")
			.queryParam("lat", lat)
			.queryParam("lon", lon)
			.queryParam("appid", apiKey)
			.build()
			.toUriString();

		OpenWeatherMapAirPollutionResponse response = Optional.ofNullable(
				restTemplate.getForObject(uri, OpenWeatherMapAirPollutionResponse.class))
			.orElseThrow(() -> new WeatherException(ErrorCode.EXTERNAL_API_FAIL));

		Components components = response.list().get(0).components();
		String dust = String.valueOf(Math.round(components.pm10()));
		String ultraFineDust = String.valueOf(Math.round(components.pm25()));
		return new DustResult(dust, ultraFineDust);
	}
}