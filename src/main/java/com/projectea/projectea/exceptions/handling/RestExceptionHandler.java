package com.projectea.projectea.exceptions.handling;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;


@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BaseRuntimeException.class)
    public ResponseEntity<ExceptionResponse> handleBaseRuntimeException(BaseRuntimeException ex, HttpServletRequest request) {
        return logAndReturnError(ex, ex.getStatus(), request);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ExceptionResponse> handleExpiredJwtException(ExpiredJwtException exception, HttpServletRequest request) {
        return logAndReturnError(exception, HttpStatus.UNAUTHORIZED, request);
    }

    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public final ResponseEntity<ExceptionResponse> handleAccessDeniedException(org.springframework.security.access.AccessDeniedException ex, HttpServletRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse("Access Denied: Insufficient permissions", HttpStatus.FORBIDDEN);
        log.error("[ERROR] {} {} - Access Denied: {}", request.getMethod(), request.getServletPath(), ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AuthenticationException.class)
    public final ResponseEntity<ExceptionResponse> handleAuthenticationException(AuthenticationException ex, HttpServletRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse("Authentication failed: Invalid or expired token", HttpStatus.UNAUTHORIZED);
        log.error("[ERROR] {} {} - Authentication failed: {}", request.getMethod(), request.getServletPath(), ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handles validation errors from @Valid annotations on controller methods.
     * Extracts field-specific error messages and returns them in a structured format.
     * 
     * @param ex The validation exception containing binding errors
     * @param headers HTTP headers
     * @param status HTTP status code
     * @param request The web request
     * @return ResponseEntity with field-specific error messages
     */
    @Override
    protected @NotNull ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            @NotNull HttpHeaders headers,
            @NotNull HttpStatusCode status,
            @NotNull WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String key = "error";
            if (error instanceof FieldError) {
                key = ((FieldError) error).getField();
            }
            final String errorMessage = error.getDefaultMessage();
            errors.put(key, errorMessage);
        });
        final HttpServletRequest httpServletRequest = ((ServletWebRequest) request).getRequest();
        logger.error(String.format("[ERROR] %s: %s with message: %s", httpServletRequest.getMethod(), httpServletRequest.getServletPath(), errors));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Logs error details and returns a standardized error response.
     * 
     * @param throwable The exception that occurred
     * @param status The HTTP status code to return
     * @param request The HTTP request that caused the error
     * @return ResponseEntity with error details
     */
    private ResponseEntity<ExceptionResponse> logAndReturnError(Throwable throwable, HttpStatus status, HttpServletRequest request) {
        log.error("[Error] {} {} - with message: {}", request.getMethod(), request.getServletPath(), throwable.getMessage());
        log.debug("Stack trace: ", throwable);
        return new ResponseEntity<>(new ExceptionResponse(throwable.getMessage(), status), status);
    }
}
