package com.tiaramisu.schooladministration.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddStudentResponse {
    private String email;
    private String responseCode;
    private String responseMessage;
}
