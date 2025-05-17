package cotato.backend.domain.location.converter;

import org.springframework.stereotype.Component;

import cotato.backend.domain.location.dto.request.LocationRequestDto;
import cotato.backend.domain.location.dto.response.LocationResponseDto;
import cotato.backend.domain.location.entity.Location;

@Component
public class LocationConverter {
	public Location toEntity(LocationRequestDto requestDto) {
		return Location.builder()
			.name(requestDto.name())
			.latitude(requestDto.latitude())
			.longitude(requestDto.longitude())
			.build();
	}

	public LocationResponseDto toResponse(Location location) {
		return LocationResponseDto.builder()
			.id(location.getId())
			.name(location.getName())
			.latitude(location.getLatitude())
			.longitude(location.getLongitude())
			.isPinned(location.getIsPinned())
			.build();
	}
}
