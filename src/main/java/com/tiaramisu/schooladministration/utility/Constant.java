package com.tiaramisu.schooladministration.utility;

import com.tiaramisu.schooladministration.model.AddUserRequest;
import liquibase.repackaged.org.apache.commons.lang3.StringUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Constant {
    public static boolean checkEmptyRequest(AddUserRequest addUserRequest) {
        final boolean isEmailEmpty = StringUtils.isEmpty(addUserRequest.getEmail());
        final boolean isNameEmpty = StringUtils.isEmpty(addUserRequest.getName());
        return isEmailEmpty || isNameEmpty;
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ResponseCode {
        public static final String ADD_USER_SUCCESS_CODE = "201";
        public static final String ADD_USER_INVALID_REQUEST_CODE = "400";
        public static final String ADD_USER_GENERIC_ERROR_CODE = "500";
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ResponseMessage {
        public static final String ADD_STUDENT_SUCCESS_MESSAGE = "Successfully registered a student";
        public static final String ADD_TEACHER_SUCCESS_MESSAGE = "Successfully registered a teacher";
        public static final String ADD_USER_INVALID_REQUEST_MESSAGE = "Email and name should not be empty";
        public static final String ADD_USER_DUPLICATE_ENTRY_MESSAGE = "User with provided email already exists";
        public static final String ADD_USER_GENERIC_ERROR_MESSAGE = "Error occurred. Please contact developer";
    }
}
