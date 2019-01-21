package com.crater.api.endpoints;

import com.crater.api.dto.ValidationErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class RestErrorHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<ValidationErrorDTO> apiErrorResponseEntity(MethodArgumentNotValidException ex) {
        ValidationErrorDTO validationErrorDTO = new ValidationErrorDTO();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            validationErrorDTO.addFieldError(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(validationErrorDTO);
    }
}
