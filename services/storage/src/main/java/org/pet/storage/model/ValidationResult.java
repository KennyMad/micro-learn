package org.pet.storage.model;

import com.pet.common.dto.response.ErrorResponse;
import lombok.Data;

@Data
public class ValidationResult {
    boolean valid;
    ErrorResponse error;
}
