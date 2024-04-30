package com.dorayd.sports.core.validations;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ProblemDetail;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConstraintViolation {
    private String fieldName;
    private String message;
    private String rejectedValue;
}
