package cotato.backend.domain.location.exception;

import cotato.backend.global.error.ErrorCode;
import cotato.backend.global.error.exception.CustomException;

public class LocationException extends CustomException {
	public LocationException(ErrorCode errorCode) {
		super(errorCode);
	}
}
