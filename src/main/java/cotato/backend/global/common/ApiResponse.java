package cotato.backend.global.common;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

	private final boolean success;
	private final T data;
	private final ErrorResponse error;

	public static <T> ApiResponse<T> success(T data) {
		return ApiResponse.<T>builder()
			.success(true)
			.data(data)
			.build();
	}

	public static <T> ApiResponse<T> failure(ErrorResponse error) {
		return ApiResponse.<T>builder()
			.success(false)
			.error(error)
			.build();
	}
}
