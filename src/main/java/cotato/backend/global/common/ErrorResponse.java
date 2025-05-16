package cotato.backend.global.common;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;

import cotato.backend.global.error.ErrorCode;
import lombok.Builder;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)

@Getter
public class ErrorResponse {
	private final int status;
	private final String error;
	private final String message;
	private final String path;
	private final LocalDateTime timestamp;

	@Builder
	protected ErrorResponse(final ErrorCode code, final String message, final String path) {
		this.timestamp = LocalDateTime.now();
		this.status = code.getStatus().value();
		this.error = HttpStatus.valueOf(code.getStatus().value()).getReasonPhrase();
		this.message = message;
		this.path = path;
	}

	public static ErrorResponse of(final ErrorCode code, final String message, final String path) {
		return new ErrorResponse(code, message, path);
	}
}
