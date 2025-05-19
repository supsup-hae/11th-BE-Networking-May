package cotato.backend.domain.location.service.command;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cotato.backend.domain.location.converter.LocationConverter;
import cotato.backend.domain.location.dto.request.LocationRequestDto;
import cotato.backend.domain.location.dto.response.LocationResponseDto;
import cotato.backend.domain.location.entity.Location;
import cotato.backend.domain.location.exception.LocationException;
import cotato.backend.domain.location.repository.LocationRepository;
import cotato.backend.global.error.ErrorCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class LocationCommandService {

	private final LocationConverter locationConverter;
	private final LocationRepository locationRepository;

	public LocationResponseDto saveLocation(LocationRequestDto requestDto) {
		validateDuplicateLocation(requestDto.name());

		Location location = locationConverter.toEntity(requestDto);
		Location savedLocation = locationRepository.save(location);

		log.info("[Location] 장소 저장 완료 - {}", requestDto.name());
		return locationConverter.toResponse(savedLocation);
	}

	public LocationResponseDto togglePin(Long id) {
		Location location = locationRepository.findById(id)
			.orElseThrow(() -> new LocationException(ErrorCode.LOCATION_NOT_FOUND));

		location.togglePin();

		log.info("[Location] 핀 토글 완료 {} - {}", location.getName(), location.getIsPinned());
		return locationConverter.toResponse(location);
	}

	public void deleteLocation(Long id) {
		Location location = locationRepository.findById(id)
			.orElseThrow(() -> new LocationException(ErrorCode.LOCATION_NOT_FOUND));

		locationRepository.delete(location);
	}

	private void validateDuplicateLocation(String name) {
		if (locationRepository.existsByName(name)) {
			throw new LocationException(ErrorCode.ALREADY_EXISTS_LOCATION);
		}
	}

}