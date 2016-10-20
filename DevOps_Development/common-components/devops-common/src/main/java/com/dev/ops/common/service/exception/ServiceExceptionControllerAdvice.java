package com.dev.ops.common.service.exception;

import java.net.ConnectException;
import java.util.List;

import javax.persistence.PersistenceException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.dev.ops.exception.types.Severity;
import com.dev.ops.exceptions.impl.DefaultWrappedException;

@ControllerAdvice
@EnableWebMvc
public class ServiceExceptionControllerAdvice extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		final BindingResult result = ex.getBindingResult();
		final List<FieldError> fieldErrors = result.getFieldErrors();
		final StringBuilder validationErrors = new StringBuilder();
		for(final FieldError fieldError : fieldErrors) {
			validationErrors.append("Field error in object '" + fieldError.getObjectName() + "' on field '" + fieldError.getField() + "': rejected value [" + fieldError.getRejectedValue() + "] with cause: [" + fieldError.getDefaultMessage() + " ]");
		}
		final Exception e = ExceptionUtil.createException(validationErrors.toString(), Severity.FATAL, ex, null, true, null);
		return this.handleExceptionInternal(e, validationErrors.toString(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler({PersistenceException.class, CannotCreateTransactionException.class, TransactionSystemException.class})
	protected ResponseEntity<Object> handlePersistenceException(final Exception ex, final WebRequest request) {
		final Exception wrappedException = ExceptionUtil.createException(ExceptionUtil.ExceptionCodes.Database.PERSISTENCE_EXCEPTION, Severity.FATAL, ex, null, true, null);
		return this.handleExceptionInternal(wrappedException, wrappedException.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

	@ExceptionHandler({ResourceAccessException.class, ConnectException.class})
	protected ResponseEntity<Object> handleServiceConnectionException(final Exception ex, final WebRequest request) {
		final Exception wrappedException = ExceptionUtil.createException(ExceptionUtil.ExceptionCodes.Connection.CONNECTION_EXCEPTION, Severity.FATAL, ex, null, true, null);
		return this.handleExceptionInternal(wrappedException, wrappedException.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

	@ExceptionHandler(DefaultWrappedException.class)
	protected ResponseEntity<Object> handleDefaultWrappedException(final Exception ex, final WebRequest request) {
		final Exception wrappedException = ExceptionUtil.createException(ex.getMessage(), Severity.FATAL, ex, null, true, null);
		return this.handleExceptionInternal(wrappedException, wrappedException.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

	@ExceptionHandler(HttpServerErrorException.class)
	protected ResponseEntity<Object> handleDefaultWrappedException(final HttpServerErrorException ex, final WebRequest request) {
		final Exception wrappedException = ExceptionUtil.createException(ex.getMessage(), Severity.FATAL, ex, null, true, null);
		return this.handleExceptionInternal(wrappedException, wrappedException.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

	/**
	 * Handle all other service exceptions.
	 * @param ex the exception
	 * @param request the request
	 * @return the response entity
	 */
	@ExceptionHandler(Exception.class)
	protected ResponseEntity<Object> handleServiceException(final Exception ex, final WebRequest request) {
		final Exception wrappedException = ExceptionUtil.createException(ex.getMessage(), Severity.FATAL, ex, null, true, null);
		return this.handleExceptionInternal(wrappedException, wrappedException.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}
}