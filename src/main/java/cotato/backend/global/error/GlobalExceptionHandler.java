package cotato.backend.global.error;

import static cotato.backend.global.error.ErrorCode.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import cotato.backend.global.common.ApiResponse;
import cotato.backend.global.common.ErrorResponse;
import cotato.backend.global.error.exception.ExternalApiException;
import cotato.backend.global.error.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	private static final HttpStatus HTTP_STATUS_OK = HttpStatus.OK;

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ApiResponse<ErrorResponse>> handleIllegalArgument(IllegalArgumentException e,
		HttpServletRequest request) {
		log.error("[Error] IllegalArgumentException : {}", e.getMessage());
		log.error("[Error] 발생 이유: {}", e);
		log.error("[Error] 예외 발생 지점 : {} | {}", request.getMethod(), request.getRequestURI());

		ErrorResponse response = ErrorResponse.of(
			INVALID_INPUT_VALUE,
			e.getMessage(),
			request.getRequestURI()
		);

		return new ResponseEntity<>(ApiResponse.failure(response), HTTP_STATUS_OK);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse<ErrorResponse>> handleResourceNotFound(ResourceNotFoundException e,
		HttpServletRequest request) {
		log.error("[Error] ResourceNotFoundException : {}", e.getMessage());
		log.error("[Error] 발생 이유: {}", e);
		log.error("[Error] 예외 발생 지점 : {} | {}", request.getMethod(), request.getRequestURI());

		ErrorResponse response = ErrorResponse.of(
			NOT_FOUND,
			e.getMessage(),
			request.getRequestURI()
		);

		return new ResponseEntity<>(ApiResponse.failure(response), HTTP_STATUS_OK);
	}

	@ExceptionHandler(ExternalApiException.class)
	public ResponseEntity<ApiResponse<ErrorResponse>> handleExternalApiException(ExternalApiException e,
		HttpServletRequest request) {
		log.error("[Error] ExternalApiException : {}", e.getMessage());
		log.error("[Error] 발생 이유: {}", e);
		log.error("[Error] 예외 발생 지점 : {} | {}", request.getMethod(), request.getRequestURI());

		ErrorResponse response = ErrorResponse.of(
			EXTERNAL_API_FAIL,
			e.getMessage(),
			request.getRequestURI()
		);

		return new ResponseEntity<>(ApiResponse.failure(response), HTTP_STATUS_OK);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<ErrorResponse>> handleException(Exception e, HttpServletRequest request) {
		log.error("[Error] Exception : {}", e.getMessage());
		log.error("[Error] 발생 이유: {}", e);
		log.error("[Error] 예외 발생 지점 : {} | {}", request.getMethod(), request.getRequestURI());

		ErrorResponse response = ErrorResponse.of(
			INTERNAL_SERVER_ERROR,
			INTERNAL_SERVER_ERROR.getMessage(),
			request.getRequestURI()
		);

		return new ResponseEntity<>(ApiResponse.failure(response), HTTP_STATUS_OK);
	}
}