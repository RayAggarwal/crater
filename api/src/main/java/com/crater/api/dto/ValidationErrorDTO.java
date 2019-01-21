package com.crater.api.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ValidationErrorDTO {
    private Date timeStamp;
    private List<FieldErrorDTO> fieldErrors = new ArrayList<>();

    public ValidationErrorDTO() {
        timeStamp = new Date();
    }

    public void addFieldError(String field, String message) {
        fieldErrors.add(new FieldErrorDTO(field, message));
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public List<FieldErrorDTO> getFieldErrors() {
        return fieldErrors;
    }

    class FieldErrorDTO {
        private String field;
        private String message;

        private FieldErrorDTO(String field, String message) {
            this.field = field;
            this.message = message;
        }

        public String getField() {
            return field;
        }

        public String getMessage() {
            return message;
        }
    }
}
