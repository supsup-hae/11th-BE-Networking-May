package cotato.backend.infra.weather.excpetion;

import cotato.backend.global.error.ErrorCode;
import cotato.backend.global.error.exception.CustomException;

public class WeatherException extends CustomException {
	public WeatherException(ErrorCode errorCode) {
		super(errorCode);
	}
}
