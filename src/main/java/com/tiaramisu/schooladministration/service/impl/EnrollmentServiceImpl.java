package com.tiaramisu.schooladministration.service.impl;

import com.tiaramisu.schooladministration.entity.Enrollment;
import com.tiaramisu.schooladministration.entity.Revocation;
import com.tiaramisu.schooladministration.entity.Student;
import com.tiaramisu.schooladministration.entity.Teacher;
import com.tiaramisu.schooladministration.model.EnrollmentRequest;
import com.tiaramisu.schooladministration.model.EnrollmentResponse;
import com.tiaramisu.schooladministration.model.RevokeEnrollmentRequest;
import com.tiaramisu.schooladministration.model.RevokeEnrollmentResponse;
import com.tiaramisu.schooladministration.repository.EnrollmentRepository;
import com.tiaramisu.schooladministration.repository.RevocationRepository;
import com.tiaramisu.schooladministration.repository.StudentRepository;
import com.tiaramisu.schooladministration.repository.TeacherRepository;
import com.tiaramisu.schooladministration.service.EnrollmentService;
import liquibase.repackaged.org.apache.commons.lang3.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.tiaramisu.schooladministration.utility.Constant.ResponseCode.ENROLLMENT_INVALID_REQUEST_CODE;
import static com.tiaramisu.schooladministration.utility.Constant.ResponseCode.ENROLLMENT_SUCCESS_CODE;
import static com.tiaramisu.schooladministration.utility.Constant.ResponseCode.REVOKE_ENROLLMENT_INVALID_REQUEST_CODE;
import static com.tiaramisu.schooladministration.utility.Constant.ResponseCode.REVOKE_ENROLLMENT_NOTHING_TO_REVOKE_CODE;
import static com.tiaramisu.schooladministration.utility.Constant.ResponseCode.REVOKE_ENROLLMENT_SUCCESS_CODE;
import static com.tiaramisu.schooladministration.utility.Constant.ResponseCode.REVOKE_ENROLLMENT_USER_NOT_FOUND_CODE;
import static com.tiaramisu.schooladministration.utility.Constant.ResponseMessage.ENROLLMENT_INVALID_REQUEST_MESSAGE;
import static com.tiaramisu.schooladministration.utility.Constant.ResponseMessage.ENROLLMENT_SUCCESS_MESSAGE;
import static com.tiaramisu.schooladministration.utility.Constant.ResponseMessage.REVOKE_ENROLLMENT_INVALID_REQUEST_MESSAGE;
import static com.tiaramisu.schooladministration.utility.Constant.ResponseMessage.REVOKE_ENROLLMENT_NOTHING_TO_REVOKE_MESSAGE;
import static com.tiaramisu.schooladministration.utility.Constant.ResponseMessage.REVOKE_ENROLLMENT_SUCCESS_MESSAGE;
import static com.tiaramisu.schooladministration.utility.Constant.ResponseMessage.REVOKE_ENROLLMENT_USER_NOT_FOUND_MESSAGE;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final RevocationRepository revocationRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
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

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public RevokeEnrollmentResponse revokeEnrollment(RevokeEnrollmentRequest revokeEnrollmentRequest) {
        if (checkEmptyEmails(revokeEnrollmentRequest)) {
            return RevokeEnrollmentResponse.builder()
                    .responseCode(REVOKE_ENROLLMENT_INVALID_REQUEST_CODE)
                    .responseMessage(REVOKE_ENROLLMENT_INVALID_REQUEST_MESSAGE)
                    .build();
        }
        final Teacher fetchedTeacher = teacherRepository.findByEmail(revokeEnrollmentRequest.getTeacher());
        final Student fetchedStudent = studentRepository.findByEmail(revokeEnrollmentRequest.getStudent());
        if (checkNonexistentUser(fetchedTeacher, fetchedStudent)) {
            return RevokeEnrollmentResponse.builder()
                    .responseCode(REVOKE_ENROLLMENT_USER_NOT_FOUND_CODE)
                    .responseMessage(REVOKE_ENROLLMENT_USER_NOT_FOUND_MESSAGE)
                    .build();
        }
        final Enrollment fetchedExistingEnrollment = enrollmentRepository.findByTeacherIdAndStudentId(
                fetchedTeacher.getTeacherId(),
                fetchedStudent.getStudentId());
        long numberOfRowsDeleted = enrollmentRepository.deleteByTeacherIdAndStudentId(
                fetchedTeacher.getTeacherId(),
                fetchedStudent.getStudentId());
        if (numberOfRowsDeleted > 0) {
            recordEnrollmentRevocation(revokeEnrollmentRequest, fetchedExistingEnrollment);
            return RevokeEnrollmentResponse.builder()
                    .responseCode(REVOKE_ENROLLMENT_SUCCESS_CODE)
                    .responseMessage(REVOKE_ENROLLMENT_SUCCESS_MESSAGE)
                    .build();
        }
        return RevokeEnrollmentResponse.builder()
                .responseCode(REVOKE_ENROLLMENT_NOTHING_TO_REVOKE_CODE)
                .responseMessage(REVOKE_ENROLLMENT_NOTHING_TO_REVOKE_MESSAGE)
                .build();
    }

    private boolean checkNonexistentUser(Teacher fetchedTeacher, Student fetchedStudent) {
        final boolean teacherNotExists = fetchedTeacher == null;
        final boolean studentNotExists = fetchedStudent == null;
        return teacherNotExists || studentNotExists;
    }

    private boolean checkEmptyEmails(RevokeEnrollmentRequest revokeEnrollmentRequest) {
        final boolean isTeacherEmailEmpty = StringUtils.isEmpty(revokeEnrollmentRequest.getTeacher());
        final boolean isStudentEmailEmpty = StringUtils.isEmpty(revokeEnrollmentRequest.getStudent());
        return isTeacherEmailEmpty || isStudentEmailEmpty;
    }

    private void recordEnrollmentRevocation(RevokeEnrollmentRequest revokeEnrollmentRequest, Enrollment fetchedExistingEnrollment) {
        Revocation revocationToBeRecorded = Revocation.builder()
                .enrollmentId(fetchedExistingEnrollment.getEnrollmentId())
                .reason(revokeEnrollmentRequest.getReason())
                .createdDate(new Date())
                .modifiedDate(new Date())
                .build();
        revocationRepository.save(revocationToBeRecorded);
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
