package com.tiaramisu.schooladministration.model.teacher;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddTeacherResponse {
    private String email;
    private String responseCode;
    private String responseMessage;
}
