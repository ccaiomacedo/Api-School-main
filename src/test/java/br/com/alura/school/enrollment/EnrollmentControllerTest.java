package br.com.alura.school.enrollment;

import br.com.alura.school.course.Course;
import br.com.alura.school.course.CourseRepository;
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
public class EnrollmentControllerTest {

    private final ObjectMapper jsonMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;


    private NewEnrollmentRequest newEnrollmentRequest;
    private String nonExistingCode;


    @BeforeEach
    void setUp() throws Exception {
        nonExistingCode = "PHP";
        newEnrollmentRequest = Factory.createNewEnrollmentRequest();
    }

    @Test
    void should_add_new_enrollment() throws Exception {

        User user = userRepository.save(Factory.createUser());
        Course course = courseRepository.save(Factory.createCourse());

        String jsonBody = jsonMapper.writeValueAsString(newEnrollmentRequest);

        ResultActions result = mockMvc.perform(post("/courses/{courseCode}/enroll", course.getCode())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody).accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isCreated());
    }

    @Test
    void should_return_not_found_if_user_not_exists() throws Exception {

        Course course = courseRepository.save(Factory.createCourse());

        String jsonBody = jsonMapper.writeValueAsString(newEnrollmentRequest);

        ResultActions result = mockMvc.perform(post("/courses/{courseCode}/enroll", course.getCode())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody).accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
        result.andExpect(jsonPath("$.message", is("User not found")));
    }

    @Test
    void should_return_not_found_if_course_not_exists() throws Exception {
        User user = userRepository.save(Factory.createUser());

        String jsonBody = jsonMapper.writeValueAsString(newEnrollmentRequest);

        ResultActions result = mockMvc.perform(post("/courses/{courseCode}/enroll", nonExistingCode)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody).accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
        result.andExpect(jsonPath("$.message", is("Course not found")));
    }

    @Test
    void should_return_bad_request_if_user_is_already_enrolled_in_course() throws Exception {
        User user = userRepository.save(Factory.createUser());
        Course course = courseRepository.save(Factory.createCourse());
        enrollmentRepository.save(new Enrollment(course, user));

        String jsonBody = jsonMapper.writeValueAsString(newEnrollmentRequest);

        ResultActions result = mockMvc.perform(post("/courses/{courseCode}/enroll", course.getCode())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody).accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isBadRequest());
        result.andExpect(jsonPath("$.message", is("User alex is already enrolled in course with code " + course.getCode())));
    }

    @Test
    void should_return_bad_request_if_username_is_null() throws Exception {

        Course course = courseRepository.save(Factory.createCourse());
        NewEnrollmentRequest newEnrollmentRequest = new NewEnrollmentRequest(1L, "java-2", "");

        String jsonBody = jsonMapper.writeValueAsString(newEnrollmentRequest);

        ResultActions result = mockMvc.perform(post("/courses/{courseCode}/enroll", course.getCode())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody).accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isBadRequest());
        result.andExpect(jsonPath("$.errors.[0].fieldName", is("username")));
        result.andExpect(jsonPath("$.errors.[0].message", is("Required field")));
        result.andExpect(jsonPath("$.errors.[1].fieldName", is("username")));
        result.andExpect(jsonPath("$.errors.[1].message", is("username must contain betwen 3 and 20 characters")));
    }

    @Test
    void should_return_bad_request_if_username_is_less_than_3_characters() throws Exception {

        Course course = courseRepository.save(Factory.createCourse());
        NewEnrollmentRequest newEnrollmentRequest = new NewEnrollmentRequest(1L, "java-2", "al");

        String jsonBody = jsonMapper.writeValueAsString(newEnrollmentRequest);

        ResultActions result = mockMvc.perform(post("/courses/{courseCode}/enroll", course.getCode())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody).accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isBadRequest());
        result.andExpect(jsonPath("$.errors.[0].fieldName", is("username")));
        result.andExpect(jsonPath("$.errors.[0].message", is("username must contain betwen 3 and 20 characters")));
    }

    @Test
    void should_return_bad_request_if_username_is_greater_than_20_characters() throws Exception {

        Course course = courseRepository.save(Factory.createCourse());
        NewEnrollmentRequest newEnrollmentRequest = new NewEnrollmentRequest(1L, "java-2", "alex1234567891011121314151617");

        String jsonBody = jsonMapper.writeValueAsString(newEnrollmentRequest);

        ResultActions result = mockMvc.perform(post("/courses/{courseCode}/enroll", course.getCode())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody).accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isBadRequest());
        result.andExpect(jsonPath("$.errors.[0].fieldName", is("username")));
        result.andExpect(jsonPath("$.errors.[0].message", is("username must contain betwen 3 and 20 characters")));
    }
}
