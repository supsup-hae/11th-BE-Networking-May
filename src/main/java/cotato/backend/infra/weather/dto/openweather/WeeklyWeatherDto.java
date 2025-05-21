package cotato.backend.infra.weather.dto.openweather;

import java.util.List;

public record WeeklyWeatherDto(
	long dt,
	long sunrise,
	long sunset,
	long moonrise,
	long moonset,
	double moonPhase,
	String summary,
	Temp temp,
	Temp feelsLike,
	int pressure,
	int humidity,
	double dewPoint,
	double windSpeed,
	int windDeg,
	double windGust,
	List<Weather> weather,
	int clouds,
	double pop,
	double rain,
	double uvi
) {
	public record Temp(
		double day,
		double min,
		double max,
		double night,
		double eve,
		double morn
	) {
	}

	public record Weather(
		int id,
		String main,
		String description,
		String icon
	) {
	}
}
