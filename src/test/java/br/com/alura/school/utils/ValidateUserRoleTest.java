package br.com.alura.school.utils;

import br.com.alura.school.tests.Factory;
import br.com.alura.school.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ValidateUserRoleTest {


    @Test
    public void should_throw_User_Role_Validation_Exception_if_user_is_not_instructor() {

        User user = Factory.createUserStudent();

        try {
            ValidateUserRole.validateUserAsInstructor(user);
            Assertions.fail("Should thrown an exception");
        } catch (Exception e) {
            Assertions.assertEquals("Only instructor allowed to register a section!", e.getMessage());
        }

    }
}
