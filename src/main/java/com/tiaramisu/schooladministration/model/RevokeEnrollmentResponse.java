package com.tiaramisu.schooladministration.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RevokeEnrollmentResponse {
    private String responseCode;
    private String responseMessage;
}
