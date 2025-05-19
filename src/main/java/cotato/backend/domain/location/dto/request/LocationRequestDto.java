package cotato.backend.domain.location.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LocationRequestDto(@NotBlank(message = "위치 이름은 공백일 수 없습니다.")
								 String name,
								 @NotNull(message = "위도 정보는 필수 값입니다.")
								 BigDecimal latitude,
								 @NotNull(message = "경도 정보는 필수 값입니다.")
								 BigDecimal longitude
) {
}
