package com.baeldung.common.web;

import com.baeldung.common.web.exception.ApiError;
import com.baeldung.common.web.exception.MyBadRequestException;
import javax.validation.ConstraintViolationException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  private Logger log = LoggerFactory.getLogger(getClass());

  public RestResponseEntityExceptionHandler() {
    super();
  }

  // API

  // 400

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
      HttpMessageNotReadableException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {

    return handleExceptionInternal(ex, message(HttpStatus.BAD_REQUEST, ex), headers,
        HttpStatus.BAD_REQUEST,
        request);
  }

  /**
   * this method is going to be dealing with this method argument not valid exception
   */
  @Override
  protected final ResponseEntity<Object> handleMethodArgumentNotValid(
      final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status,
      final WebRequest request) {
    return handleExceptionInternal(ex, message(HttpStatus.BAD_REQUEST, ex), headers,
        HttpStatus.BAD_REQUEST, request);
  }


  @ExceptionHandler(value = {DataIntegrityViolationException.class, MyBadRequestException.class})
  protected final ResponseEntity<Object> handleBadRequest(final RuntimeException ex,
      final WebRequest request) {
    return handleExceptionInternal(ex, message(HttpStatus.BAD_REQUEST, ex), new HttpHeaders(),
        HttpStatus.BAD_REQUEST, request);
  }


  /**
   * We have a message method here that simply uses the HTTP status and the exception to create this
   * API error data transfer object. This is going to represent the error message that we're gonna
   * return back to the client.
   */
  private final ApiError message(final HttpStatus httpStatus, final Exception ex) {
    final String message =
        ex.getMessage() == null ? ex.getClass().getSimpleName() : ex.getMessage();
    final String devMessage = ExceptionUtils.getRootCauseMessage(ex);

    return new ApiError(httpStatus.value(), message, devMessage);
  }


}
