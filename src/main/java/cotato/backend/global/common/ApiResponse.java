package cotato.backend.global.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class ApiResponse<T> {

    private final boolean success;
    private final T data;
    private final String message;
    private final String path;
    private final LocalDateTime timestamp;

    public static <T> ApiResponse<T> success(T data, String path) {
        return ApiResponse.<T>builder()
                .success(true)
                .data(data)
                .message(null)
                .path(path)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> ApiResponse<T> fail(String message, String path) {
        return ApiResponse.<T>builder()
                .success(false)
                .data(null)
                .message(message)
                .path(path)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
