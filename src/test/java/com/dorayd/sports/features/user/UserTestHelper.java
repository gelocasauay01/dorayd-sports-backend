package com.dorayd.sports.features.user;

import com.dorayd.sports.features.user.models.User;

public class UserTestHelper {
    private UserTestHelper() {}
    
    public static boolean isUserEqual(User a, User b) {
        return a.getFirstName().equals(b.getFirstName())
            && a.getMiddleName().equals(b.getMiddleName())
            && a.getLastName().equals(b.getLastName())
            && a.getBirthDate().equals(b.getBirthDate())
            && a.getGender() == b.getGender();
    }
}
