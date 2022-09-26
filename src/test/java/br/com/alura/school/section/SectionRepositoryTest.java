package br.com.alura.school.section;

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

import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
public class SectionRepositoryTest {

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private UserRepository userRepository;

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
        User user = Factory.createUser();
        userRepository.save(user);

        Course course = Factory.createCourse();
        courseRepository.save(course);

        Section section = Factory.createSection();
        section.setId(null);

        sectionRepository.save(section);

        Assertions.assertNotNull(section.getId());
    }

    @Test
    void find_by_code_should_Return_object_when_code_exists() {
        Optional<Section> section = sectionRepository.findByCode(existingCode);

        Assertions.assertNotNull(section);
    }

    @Test
    void find_by_code_should_Return_null_when_code_does_not_exists() {
        Optional<Section> section = sectionRepository.findByCode(nonExistingCode);

        Assertions.assertFalse(section.isPresent());
    }

    @Test
    void find_by_course_should_Return_object_when_course_exists() {
        List<Section> section = sectionRepository.findByCourse(course);

        Assertions.assertNotNull(section);
    }

    @Test
    void find_by_course_should_Return_null_when_course_does_not_exists() {
        List<Section> section = sectionRepository.findByCourse(course);

        Assertions.assertTrue(section.isEmpty());
    }
}
