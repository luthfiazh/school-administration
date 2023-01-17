package com.tiaramisu.schooladministration.model.teacher;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddTeacherRequest {
    private String email;
    private String name;
}
