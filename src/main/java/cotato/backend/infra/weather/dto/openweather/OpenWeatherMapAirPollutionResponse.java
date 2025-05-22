package cotato.backend.infra.weather.dto.openweather;

import java.util.List;

// 예상되는 OpenWeatherMap API 응답 구조에 맞춰 DTO 클래스 수정
public record OpenWeatherMapAirPollutionResponse(
	Coord coord,
	List<AirPollutionData> list
) {
	public record Coord(
		double lat,
		double lon
	) {
	}
}
