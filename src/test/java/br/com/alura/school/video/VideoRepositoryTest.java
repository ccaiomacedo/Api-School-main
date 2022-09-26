package br.com.alura.school.video;

import br.com.alura.school.course.Course;
import br.com.alura.school.course.CourseRepository;
import br.com.alura.school.section.Section;
import br.com.alura.school.section.SectionRepository;
import br.com.alura.school.tests.Factory;
import br.com.alura.school.user.User;
import br.com.alura.school.user.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class VideoRepositoryTest {


    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void save_should_persist_with_auto_increment_when_id_is_null() {
        User user = userRepository.save(Factory.createUser());
        Course course = courseRepository.save(Factory.createCourse());
        Section section = sectionRepository.save(Factory.createSection());

        Video video = new Video("https://www.youtube.com/watch?v=gI4-vj0WpK",section);
        video.setId(null);

        videoRepository.save(video);

        Assertions.assertNotNull(video.getId());
    }
}
