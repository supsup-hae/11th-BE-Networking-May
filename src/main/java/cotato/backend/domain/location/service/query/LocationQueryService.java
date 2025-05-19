package cotato.backend.domain.location.service.query;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cotato.backend.domain.location.converter.LocationConverter;
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
@Transactional(readOnly = true)
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class LocationQueryService {

	private final LocationRepository locationRepository;
	private final LocationConverter locationConverter;

	public LocationResponseDto findLocationById(Long id) {
		Location foundLocation = locationRepository.findById(id)
			.orElseThrow(() -> new LocationException(ErrorCode.LOCATION_NOT_FOUND));

		log.info("[Location] 단일 조회 - {}", foundLocation.getName());
		return locationConverter.toResponse(foundLocation);
	}

	public List<LocationResponseDto> findAllLocations() {
		List<Location> locationList = locationRepository.findAll();
		log.info("[Location] 전체 조회 - {}건", locationList.size());
		return locationList.stream()
			.map(locationConverter::toResponse)
			.toList();
	}
}
