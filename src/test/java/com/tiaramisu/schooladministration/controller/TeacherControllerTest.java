package com.tiaramisu.schooladministration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiaramisu.schooladministration.model.AddUserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TeacherController.class)
@ContextConfiguration(classes = {TeacherController.class})
class TeacherControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void add_shouldReturnHttpOK_whenURIIsHit() throws Exception {
        final String ENDPOINT_URI = "/api/teachers";
        final String DUMMY_EMAIL = "teacher@school.com";
        final String DUMMY_NAME = "Janey Done";
        final AddUserRequest request = AddUserRequest.builder()
                .email(DUMMY_EMAIL)
                .name(DUMMY_NAME)
                .build();
        final String requestInJson = new ObjectMapper().writeValueAsString(request);

        mockMvc.perform(post(ENDPOINT_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestInJson))
                .andExpect(status().isOk());
    }
}