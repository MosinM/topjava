package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

public class UsersUtils {

    public static final User ADMIN = new User(1, "admin", "admin@admin.ru", "admin", Role.ROLE_ADMIN);
    public static final User TEST_USER = new User(1, "test_user", "test@test.ru", "test", Role.ROLE_USER);

}
