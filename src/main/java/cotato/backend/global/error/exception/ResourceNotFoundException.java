package cotato.backend.global.error.exception;

import cotato.backend.global.error.ErrorCode;

public class ResourceNotFoundException extends CustomException {
	public ResourceNotFoundException(ErrorCode errorCode) {
		super(errorCode);
	}
}
