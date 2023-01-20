package com.tiaramisu.schooladministration.utility;

import com.tiaramisu.schooladministration.model.AddUserRequest;
import liquibase.repackaged.org.apache.commons.lang3.StringUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Generic {
    public static boolean checkEmptyUserRequest(AddUserRequest addUserRequest) {
        final boolean isEmailEmpty = StringUtils.isEmpty(addUserRequest.getEmail());
        final boolean isNameEmpty = StringUtils.isEmpty(addUserRequest.getName());
        return isEmailEmpty || isNameEmpty;
    }
}
