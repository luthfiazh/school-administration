package com.tiaramisu.schooladministration.utility;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Constant {
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ResponseMessage {
        public static final String ADD_STUDENT_SUCCESS_MESSAGE = "Successfully registered a student";
    }
}
