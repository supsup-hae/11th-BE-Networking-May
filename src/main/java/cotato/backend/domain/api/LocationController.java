package cotato.backend.domain.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cotato.backend.domain.location.dto.request.LocationRequestDto;
import cotato.backend.domain.location.dto.response.LocationResponseDto;
import cotato.backend.domain.location.service.command.LocationCommandService;
import cotato.backend.domain.location.service.query.LocationQueryService;
import cotato.backend.global.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Tag(name = "API", description = "위치 기반 서비스 API 입니다.")
public class LocationController {

	private final LocationCommandService commandService;
	private final LocationQueryService queryService;

	@PostMapping
	@Operation(summary = "장소 저장", description = "새로운 장소를 등록합니다.")
	public ResponseEntity<ApiResponse<LocationResponseDto>> saveLocation(
		@Valid @RequestBody LocationRequestDto requestDto) {
		LocationResponseDto response = commandService.saveLocation(requestDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(response));
	}

	@GetMapping
	@Operation(summary = "장소 전체 조회", description = "등록된 모든 장소 목록을 조회합니다.")
	public ResponseEntity<ApiResponse<List<LocationResponseDto>>> retrieveAllLocations() {
		List<LocationResponseDto> response = queryService.findAllLocations();
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	@GetMapping("/{id}")
	@Operation(summary = "장소 상세 조회", description = "장소 ID로 단일 장소의 상세 정보를 조회합니다.")
	public ResponseEntity<ApiResponse<LocationResponseDto>> retrieveLocationDetail(@PathVariable Long id) {
		LocationResponseDto response = queryService.findLocationById(id);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	@PostMapping("/{id}/pin")
	@Operation(summary = "장소 핀 토글", description = "장소의 핀(고정) 상태를 토글합니다.")
	public ResponseEntity<ApiResponse<LocationResponseDto>> togglePinLocation(@PathVariable Long id) {
		LocationResponseDto response = commandService.togglePin(id);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	@DeleteMapping("{id}")
	@Operation(summary = "장소 삭제", description = "저장된 장소를 삭제합니다.")
	public ResponseEntity<ApiResponse<Void>> deleteLocation(@PathVariable Long id) {
		commandService.deleteLocation(id);
		return ResponseEntity.noContent().build();
	}
}
