package br.com.alura.school.section;

import br.com.alura.school.course.Course;
import br.com.alura.school.course.CourseRepository;
import br.com.alura.school.enrollment.Enrollment;
import br.com.alura.school.enrollment.EnrollmentRepository;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class SectionControllerTest {

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
    private EnrollmentRepository enrollmentRepository;


    private NewSectionRequest newSectionRequest;
    private String nonExistingCode;

    @BeforeEach
    void setUp() throws Exception {
        nonExistingCode = "PHP";
        newSectionRequest = Factory.createNewSectionRequest();
    }


    @Test
    void should_add_new_section() throws Exception {
        User user = userRepository.save(Factory.createUser());
        Course course = courseRepository.save(Factory.createCourse());
        newSectionRequest.setAuthorUsername(user.getUsername());
        String jsonBody = jsonMapper.writeValueAsString(newSectionRequest);


        ResultActions result = mockMvc.perform(post("/courses/{code}/sections", course.getCode())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody).accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.code").exists());
        result.andExpect(jsonPath("$.title").exists());
        result.andExpect(jsonPath("$.authorUsername").exists());
    }

    @Test
    void should_return_not_found_to_add_new_section_if_code_of_course_not_exists() throws Exception {
        User user = userRepository.save(Factory.createUser());
        newSectionRequest.setAuthorUsername(user.getUsername());
        String jsonBody = jsonMapper.writeValueAsString(newSectionRequest);


        ResultActions result = mockMvc.perform(post("/courses/{code}/sections", nonExistingCode)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody).accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
        result.andExpect(jsonPath("$.message", is("Course not found")));
    }

    @Test
    void should_return_not_found_to_add_new_section_if_user_not_exists() throws Exception {
        Course course = courseRepository.save(Factory.createCourse());
        String jsonBody = jsonMapper.writeValueAsString(newSectionRequest);


        ResultActions result = mockMvc.perform(post("/courses/{code}/sections", course.getCode())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody).accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
        result.andExpect(jsonPath("$.message", is("User not found")));
    }

    @Test
    void should_return_bad_request_to_add_new_section_if_user_is_not_instructor() throws Exception {
        User user = userRepository.save(Factory.createUserStudent());
        newSectionRequest.setAuthorUsername(user.getUsername());
        Course course = courseRepository.save(Factory.createCourse());

        String jsonBody = jsonMapper.writeValueAsString(newSectionRequest);


        ResultActions result = mockMvc.perform(post("/courses/{code}/sections", course.getCode())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody).accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isBadRequest());
        result.andExpect(jsonPath("$.message", is("Only instructor allowed to register a section!")));
    }

    @Test
    void should_return_bad_request_to_add_new_section_if_code_of_section_already_exists() throws Exception {
        User user = userRepository.save(Factory.createUser());
        courseRepository.save(Factory.createCourse());
        newSectionRequest.setAuthorUsername(user.getUsername());
        Course course = courseRepository.save(Factory.createCourse());
        sectionRepository.save(Factory.createSection());

        String jsonBody = jsonMapper.writeValueAsString(newSectionRequest);


        ResultActions result = mockMvc.perform(post("/courses/{code}/sections", course.getCode())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody).accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isBadRequest());
        result.andExpect(jsonPath("$.errors.[0].fieldName", is("code")));
    }

    @Test
    void section_report_should_return_section_by_videos_report_if_have_courses_enrolled() throws Exception {
        User user = userRepository.save(Factory.createUser());
        Course course = courseRepository.save(Factory.createCourse());
        Section section = sectionRepository.save(new Section("flutter-cores-dinamicas", "Flutter: Configurando cores dinâmicas", user, course));
        Enrollment enrollment = new Enrollment(course, user);
        enrollmentRepository.save(enrollment);
        enrollmentRepository.findEnrollmentByUserAndCourse(user, course);

        mockMvc.perform(get("/sectionByVideosReport")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].courseName", is("Java Collections")))
                .andExpect(jsonPath("$[0].title", is("Flutter: Configurando cores dinâmicas")))
                .andExpect(jsonPath("$[0].authorName", is("alex")))
                .andExpect(jsonPath("$[0].totalVideos", is(0)));
    }

    @Test
    void section_report_should_return_no_content_if_do_not_have_courses_enrolled() throws Exception {
        User user = userRepository.save(Factory.createUser());
        Course course = courseRepository.save(Factory.createCourse());

        enrollmentRepository.findEnrollmentByUserAndCourse(user, course);

        mockMvc.perform(get("/sectionByVideosReport")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }


}
