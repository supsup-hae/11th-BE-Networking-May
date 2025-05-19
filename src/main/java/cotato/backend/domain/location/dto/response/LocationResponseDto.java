package cotato.backend.domain.location.dto.response;

import java.math.BigDecimal;

import lombok.Builder;

@Builder
public record LocationResponseDto(Long id,
								  String name,
								  BigDecimal latitude,
								  BigDecimal longitude,
								  boolean isPinned
) {
}
