package com.tiaramisu.schooladministration.utility;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Constant {
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ResponseCode {
        public static final String ADD_USER_SUCCESS_CODE = "201";
        public static final String ADD_USER_INVALID_REQUEST_CODE = "400";
        public static final String ENROLLMENT_INVALID_REQUEST_CODE = "400";
        public static final String ENROLLMENT_USER_NOT_FOUND_CODE = "404";
        public static final String GENERIC_ERROR_CODE = "500";
        public static final String ENROLLMENT_SUCCESS_CODE = "204";
        public static final String REVOKE_ENROLLMENT_SUCCESS_CODE = "200";
        public static final String REVOKE_ENROLLMENT_NOTHING_TO_REVOKE_CODE = "200";
        public static final String REVOKE_ENROLLMENT_INVALID_REQUEST_CODE = "400";
        public static final String REVOKE_ENROLLMENT_USER_NOT_FOUND_CODE = "404";
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ResponseMessage {
        public static final String ADD_STUDENT_SUCCESS_MESSAGE = "Successfully registered a student";
        public static final String ADD_TEACHER_SUCCESS_MESSAGE = "Successfully registered a teacher";
        public static final String ADD_USER_INVALID_REQUEST_MESSAGE = "Email and name should not be empty";
        public static final String ADD_USER_DUPLICATE_ENTRY_MESSAGE = "User with provided email already exists";
        public static final String GENERIC_ERROR_MESSAGE = "Error occurred. Please contact developer";
        public static final String ENROLLMENT_SUCCESS_MESSAGE = "Successfully enrolled";
        public static final String ENROLLMENT_INVALID_REQUEST_MESSAGE = "Teacher email cannot be null or empty";
        public static final String ENROLLMENT_USER_NOT_FOUND_MESSAGE = "Teacher/Student not found";
        public static final String REVOKE_ENROLLMENT_SUCCESS_MESSAGE = "Successfully revoked enrollment";
        public static final String REVOKE_ENROLLMENT_NOTHING_TO_REVOKE_MESSAGE = "No enrollments to revoke with these emails";
        public static final String REVOKE_ENROLLMENT_INVALID_REQUEST_MESSAGE = "Emails should not be empty";
        public static final String REVOKE_ENROLLMENT_USER_NOT_FOUND_MESSAGE = "Teacher/Student not found";
    }
}
