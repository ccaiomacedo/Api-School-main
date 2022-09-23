package br.com.alura.school.utils;

import br.com.alura.school.exceptions.UserRoleValidationException;
import br.com.alura.school.user.User;

public class ValidateUserRole {


    public static void validateUser(User user) {
        if (user.getRole().name() != "INSTRUCTOR") {
            throw new UserRoleValidationException("Only instructor allowed to register a section!");
        }
    }
}
