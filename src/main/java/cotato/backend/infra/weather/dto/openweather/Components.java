package cotato.backend.infra.weather.dto.openweather;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Components(
	double co,
	double no,
	double no2,
	double o3,
	double so2,
	@JsonProperty("pm2_5") double pm25,
	double pm10,
	double nh3
) {
}