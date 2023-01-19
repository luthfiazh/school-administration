package com.tiaramisu.schooladministration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiaramisu.schooladministration.model.EnrollmentRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EnrollmentController.class)
@ContextConfiguration(classes = {EnrollmentController.class})
class EnrollmentControllerTest {
    final String ENDPOINT_URI = "/register";
    @Autowired
    private MockMvc mockMvc;

    @Test
    void register_shouldReturnHttpOk_whenEndpointIsHit() throws Exception {
        EnrollmentRequest request = EnrollmentRequest.builder().build();
        final String requestInJson = new ObjectMapper().writeValueAsString(request);

        mockMvc.perform(post(ENDPOINT_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestInJson))
                .andExpect(status().isOk())
                .andReturn();
    }
}