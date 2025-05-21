package cotato.backend.infra.weather.dto.dust;

import java.util.List;

public record DustApiResponseDto(Response response) {
    public record Response(Body body) {}
    public record Body(List<Item> items) {}
    public record Item(String pm10Value, String pm25Value) {}
}
