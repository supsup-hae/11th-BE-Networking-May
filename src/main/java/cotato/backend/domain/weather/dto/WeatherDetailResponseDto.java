package cotato.backend.domain.weather.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WeatherDetailResponseDto {
    private double temp;
    private String timeOfDay;
    private String status;
    private double feelsLike;
    private int humidity;
    private Wind wind;
    private String dust;
    private String ultraFineDust;
    private String uv;
    private String sunrise;

    @Getter
    @Builder
    public static class Wind {
        private String direction;
        private double speed;
    }
}
