package br.com.alura.school.video;

import br.com.alura.school.course.Course;
import br.com.alura.school.course.CourseRepository;
import br.com.alura.school.section.Section;
import br.com.alura.school.section.SectionRepository;
import br.com.alura.school.tests.Factory;
import br.com.alura.school.user.User;
import br.com.alura.school.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class VideoControllerTest {

    private final ObjectMapper jsonMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;


    @Autowired
    private VideoRepository videoRepository;


    private NewVideoRequest newVideoRequest;
    private String nonExistingCourseCode;
    private String nonExistingSectionCode;


    @BeforeEach
    void setUp() throws Exception {
        nonExistingSectionCode = "Queries";
        nonExistingCourseCode = "PHP";
        newVideoRequest = Factory.createNewVideoRequest();
    }

    @Test
    void should_add_new_video() throws Exception {
        User user = userRepository.save(Factory.createUser());
        Course course = courseRepository.save(Factory.createCourse());
        Section section = sectionRepository.save(new Section("flutter-cores-dinamicas", "Flutter: Configurando cores dinâmicas", user, course));


        String jsonBody = jsonMapper.writeValueAsString(newVideoRequest);

        ResultActions result = mockMvc.perform(post("/courses/{courseCode}/sections/{sectionCode}", course.getCode(), section.getCode())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody).accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isCreated());

    }

    @Test
    void should_return_bad_request_to_add_new_video_if_video_already_exists() throws Exception {
        User user = userRepository.save(Factory.createUser());
        Course course = courseRepository.save(Factory.createCourse());
        Section section = sectionRepository.save(new Section("flutter-cores-dinamicas", "Flutter: Configurando cores dinâmicas", user, course));
        videoRepository.save(new Video("https://www.youtube.com/watch?v=gI4-vj0WpK", section));

        String jsonBody = jsonMapper.writeValueAsString(newVideoRequest);

        ResultActions result = mockMvc.perform(post("/courses/{courseCode}/sections/{sectionCode}", course.getCode(), section.getCode())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody).accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isBadRequest());


    }

    @Test
    void should_return_not_found_to_add_new_video_if_course_not_exists() throws Exception {
        userRepository.save(Factory.createUser());
        Course course = courseRepository.save(Factory.createCourse());
        Section section = sectionRepository.save(Factory.createSection());

        String jsonBody = jsonMapper.writeValueAsString(newVideoRequest);

        ResultActions result = mockMvc.perform(post("/courses/{courseCode}/sections/{sectionCode}", nonExistingCourseCode, section.getCode())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody).accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());

    }

    @Test
    void should_return_not_found_to_add_new_video_if_section_not_exists_for_course() throws Exception {
        User user = userRepository.save(Factory.createUser());
        Course course = courseRepository.save(Factory.createCourse());

        String jsonBody = jsonMapper.writeValueAsString(newVideoRequest);

        ResultActions result = mockMvc.perform(post("/courses/{courseCode}/sections/{sectionCode}", course.getCode(), nonExistingSectionCode)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody).accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
        result.andExpect(jsonPath("$.message", is("Section not found for course " + course.getCode())));
    }

    @Test
    void should_return_not_found_to_add_new_video_if_section_not_exists() throws Exception {
        User user = userRepository.save(Factory.createUser());
        Course course = courseRepository.save(Factory.createCourse());
        Section section = sectionRepository.save(new Section("flutter-cores-dinamicas", "Flutter: Configurando cores dinâmicas", user, course));

        String jsonBody = jsonMapper.writeValueAsString(newVideoRequest);

        ResultActions result = mockMvc.perform(post("/courses/{courseCode}/sections/{sectionCode}", course.getCode(), nonExistingSectionCode)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody).accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
        result.andExpect(jsonPath("$.message", is("Section not found")));
    }

}
