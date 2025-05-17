package cotato.backend.global.common;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

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
	private final List<FieldErrorDetail> errors;

	@Builder
	protected ErrorResponse(final ErrorCode code, final String message, final String path) {
		this.timestamp = LocalDateTime.now();
		this.status = code.getStatus().value();
		this.error = HttpStatus.valueOf(code.getStatus().value()).getReasonPhrase();
		this.message = message;
		this.path = path;
		this.errors = new ArrayList<>();
	}

	public static ErrorResponse of(final ErrorCode code, final String message, final String path) {
		return new ErrorResponse(code, message, path);
	}

	public void addValidationErrors(BindingResult bindingResult) {
		bindingResult.getFieldErrors().forEach(this::addValidationError);
	}

	private void addValidationError(FieldError fieldError) {
		this.errors.add(new FieldErrorDetail(
			fieldError.getField(),
			fieldError.getDefaultMessage()
		));
	}

	@Getter
	public static class FieldErrorDetail {
		private final String field;
		private final String message;

		public FieldErrorDetail(String field, String message) {
			this.field = field;
			this.message = message;
		}
	}
}
