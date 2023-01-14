package com.tiaramisu.schooladministration.utility;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Constant {
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ResponseCode {
        public static final String ADD_STUDENT_SUCCESS_CODE = "201";
        public static final String ADD_STUDENT_BAD_REQUEST_CODE = "400";
        public static final String ADD_STUDENT_DUPLICATE_ENTRY_CODE = "409";
        public static final String ADD_STUDENT_ERROR_CODE = "500";
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ResponseMessage {
        public static final String ADD_STUDENT_SUCCESS_MESSAGE = "Successfully registered a student";
        public static final String ADD_STUDENT_BAD_REQUEST_MESSAGE = "Email and name should not be empty";
        public static final String ADD_STUDENT_DUPLICATE_ENTRY_MESSAGE = "Student with provided email already exists";
        public static final String ADD_STUDENT_ERROR_MESSAGE = "Error occurred";
    }
}
