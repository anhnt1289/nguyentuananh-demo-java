package com.main.api.payload;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author : AnhNT
 * @since : 10/11/2021, Wed
 */
public class DataResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> ResponseEntity<T> success(ResponseData dataResponse) {
		return new ResponseEntity(dataResponse, HttpStatus.OK);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> ResponseEntity<T> error(ResponseData dataResponse) {
		return new ResponseEntity(dataResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
