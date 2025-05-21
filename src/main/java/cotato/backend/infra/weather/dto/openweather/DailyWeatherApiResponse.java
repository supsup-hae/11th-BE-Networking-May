package cotato.backend.infra.weather.dto.openweather;

import java.util.List;

import lombok.Builder;

@Builder
public record DailyWeatherApiResponse(
	double lat,
	double lon,
	String timezone,
	int timezone_offset,
	List<Data> data
) {
	@Builder
	public record Data(
		long dt,
		long sunrise,
		long sunset,
		double temp,
		double feels_like,
		int pressure,
		int humidity,
		double dew_point,
		double uvi,
		int clouds,
		int visibility,
		double wind_speed,
		int wind_deg,
		List<Weather> weather
	) {
	}

	@Builder
	public record Weather(
		int id,
		String main,
		String description,
		String icon
	) {
	}
}