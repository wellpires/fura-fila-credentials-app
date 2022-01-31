package br.com.furafila.credentialsapp.controller;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.furafila.credentialsapp.exception.CredentialNotAuthorizedException;
import br.com.furafila.credentialsapp.exception.CredentialsNotFoundException;
import br.com.furafila.credentialsapp.response.ErrorResponse;

@RestControllerAdvice
public class LoginControllerAdvice {

	private static final Logger logger = LoggerFactory.getLogger(LoginControllerAdvice.class);

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(Exception ex) {

		String errorMessage = "Internal Server Error!";
		logger.error(errorMessage, ex);

		ErrorResponse errorResponse = new ErrorResponse(errorMessage);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	}

	@ExceptionHandler(CredentialsNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleCredentialNotFound(CredentialsNotFoundException cnfEx) {
		logger.error(cnfEx.getMessage(), cnfEx);
		return ResponseEntity.notFound().build();
	}

	@ExceptionHandler(CredentialNotAuthorizedException.class)
	public ResponseEntity<ErrorResponse> handleCredentialNotAuthorized(CredentialNotAuthorizedException cnex) {
		logger.error(cnex.getMessage(), cnex);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse(cnex.getMessage()));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException maEx) {

		String rejectedValue = maEx.getBindingResult().getFieldErrors().stream().filter(Objects::nonNull).findFirst()
				.map(item -> String.valueOf(item.getRejectedValue())).orElseGet(() -> "");
		String defaultMessage = maEx.getBindingResult().getFieldError().getDefaultMessage();
		logger.error("{} - Value: {}", defaultMessage, rejectedValue);
		return ResponseEntity.badRequest().body(new ErrorResponse(defaultMessage));
	}

}
