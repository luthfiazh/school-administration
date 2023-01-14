package com.tiaramisu.schooladministration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiaramisu.schooladministration.model.AddStudentRequest;
import com.tiaramisu.schooladministration.model.AddStudentResponse;
import com.tiaramisu.schooladministration.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static com.tiaramisu.schooladministration.utility.Constant.ResponseCode.ADD_STUDENT_SUCCESS_CODE;
import static com.tiaramisu.schooladministration.utility.Constant.ResponseMessage.ADD_STUDENT_SUCCESS_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
@ContextConfiguration(classes = {StudentController.class})
class StudentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    final String DUMMY_STUDENT_EMAIL = "student@school.com";
    final String DUMMY_STUDENT_NAME = "Johnny Doe";

    @Test
    void add_shouldReturnHttpStatusOKAndExpectedResponse_whenGivenRequest() throws Exception {
        final String ENDPOINT_URI = "/api/students";
        final AddStudentRequest request = AddStudentRequest.builder()
                .email(DUMMY_STUDENT_EMAIL)
                .name(DUMMY_STUDENT_NAME)
                .build();
        final AddStudentResponse response = AddStudentResponse.builder()
                .email(DUMMY_STUDENT_EMAIL)
                .responseCode(ADD_STUDENT_SUCCESS_CODE)
                .responseMessage(ADD_STUDENT_SUCCESS_MESSAGE)
                .build();
        when(studentService.addStudent(request)).thenReturn(response);
        final String requestInJson = new ObjectMapper().writeValueAsString(request);
        final String expectedResponseInJson = new ObjectMapper().writeValueAsString(response);

        MvcResult result = mockMvc.perform(post(ENDPOINT_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestInJson))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(expectedResponseInJson, result.getResponse().getContentAsString());
    }
}