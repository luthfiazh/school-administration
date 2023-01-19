package com.tiaramisu.schooladministration.service;

import com.tiaramisu.schooladministration.entity.Enrollment;
import com.tiaramisu.schooladministration.entity.Student;
import com.tiaramisu.schooladministration.entity.Teacher;
import com.tiaramisu.schooladministration.model.EnrollmentRequest;
import com.tiaramisu.schooladministration.model.EnrollmentResponse;
import com.tiaramisu.schooladministration.repository.EnrollmentRepository;
import com.tiaramisu.schooladministration.repository.StudentRepository;
import com.tiaramisu.schooladministration.repository.TeacherRepository;
import com.tiaramisu.schooladministration.service.impl.EnrollmentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static com.tiaramisu.schooladministration.utility.Constant.ResponseCode.ENROLLMENT_SUCCESS_CODE;
import static com.tiaramisu.schooladministration.utility.Constant.ResponseMessage.ENROLLMENT_SUCCESS_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EnrollmentServiceTest {
    @Captor
    ArgumentCaptor<List<Enrollment>> enrollmentsArgumentCaptor;
    @Mock
    private TeacherRepository teacherRepository;
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private EnrollmentRepository enrollmentRepository;
    @InjectMocks
    private EnrollmentServiceImpl enrollmentService;

    @Test
    void enrollStudent_shouldReturnSuccessEnrollment_whenGivenAppropriateRequest() {
        final String JANE_STUDENT_EMAIL = "jane@student.com";
        final String JOHN_STUDENT_EMAIL = "john@student.com";
        List<String> studentEmails = new ArrayList<>();
        studentEmails.add(JANE_STUDENT_EMAIL);
        studentEmails.add(JOHN_STUDENT_EMAIL);
        final String TEACHER_EMAIL = "teacher@email.com";
        EnrollmentRequest request = EnrollmentRequest.builder()
                .teacher(TEACHER_EMAIL)
                .students(studentEmails)
                .build();
        final String TEACHER_ID = "teacherId001";
        final Teacher teacher = Teacher.builder().teacherId(TEACHER_ID).build();
        final String JANE_STUDENT_ID = "studentId001";
        Student jane = Student.builder().studentId(JANE_STUDENT_ID).build();
        final String JOHN_STUDENT_ID = "studentId002";
        Student john = Student.builder().studentId(JOHN_STUDENT_ID).build();
        List<Student> students = new ArrayList<>();
        students.add(jane);
        students.add(john);
        when(teacherRepository.findByEmail(TEACHER_EMAIL)).thenReturn(teacher);
        when(studentRepository.findAllByEmailIn(studentEmails)).thenReturn(students);

        EnrollmentResponse response = enrollmentService.enrollStudent(request);

        verify(teacherRepository, atMostOnce()).findByEmail(TEACHER_EMAIL);
        verify(studentRepository, atMostOnce()).findAllByEmailIn(studentEmails);
        verify(enrollmentRepository, atMostOnce()).saveAll(enrollmentsArgumentCaptor.capture());
        List<Enrollment> capturedEnrollments = enrollmentsArgumentCaptor.getValue();
        assertEquals(TEACHER_ID, capturedEnrollments.get(0).getTeacherId());
        assertEquals(TEACHER_ID, capturedEnrollments.get(1).getTeacherId());
        assertEquals(ENROLLMENT_SUCCESS_CODE, response.getResponseCode());
        assertEquals(ENROLLMENT_SUCCESS_MESSAGE, response.getResponseMessage());
    }

    @Test
    void enrollStudent_shouldOnlyEnroll2OutOf3Students_when1StudentIsAlreadyEnrolledWithAGivenTeacher() {
        final String JANE_STUDENT_EMAIL = "jane@student.com";
        final String JOHN_STUDENT_EMAIL = "john@student.com";
        final String JIMMY_STUDENT_EMAIL = "jimmy@student.com";
        List<String> studentEmails = new ArrayList<>();
        studentEmails.add(JANE_STUDENT_EMAIL);
        studentEmails.add(JOHN_STUDENT_EMAIL);
        studentEmails.add(JIMMY_STUDENT_EMAIL);
        final String TEACHER_EMAIL = "teacher@email.com";
        EnrollmentRequest request = EnrollmentRequest.builder()
                .teacher(TEACHER_EMAIL)
                .students(studentEmails)
                .build();
        final String TEACHER_ID = "teacherId001";
        final Teacher teacher = Teacher.builder().teacherId(TEACHER_ID).build();
        final String JANE_STUDENT_ID = "studentId001";
        Student jane = Student.builder().studentId(JANE_STUDENT_ID).build();
        final String JOHN_STUDENT_ID = "studentId002";
        Student john = Student.builder().studentId(JOHN_STUDENT_ID).build();
        final String JIMMY_STUDENT_ID = "studentId003";
        Student jimmy = Student.builder().studentId(JIMMY_STUDENT_ID).build();
        List<Student> students = new ArrayList<>();
        students.add(jane);
        students.add(john);
        students.add(jimmy);
        List<String> studentIdsByInput = new ArrayList<>();
        studentIdsByInput.add(JANE_STUDENT_ID);
        studentIdsByInput.add(JOHN_STUDENT_ID);
        studentIdsByInput.add(JIMMY_STUDENT_ID);
        List<Enrollment> existingEnrollments = new ArrayList<>();
        final String ENROLLMENT_ID = "enrollmentId01";
        existingEnrollments.add(Enrollment.builder()
                .enrollmentId(ENROLLMENT_ID).teacherId(TEACHER_ID).studentId(JANE_STUDENT_ID).build());
        when(teacherRepository.findByEmail(TEACHER_EMAIL)).thenReturn(teacher);
        when(studentRepository.findAllByEmailIn(studentEmails)).thenReturn(students);
        when(enrollmentRepository.findAllByTeacherIdAndStudentIdIn(TEACHER_ID, studentIdsByInput)).thenReturn(existingEnrollments);

        enrollmentService.enrollStudent(request);

        verify(enrollmentRepository, atMostOnce()).saveAll(enrollmentsArgumentCaptor.capture());
        List<Enrollment> savedEnrollments = enrollmentsArgumentCaptor.getValue();
        assertEquals(2, savedEnrollments.size());
    }
}