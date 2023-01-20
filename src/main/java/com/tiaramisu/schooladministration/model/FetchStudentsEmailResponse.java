package com.tiaramisu.schooladministration.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FetchStudentsEmailResponse {
    private String[] students;
}
