package com.tiaramisu.schooladministration.service.impl;

import com.tiaramisu.schooladministration.entity.Enrollment;
import com.tiaramisu.schooladministration.entity.Student;
import com.tiaramisu.schooladministration.entity.Teacher;
import com.tiaramisu.schooladministration.model.EnrollmentRequest;
import com.tiaramisu.schooladministration.model.EnrollmentResponse;
import com.tiaramisu.schooladministration.repository.EnrollmentRepository;
import com.tiaramisu.schooladministration.repository.StudentRepository;
import com.tiaramisu.schooladministration.repository.TeacherRepository;
import com.tiaramisu.schooladministration.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.tiaramisu.schooladministration.utility.Constant.ResponseCode.ENROLLMENT_SUCCESS_CODE;
import static com.tiaramisu.schooladministration.utility.Constant.ResponseMessage.ENROLLMENT_SUCCESS_MESSAGE;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final EnrollmentRepository enrollmentRepository;

    @Override
    public EnrollmentResponse enrollStudent(EnrollmentRequest enrollmentRequest) {
        Teacher fetchedTeacher = teacherRepository.findByEmail(enrollmentRequest.getTeacher());
        List<Student> fetchedStudents = studentRepository.findAllByEmail(enrollmentRequest.getStudents());
        List<Enrollment> enrollments = new ArrayList<>();
        fetchedStudents.forEach(student -> {
            final Enrollment enrollment = Enrollment.builder()
                    .teacherId(fetchedTeacher.getTeacherId())
                    .studentId(student.getStudentId())
                    .createdDate(new Date())
                    .modifiedDate(new Date())
                    .build();
            enrollments.add(enrollment);
        });
        enrollmentRepository.saveAll(enrollments);
        return EnrollmentResponse.builder()
                .responseCode(ENROLLMENT_SUCCESS_CODE)
                .responseMessage(ENROLLMENT_SUCCESS_MESSAGE)
                .build();
    }
}
