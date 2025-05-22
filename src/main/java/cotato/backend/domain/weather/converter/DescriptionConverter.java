package cotato.backend.domain.weather.converter;

import java.util.Map;
import java.util.Optional;

import cotato.backend.domain.weather.enums.WeatherCode;
import cotato.backend.global.error.ErrorCode;
import cotato.backend.infra.weather.excpetion.WeatherException;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DescriptionConverter {
	private static final Map<String, String> weatherCodeMap = WeatherCode.toMap();

	public String convert(String code) {
		return Optional.of(weatherCodeMap.keySet().stream()
				.filter(code::contains)
				.findFirst()
				.map(weatherCodeMap::get)
				.orElseThrow(() -> new WeatherException(ErrorCode.INVALID_WEATHER_CODE)))
			.get();
	}

}
