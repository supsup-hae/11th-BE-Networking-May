package cotato.backend.domain.weather.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public enum WeatherCode {
	CLEAR("01", "clear sky"),
	FEW_CLOUDS("02", "few clouds"),
	SCATTERED_CLOUDS("03", "scattered clouds"),
	BROKEN_CLOUDS("04", "broken clouds"),
	SHOWER_RAIN("09", "shower rain"),
	RAIN("10", "rain"),
	THUNDERSTORM("11", "thunderstorm"),
	SNOW("13", "snow"),
	MIST("50", "mist");

	private final String code;
	private final String description;

	public static Map<String, String> toMap() {
		return Arrays.stream(WeatherCode.values())
			.collect(Collectors.toMap(WeatherCode::getCode, WeatherCode::getDescription));
	}
}