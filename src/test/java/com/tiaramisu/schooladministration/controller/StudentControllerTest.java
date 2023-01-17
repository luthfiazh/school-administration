package com.tiaramisu.schooladministration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiaramisu.schooladministration.model.AddUserRequest;
import com.tiaramisu.schooladministration.model.AddUserResponse;
import com.tiaramisu.schooladministration.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static com.tiaramisu.schooladministration.utility.Constant.ResponseCode.ADD_USER_GENERIC_ERROR_CODE;
import static com.tiaramisu.schooladministration.utility.Constant.ResponseCode.ADD_USER_INVALID_REQUEST_CODE;
import static com.tiaramisu.schooladministration.utility.Constant.ResponseCode.ADD_USER_SUCCESS_CODE;
import static com.tiaramisu.schooladministration.utility.Constant.ResponseMessage.ADD_STUDENT_SUCCESS_MESSAGE;
import static com.tiaramisu.schooladministration.utility.Constant.ResponseMessage.ADD_USER_GENERIC_ERROR_MESSAGE;
import static com.tiaramisu.schooladministration.utility.Constant.ResponseMessage.ADD_USER_INVALID_REQUEST_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
@ContextConfiguration(classes = {StudentController.class})
class StudentControllerTest {
    final String DUMMY_STUDENT_EMAIL = "student@school.com";
    final String DUMMY_STUDENT_NAME = "Johnny Doe";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    @Qualifier(value = "StudentServiceImpl")
    private UserService studentService;

    @Test
    void add_shouldReturnHttpStatusCreatedAndExpectedResponseBody_whenGivenAppropriateRequestBody() throws Exception {
        final String ENDPOINT_URI = "/api/students";
        final AddUserRequest request = AddUserRequest.builder()
                .email(DUMMY_STUDENT_EMAIL)
                .name(DUMMY_STUDENT_NAME)
                .build();
        final AddUserResponse response = AddUserResponse.builder()
                .email(DUMMY_STUDENT_EMAIL)
                .responseCode(ADD_USER_SUCCESS_CODE)
                .responseMessage(ADD_STUDENT_SUCCESS_MESSAGE)
                .build();
        when(studentService.addUser(request)).thenReturn(response);
        final String requestInJson = new ObjectMapper().writeValueAsString(request);
        final String expectedResponseInJson = new ObjectMapper().writeValueAsString(response);

        MvcResult result = mockMvc.perform(post(ENDPOINT_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestInJson))
                .andExpect(status().isCreated())
                .andReturn();

        assertEquals(expectedResponseInJson, result.getResponse().getContentAsString());
    }

    @Test
    void add_shouldReturnHttpStatusNotAcceptableAndExpectedResponseBody_whenGivenRequestBodyWithNoEmail() throws Exception {
        final String ENDPOINT_URI = "/api/students";
        final AddUserRequest request = AddUserRequest.builder()
                .name(DUMMY_STUDENT_NAME)
                .build();
        final AddUserResponse response = AddUserResponse.builder()
                .responseCode(ADD_USER_INVALID_REQUEST_CODE)
                .responseMessage(ADD_USER_INVALID_REQUEST_MESSAGE)
                .build();
        when(studentService.addUser(request)).thenReturn(response);
        final String requestInJson = new ObjectMapper().writeValueAsString(request);
        final String expectedResponseInJson = new ObjectMapper().writeValueAsString(response);

        MvcResult result = mockMvc.perform(post(ENDPOINT_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestInJson))
                .andExpect(status().isNotAcceptable())
                .andReturn();

        assertEquals(expectedResponseInJson, result.getResponse().getContentAsString());
    }

    @Test
    void add_shouldReturnHttpStatusInternalServerErrorAndExpectedResponseBody_whenServiceMethodReturnsWithResponseCodeNot201Or400() throws Exception {
        final String ENDPOINT_URI = "/api/students";
        final AddUserRequest request = AddUserRequest.builder()
                .name(DUMMY_STUDENT_NAME)
                .build();
        final AddUserResponse response = AddUserResponse.builder()
                .responseCode(ADD_USER_GENERIC_ERROR_CODE)
                .responseMessage(ADD_USER_GENERIC_ERROR_MESSAGE)
                .build();
        when(studentService.addUser(request)).thenReturn(response);
        final String requestInJson = new ObjectMapper().writeValueAsString(request);
        final String expectedResponseInJson = new ObjectMapper().writeValueAsString(response);

        MvcResult result = mockMvc.perform(post(ENDPOINT_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestInJson))
                .andExpect(status().isInternalServerError())
                .andReturn();

        assertEquals(expectedResponseInJson, result.getResponse().getContentAsString());
    }
}