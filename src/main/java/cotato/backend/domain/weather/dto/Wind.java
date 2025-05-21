package cotato.backend.domain.weather.dto;

import lombok.Builder;

@Builder
public record Wind(
	String direction,
	double speed
) {
}