package cotato.backend.global.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@AllArgsConstructor
@Builder
public class SuccessResponse<T> {

    private final boolean success;
    private final T data;
    private final String message;
    private final String path;
    private final LocalDateTime timestamp;

    public static <T> SuccessResponse<T> success(T data, String path) {
        return SuccessResponse.<T>builder()
                .success(true)
                .data(data)
                .message(null)
                .path(path)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> SuccessResponse<T> fail(String message, String path) {
        return SuccessResponse.<T>builder()
                .success(false)
                .data(null)
                .message(message)
                .path(path)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
