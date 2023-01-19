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
import liquibase.repackaged.org.apache.commons.lang3.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.tiaramisu.schooladministration.utility.Constant.ResponseCode.ENROLLMENT_INVALID_REQUEST_CODE;
import static com.tiaramisu.schooladministration.utility.Constant.ResponseCode.ENROLLMENT_SUCCESS_CODE;
import static com.tiaramisu.schooladministration.utility.Constant.ResponseMessage.ENROLLMENT_INVALID_REQUEST_MESSAGE;
import static com.tiaramisu.schooladministration.utility.Constant.ResponseMessage.ENROLLMENT_SUCCESS_MESSAGE;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final EnrollmentRepository enrollmentRepository;

    @Override
    public EnrollmentResponse enrollStudent(EnrollmentRequest enrollmentRequest) {
        if (checkEmptyEnrollmentRequest(enrollmentRequest)) {
            return EnrollmentResponse.builder()
                    .responseCode(ENROLLMENT_INVALID_REQUEST_CODE)
                    .responseMessage(ENROLLMENT_INVALID_REQUEST_MESSAGE)
                    .build();
        }
        Teacher fetchedTeacher = teacherRepository.findByEmail(enrollmentRequest.getTeacher());
        List<Student> fetchedStudents = studentRepository.findAllByEmailIn(enrollmentRequest.getStudents());
        List<String> studentIds = fetchedStudents.stream()
                .map(Student::getStudentId)
                .collect(Collectors.toList());
        List<Enrollment> existingEnrollments = enrollmentRepository.findAllByTeacherIdAndStudentIdIn(
                fetchedTeacher.getTeacherId(),
                studentIds);
        List<Enrollment> enrollmentsToBeSaved = getValidEnrollments(fetchedTeacher, fetchedStudents, existingEnrollments);
        enrollmentRepository.saveAll(enrollmentsToBeSaved);
        return EnrollmentResponse.builder()
                .responseCode(ENROLLMENT_SUCCESS_CODE)
                .responseMessage(ENROLLMENT_SUCCESS_MESSAGE)
                .build();
    }

    private List<Enrollment> getValidEnrollments(Teacher fetchedTeacher, List<Student> fetchedStudents, List<Enrollment> existingEnrollments) {
        List<Enrollment> enrollmentsToBeSaved = new ArrayList<>();
        fetchedStudents.forEach(student -> {
            final Enrollment enrollment = Enrollment.builder()
                    .teacherId(fetchedTeacher.getTeacherId())
                    .studentId(student.getStudentId())
                    .createdDate(new Date())
                    .modifiedDate(new Date())
                    .build();
            enrollmentsToBeSaved.add(enrollment);
        });
        enrollmentsToBeSaved.removeAll(existingEnrollments);
        return enrollmentsToBeSaved;
    }

    private boolean checkEmptyEnrollmentRequest(EnrollmentRequest enrollmentRequest) {
        return enrollmentRequest == null || StringUtils.isEmpty(enrollmentRequest.getTeacher());
    }
}
