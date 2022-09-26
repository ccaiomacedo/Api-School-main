package br.com.alura.school.utils;

import br.com.alura.school.course.Course;
import br.com.alura.school.enrollment.Enrollment;
import br.com.alura.school.enrollment.EnrollmentRepository;
import br.com.alura.school.tests.Factory;
import br.com.alura.school.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;


@SpringBootTest
public class EnrollmentValidatorTest {

    @Mock
    EnrollmentRepository enrollmentRepository;

    @InjectMocks
    EnrollmentValidator validator;

    @Test
    void should_return_true_if_user_is_already_enrolled() {
        User user = Factory.createUser();
        Course course = Factory.createCourse();
        Enrollment enrollment = new Enrollment(course, user);

        Mockito.when(enrollmentRepository.findEnrollmentByUserAndCourse(user, course)).thenReturn(Optional.of(new Enrollment(course, user)));

        boolean isEnrolled = validator.isAlreadyEnrolled(user, course);

        Assertions.assertTrue(isEnrolled);
    }

    @Test
    void should_return_false_if_user_is_not_enrolled() {
        User user = Factory.createUser();
        Course course = Factory.createCourse();

        Mockito.when(enrollmentRepository.findEnrollmentByUserAndCourse(user, course)).thenReturn(Optional.empty());

        boolean isEnrolled = validator.isAlreadyEnrolled(user, course);

        Assertions.assertFalse(isEnrolled);
    }
}
