package br.com.alura.school.enrollment;

import br.com.alura.school.course.Course;
import br.com.alura.school.course.CourseRepository;
import br.com.alura.school.tests.Factory;
import br.com.alura.school.user.User;
import br.com.alura.school.user.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
@Transactional
public class EnrollmentRepositoryTest {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;


    private Enrollment enrollment;
    private User user;
    private Course course;


    @BeforeEach
    void setUp() throws Exception {
        enrollment = Factory.createEnrollment();
        user = Factory.createUser();
        course = Factory.createCourse();
    }

    @Test
    void save_should_persist_with_auto_increment_when_id_is_null() {
        userRepository.save(user);
        courseRepository.save(course);

        enrollment = enrollmentRepository.save(enrollment);

        Assertions.assertNotNull(enrollment.getId());
    }

    @Test
    void find_Enrollment_By_User_And_Course_should_return_null_when_enrollment_does_not_exist_for_this_course_or_user() {
        Optional<Enrollment> findEnrollment = enrollmentRepository.findEnrollmentByUserAndCourse(user, course);
        Assertions.assertFalse(findEnrollment.isPresent());
    }

    @Test
    void find_Enrollment_By_User_And_Course_should_return_object_if_exist_enrollment_for_course_and_user() {
        User user = userRepository.save(this.user);
        Course course = courseRepository.save(this.course);
        enrollmentRepository.save(new Enrollment(course, user));
        Optional<Enrollment> findEnrollment = enrollmentRepository.findEnrollmentByUserAndCourse(user, course);
        Assertions.assertTrue(findEnrollment.isPresent());
    }
}
