package com.tiaramisu.schooladministration.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RevokeEnrollmentRequest {
    private String teacher;
    private String student;
    private String reason;
}
