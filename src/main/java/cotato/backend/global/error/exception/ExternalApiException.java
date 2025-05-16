package cotato.backend.global.error.exception;

import cotato.backend.global.error.ErrorCode;

public class ExternalApiException extends CustomException {
	public ExternalApiException(ErrorCode errorCode) {
		super(errorCode);
	}
}
