package com.tiaramisu.schooladministration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiaramisu.schooladministration.model.EnrollmentRequest;
import com.tiaramisu.schooladministration.model.EnrollmentResponse;
import com.tiaramisu.schooladministration.service.impl.EnrollmentServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static com.tiaramisu.schooladministration.utility.Constant.ResponseCode.ENROLLMENT_INVALID_REQUEST_CODE;
import static com.tiaramisu.schooladministration.utility.Constant.ResponseCode.ENROLLMENT_SUCCESS_CODE;
import static com.tiaramisu.schooladministration.utility.Constant.ResponseMessage.ENROLLMENT_INVALID_REQUEST_MESSAGE;
import static com.tiaramisu.schooladministration.utility.Constant.ResponseMessage.ENROLLMENT_SUCCESS_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EnrollmentController.class)
@ContextConfiguration(classes = {EnrollmentController.class})
class EnrollmentControllerTest {
    final String ENDPOINT_URI = "/register";
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EnrollmentServiceImpl enrollmentService;

    @Test
    void register_shouldReturnHttpNoContent_whenEndpointIsHitGivenAppropriateRequest() throws Exception {
        final String TEACHER_EMAIL = "teacher@email.com";
        final EnrollmentRequest request = EnrollmentRequest.builder()
                .teacher(TEACHER_EMAIL)
                .build();
        final String requestInJson = new ObjectMapper().writeValueAsString(request);
        final EnrollmentResponse response = EnrollmentResponse.builder()
                .responseCode(ENROLLMENT_SUCCESS_CODE)
                .responseMessage(ENROLLMENT_SUCCESS_MESSAGE)
                .build();
        final String responseInJson = new ObjectMapper().writeValueAsString(response);
        when(enrollmentService.enrollStudent(request)).thenReturn(response);

        MvcResult result = mockMvc.perform(post(ENDPOINT_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestInJson))
                .andExpect(status().isNoContent())
                .andReturn();

        verify(enrollmentService).enrollStudent(request);
        assertEquals(responseInJson, result.getResponse().getContentAsString());
    }

    @Test
    void register_shouldReturnHttpBadRequest_whenTeacherEmailIsEmpty() throws Exception {
        final EnrollmentRequest request = EnrollmentRequest.builder().build();
        final EnrollmentResponse response = EnrollmentResponse.builder()
                .responseCode(ENROLLMENT_INVALID_REQUEST_CODE)
                .responseMessage(ENROLLMENT_INVALID_REQUEST_MESSAGE)
                .build();
        final String requestInJson = new ObjectMapper().writeValueAsString(request);
        final String responseInJson = new ObjectMapper().writeValueAsString(response);
        when(enrollmentService.enrollStudent(request)).thenReturn(response);

        MvcResult result = mockMvc.perform(post(ENDPOINT_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestInJson))
                .andExpect(status().isBadRequest())
                .andReturn();

        verify(enrollmentService).enrollStudent(request);
        assertEquals(responseInJson, result.getResponse().getContentAsString());
    }
}