package com.tiaramisu.schooladministration.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FetchTeachersResponse {
    private List<TeacherStudentsMapping> teachers;
}
