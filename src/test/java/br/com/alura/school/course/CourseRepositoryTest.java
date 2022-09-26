package br.com.alura.school.course;

import br.com.alura.school.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class CourseRepositoryTest {

    @Autowired
    private CourseRepository courseRepository;


    private String existingCode;
    private String nonExistingCode;
    private Course course;

    @BeforeEach
    void setUp() throws Exception {
        existingCode = "flutter-cores-dinamicas";
        nonExistingCode = "PHP";
        course = Factory.createCourse();
    }

    @Test
    void save_should_persist_with_auto_increment_when_id_is_null() {
        course.setId(null);
        Course course = courseRepository.save(this.course);

        Assertions.assertNotNull(course.getId());
    }

    @Test
    void find_by_code_should_Return_object_when_code_exists() {
        Optional<Course> course = courseRepository.findByCode(existingCode);

        Assertions.assertNotNull(course);
    }
    @Test
    void find_by_code_should_Return_null_when_code_does_not_exists() {
        Optional<Course> course = courseRepository.findByCode(nonExistingCode);

        Assertions.assertFalse(course.isPresent());
    }
}
