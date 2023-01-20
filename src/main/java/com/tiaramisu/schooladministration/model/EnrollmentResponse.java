package com.tiaramisu.schooladministration.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EnrollmentResponse {
    private String responseCode;
    private String responseMessage;
}
