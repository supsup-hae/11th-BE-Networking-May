package cotato.backend.infra.weather;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import cotato.backend.global.error.ErrorCode;
import cotato.backend.infra.weather.dto.dust.DustApiResponseDto;
import cotato.backend.infra.weather.dto.dust.DustResult;
import cotato.backend.infra.weather.excpetion.WeatherException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FineDustClient {
	private final RestTemplate restTemplate;

	@Value("${DUST_API_KEY}")
	private String dustApiKey;

	public DustResult getDustLevels(String stationName) {
		String uri = UriComponentsBuilder
			.fromUriString("http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty")
			.queryParam("serviceKey", "cxjqbABDICEuwL1tYMJWU7Hl6m1aSjfGsisXV072q1En1yIoJlYIdu")
			.queryParam("returnType", "json")
			.queryParam("numOfRows", 1)
			.queryParam("pageNo", 1)
			.queryParam("stationName", stationName)
			.queryParam("dataTerm", "DAILY")
			.queryParam("ver", "1.3")
			.build()
			.toUriString();

		DustApiResponseDto response = Optional.ofNullable(restTemplate.getForObject(uri, DustApiResponseDto.class))
			.orElseThrow(() -> new WeatherException(ErrorCode.EXTERNAL_API_FAIL));

		String dust = "정보 없음";
		String ultraFineDust = "정보 없음";
		try {
			var item = response.response().body().items().get(0);
			dust = item.pm10Value();
			ultraFineDust = item.pm25Value();
		} catch (Exception e) {
			// 기본값 유지
		}
		return new DustResult(dust, ultraFineDust);
	}
}
