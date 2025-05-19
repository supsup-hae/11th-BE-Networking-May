package cotato.backend.infra.weather;

import cotato.backend.domain.weather.dto.HourlyWeatherResponseDto;
import cotato.backend.domain.weather.dto.WeeklyWeatherResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${weather.api.key}")
    private String apiKey;

    public String getCurrentWeather(double lat, double lon) {
        String uri = UriComponentsBuilder
                .fromHttpUrl("https://api.openweathermap.org/data/2.5/weather")
                .queryParam("lat", lat)
                .queryParam("lon", lon)
                .queryParam("appid", apiKey)
                .queryParam("units", "metric")
                .queryParam("lang", "kr")
                .toUriString();

        return restTemplate.getForObject(uri, String.class);
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

            weatherList.add(HourlyWeatherResponseDto.HourlyWeather.builder()
                    .time(time)
                    .temp(temp)
                    .status(status)
                    .build());
        }


        String timestamp = Instant.now().toString();

        return HourlyWeatherResponseDto.builder()
                .data(weatherList)
                .timestamp(timestamp)
                .build();
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

            WeeklyWeatherResponseDto.TimeWeather morning = WeeklyWeatherResponseDto.TimeWeather.builder()
                    .temp(mornTemp)
                    .status(status)
                    .humidity(humidity)
                    .build();

            WeeklyWeatherResponseDto.TimeWeather afternoon = WeeklyWeatherResponseDto.TimeWeather.builder()
                    .temp(dayTemp)
                    .status(status)
                    .humidity(humidity)
                    .build();

            weeklyWeather.add(WeeklyWeatherResponseDto.DailyWeather.builder()
                    .date(date.toString())
                    .dayOfWeek(dayOfWeek)
                    .morning(morning)
                    .afternoon(afternoon)
                    .build());
        }

        return WeeklyWeatherResponseDto.builder()
                .weeklyWeather(weeklyWeather)
                .timestamp(Instant.now().toString())
                .build();
    }






}
