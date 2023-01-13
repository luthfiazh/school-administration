package com.tiaramisu.schooladministration.service.impl;

import com.tiaramisu.schooladministration.entity.Student;
import com.tiaramisu.schooladministration.model.AddStudentRequest;
import com.tiaramisu.schooladministration.model.AddStudentResponse;
import com.tiaramisu.schooladministration.repository.StudentRepository;
import com.tiaramisu.schooladministration.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.tiaramisu.schooladministration.utility.Constant.ResponseMessage.ADD_STUDENT_SUCCESS_MESSAGE;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    @Override
    public AddStudentResponse addStudent(AddStudentRequest addStudentRequest) {
        final Student studentToBeSaved = Student.builder()
                .email(addStudentRequest.getEmail())
                .name(addStudentRequest.getName())
                .build();
        final Student savedStudentData = studentRepository.save(studentToBeSaved);
        return AddStudentResponse.builder()
                .email(savedStudentData.getEmail())
                .responseMessage(ADD_STUDENT_SUCCESS_MESSAGE)
                .build();
    }
}
