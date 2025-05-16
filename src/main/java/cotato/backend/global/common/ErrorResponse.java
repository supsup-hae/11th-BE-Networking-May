package cotato.backend.global.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)

@Getter
@Builder
public class ErrorResponse {
    private final boolean success;
    private final String message;
    private final String path;
    private final LocalDateTime timestamp;
}
