package com.tiaramisu.schooladministration.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class TeacherStudentsMapping {
    private String email;
    private List<String> students;

    public TeacherStudentsMapping(String email, String students) {
        this.email = email;
        final String[] arrayOfEmails = students != null ? students.split(",") : new String[]{};
        this.students = Arrays.asList(arrayOfEmails);
    }
}
