package com.tiaramisu.schooladministration.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FetchStudentsEmailResponse {
    private List<String> students;
}
