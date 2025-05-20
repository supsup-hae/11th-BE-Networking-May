package cotato.backend.infra.weather;

import cotato.backend.domain.weather.dto.HourlyWeatherResponseDto;
import cotato.backend.domain.weather.dto.WeatherDetailResponseDto;
import cotato.backend.domain.weather.dto.WeeklyWeatherResponseDto;
import cotato.backend.domain.weather.enums.UvIndexLevel;
import cotato.backend.domain.weather.enums.DustLevel;
import cotato.backend.domain.weather.enums.UltraFineDustLevel;
import cotato.backend.infra.weather.dto.dust.DustApiResponseDto;
import cotato.backend.infra.weather.dto.openweather.OpenWeatherOneCallResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.time.ZoneId;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class WeatherClient {

    private final RestTemplate restTemplate;

    @Value("${OPENWEATHER_API_KEY}")
    private String apiKey;

    @Value("${DUST_API_KEY}")
    private String dustApiKey;

    public WeatherDetailResponseDto getCurrentWeather(double lat, double lon) {
        String uri = UriComponentsBuilder
                .fromHttpUrl("https://api.openweathermap.org/data/3.0/onecall")
                .queryParam("lat", lat)
                .queryParam("lon", lon)
                .queryParam("appid", apiKey)
                .queryParam("units", "metric")
                .queryParam("lang", "kr")
                .toUriString();

        OpenWeatherOneCallResponseDto response =
                restTemplate.getForObject(uri, OpenWeatherOneCallResponseDto.class);

        if (response == null || response.current() == null) {
            throw new RuntimeException("날씨 API 응답이 비어 있습니다.");
        }

        var current = response.current();

        String windDirection = convertWindDirection(current.wind_deg());
        String uvLevel = UvIndexLevel.fromValue(current.uvi());
        String timeOfDay = resolveTimeOfDay(LocalTime.now(ZoneId.of("Asia/Seoul")));

        // 미세먼지 API 호출
        String dustUri = UriComponentsBuilder
                .fromHttpUrl("https://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty")
                .queryParam("serviceKey", dustApiKey)
                .queryParam("returnType", "json")
                .queryParam("numOfRows", 1)
                .queryParam("pageNo", 1)
                .queryParam("stationName", "종로구")
                .queryParam("dataTerm", "DAILY")
                .queryParam("ver", "1.3")
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<DustApiResponseDto> dustResponseEntity = restTemplate.exchange(
                dustUri,
                HttpMethod.GET,
                entity,
                DustApiResponseDto.class
        );

        DustApiResponseDto dustResponse = dustResponseEntity.getBody();



        String dust = "정보 없음";
        String ultraFineDust = "정보 없음";

        try {
            var item = dustResponse.response().body().items().get(0);
            dust = DustLevel.fromValue(item.pm10Value());
            ultraFineDust = UltraFineDustLevel.fromValue(item.pm25Value());
        } catch (Exception e) {
            // 기본값 유지
        }

        return new WeatherDetailResponseDto(
                current.temp(),
                timeOfDay,
                current.weather().get(0).description(),
                current.feels_like(),
                current.humidity(),
                new WeatherDetailResponseDto.Wind(windDirection, current.wind_speed()),
                dust,
                ultraFineDust,
                uvLevel,
                convertToTime(current.sunrise())
        );
    }

    private String resolveTimeOfDay(LocalTime now) {
        int hour = now.getHour();
        if (hour >= 5 && hour < 12) return "오전";
        else if (hour >= 12 && hour < 18) return "오후";
        else return "야간";
    }

    private String convertToTime(long unix) {
        return Instant.ofEpochSecond(unix)
                .atZone(ZoneId.of("Asia/Seoul"))
                .toLocalTime()
                .format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    private String convertWindDirection(int deg) {
        String[] directions = {
                "북풍", "북동풍", "동풍", "남동풍",
                "남풍", "남서풍", "서풍", "북서풍"
        };
        return directions[(int) Math.round(((double) deg % 360) / 45) % 8];
    }




    public HourlyWeatherResponseDto getHourlyWeather(Long locationId) {
        double lat = 37.5665;
        double lon = 126.9780;

        String uri = UriComponentsBuilder
                .fromHttpUrl("https://pro.openweathermap.org/data/2.5/forecast/hourly")
                .queryParam("lat", lat)
                .queryParam("lon", lon)
                .queryParam("appid", apiKey)
                .queryParam("units", "metric")
                .queryParam("exclude", "current,minutely,daily,alerts")  // hourly만 남기기
                .toUriString();

        Map<String, Object> response = restTemplate.getForObject(uri, Map.class);
        List<Map<String, Object>> hourlyList = (List<Map<String, Object>>) response.get("list");
        List<HourlyWeatherResponseDto.HourlyWeather> weatherList = new ArrayList<>();

        for (int i = 0; i < 24 && i < hourlyList.size(); i++) {
            Map<String, Object> hourData = hourlyList.get(i);

            Map<String, Object> mainMap = (Map<String, Object>) hourData.get("main");
            if (mainMap == null || mainMap.get("temp") == null) continue;

            long unixTime = ((Number) hourData.get("dt")).longValue();
            String time = LocalTime.ofInstant(Instant.ofEpochSecond(unixTime), ZoneId.of("Asia/Seoul"))
                    .format(DateTimeFormatter.ofPattern("HH:mm"));

            double temp = ((Number) mainMap.get("temp")).doubleValue();

            List<?> weatherArr = (List<?>) hourData.get("weather");
            String status = "-";
            if (weatherArr != null && !weatherArr.isEmpty()) {
                Map<String, Object> weatherObj = (Map<String, Object>) weatherArr.get(0);
                status = (String) weatherObj.getOrDefault("description", "-");
            }

            weatherList.add(new HourlyWeatherResponseDto.HourlyWeather(time, temp, status));
        }


        String timestamp = Instant.now().toString();

        return new HourlyWeatherResponseDto(weatherList, timestamp);
    }

    public WeeklyWeatherResponseDto getWeeklyWeather(Long locationId) {
        double lat = 37.5665;
        double lon = 126.9780;

        String uri = UriComponentsBuilder
                .fromHttpUrl("https://api.openweathermap.org/data/2.5/forecast/daily")
                .queryParam("lat", lat)
                .queryParam("lon", lon)
                .queryParam("cnt", 5)
                .queryParam("appid", apiKey)
                .queryParam("units", "metric")
                .queryParam("lang", "kr")
                .toUriString();

        Map<String, Object> response = restTemplate.getForObject(uri, Map.class);
        List<Map<String, Object>> dailyList = (List<Map<String, Object>>) response.get("list");

        List<WeeklyWeatherResponseDto.DailyWeather> weeklyWeather = new ArrayList<>();

        for (Map<String, Object> dayData : dailyList) {
            long unixTime = ((Number) dayData.get("dt")).longValue();
            LocalDate date = Instant.ofEpochSecond(unixTime).atZone(ZoneId.of("Asia/Seoul")).toLocalDate();
            String dayOfWeek = date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREAN); // 월, 화, 수...

            Map<String, Object> tempMap = (Map<String, Object>) dayData.get("temp");
            double mornTemp = ((Number) tempMap.get("morn")).doubleValue();
            double dayTemp = ((Number) tempMap.get("day")).doubleValue();

            int humidity = ((Number) dayData.get("humidity")).intValue();  // 전체 습도
            String status = ((Map<String, Object>) ((List<?>) dayData.get("weather")).get(0)).get("description").toString();

            WeeklyWeatherResponseDto.TimeWeather morning = new WeeklyWeatherResponseDto.TimeWeather(
                    mornTemp,
                    status,
                    humidity
            );

            WeeklyWeatherResponseDto.TimeWeather afternoon = new WeeklyWeatherResponseDto.TimeWeather(
                    dayTemp,
                    status,
                    humidity
            );

            weeklyWeather.add(new WeeklyWeatherResponseDto.DailyWeather(
                    date.toString(),
                    dayOfWeek,
                    morning,
                    afternoon
            ));
        }

        return new WeeklyWeatherResponseDto(
                weeklyWeather,
                Instant.now().toString()
        );
    }

}

