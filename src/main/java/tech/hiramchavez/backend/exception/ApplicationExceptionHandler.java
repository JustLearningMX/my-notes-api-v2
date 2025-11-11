package tech.hiramchavez.backend.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
@Order(4)
public class ApplicationExceptionHandler {

    // Validations errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApplicationExceptionResponse> methodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest req) {
        Map<String, String> errors = getErrorsMap(ex);

        ApplicationExceptionResponse errorResponse = ExceptionUtils.createResponse(HttpStatus.BAD_REQUEST, req, errors);
        return ResponseEntity.status(400).body(errorResponse);

    }

    // DB Integrity Violation
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApplicationExceptionResponse> dataIntegrityViolationException(DataIntegrityViolationException ex, HttpServletRequest req) {

        Map<String, String> errors = new HashMap<>(Map.of(ex.getClass().getSimpleName(), ex.getMessage()));
        ApplicationExceptionResponse errorResponse = ExceptionUtils.createResponse(HttpStatus.BAD_REQUEST, req, errors);
        return ResponseEntity.status(400).body(errorResponse);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApplicationExceptionResponse> handleException(Exception e, HttpServletRequest req) {
        Map<String, String> errors = new HashMap<>(Map.of(e.getClass().getSimpleName(), e.getMessage()));
        ApplicationExceptionResponse errorResponse = ExceptionUtils.createResponse(HttpStatus.INTERNAL_SERVER_ERROR, req, errors);
        return ResponseEntity.status(500).body(errorResponse);
    }

    private Map<String, String> getErrorsMap(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new LinkedHashMap<>();

        // iterate over the field errors to extract the field name and the error message
        for (FieldError fieldError : ex.getFieldErrors()) {
            String fieldName = fieldError.getField();
            String errorMessage = fieldError.getDefaultMessage();

            // verify if the field name already exists in the map
            if (errors.containsKey(fieldName)) {
                // if the field name already exists, concatenate the new error message
                errorMessage = errors.get(fieldName) + ". " + errorMessage;
            }

            // add the field name and the error message to the map
            errors.put(fieldName, errorMessage);
        }

        return errors;
    }
}

