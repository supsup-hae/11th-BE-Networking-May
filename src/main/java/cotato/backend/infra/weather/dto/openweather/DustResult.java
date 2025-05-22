package cotato.backend.infra.weather.dto.openweather;

public record DustResult(
	String pm10Value,
	String pm25Value,
	int aqi,
	String aqiLevel
) {
	// 기존 생성자 유지
	public DustResult(String pm10Value, String pm25Value) {
		this(pm10Value, pm25Value, 0, "정보 없음");
	}

	// AQI 수준에 따른 텍스트 반환
	public static String getAqiLevelText(int aqi) {
		return switch (aqi) {
			case 1 -> "좋음";
			case 2 -> "보통";
			case 3 -> "적당";
			case 4 -> "나쁨";
			case 5 -> "매우 나쁨";
			default -> "정보 없음";
		};
	}
}