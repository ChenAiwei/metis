package com.boot.metis.feign.exception;

/**
 * 业务异常
 *
 */
public class FeignException extends RuntimeException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -2483599972506697940L;

	public FeignException() {
		super();
	}

	public FeignException(String message, Throwable cause) {
		super(message, cause);
	}

	public FeignException(String message) {
		super(message);
	}

	public FeignException(Throwable cause) {
		super(cause);
	}
}