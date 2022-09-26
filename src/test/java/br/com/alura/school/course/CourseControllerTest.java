package br.com.alura.school.course;

import br.com.alura.school.tests.Factory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CourseControllerTest {

    private final ObjectMapper jsonMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CourseRepository courseRepository;

    @Test
    void should_retrieve_course_by_code() throws Exception {
        courseRepository.save(new Course("java-1", "Java OO", "Java and Object Orientation: Encapsulation, Inheritance and Polymorphism."));

        mockMvc.perform(get("/courses/java-1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is("java-1")))
                .andExpect(jsonPath("$.name", is("Java OO")))
                .andExpect(jsonPath("$.shortDescription", is("Java and O...")));
    }

    @Test
    void should_return_not_found_if_code_not_exists() throws Exception {
        courseRepository.save(new Course("java-1", "Java OO", "Java and Object Orientation: Encapsulation, Inheritance and Polymorphism."));

        mockMvc.perform(get("/courses/java-2")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_retrieve_all_courses() throws Exception {
        courseRepository.save(new Course("spring-1", "Spring Basics", "Spring Core and Spring MVC."));
        courseRepository.save(new Course("spring-2", "Spring Boot", "Spring Boot"));

        mockMvc.perform(get("/courses")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$[0].code", is("spring-1")))
                .andExpect(jsonPath("$[0].name", is("Spring Basics")))
                .andExpect(jsonPath("$[0].shortDescription", is("Spring Cor...")))
                .andExpect(jsonPath("$[1].code", is("spring-2")))
                .andExpect(jsonPath("$[1].name", is("Spring Boot")))
                .andExpect(jsonPath("$[1].shortDescription", is("Spring Boot")));
    }

    @Test
    void should_add_new_course() throws Exception {
        NewCourseRequest newCourseRequest = new NewCourseRequest("java-2", "Java Collections", "Java Collections: Lists, Sets, Maps and more.");

        mockMvc.perform(post("/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(newCourseRequest)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/courses/java-2"));
    }

    @Test
    void should_return_bad_request_if_course_name_already_exists() throws Exception {
        NewCourseRequest newCourseRequest = new NewCourseRequest("java-1", "Java Collections", "Java Collections: Lists, Sets, Maps and more.");
        Course course = courseRepository.save(Factory.createCourse());

        mockMvc.perform(post("/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(newCourseRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.[0].fieldName", is("name")));
    }

    @Test
    void should_return_bad_request_if_course_code_already_exists() throws Exception {
        NewCourseRequest newCourseRequest = new NewCourseRequest("java-2", "Java Collection", "Java Collections: Lists, Sets, Maps and more.");
        Course course = courseRepository.save(Factory.createCourse());

        mockMvc.perform(post("/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(newCourseRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.[0].fieldName", is("code")));
    }

    @Test
    void should_return_bad_request_if_course_code_is_null() throws Exception {
        NewCourseRequest newCourseRequest = new NewCourseRequest("", "Java Collection", "Java Collections: Lists, Sets, Maps and more.");

        mockMvc.perform(post("/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(newCourseRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.[0].fieldName", is("code")));
    }

    @Test
    void should_return_bad_request_if_course_name_is_null() throws Exception {
        NewCourseRequest newCourseRequest = new NewCourseRequest("java-2", "", "Java Collections: Lists, Sets, Maps and more.");

        mockMvc.perform(post("/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(newCourseRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.[0].fieldName", is("name")));
    }

    @Test
    void should_return_bad_request_if_course_code_is_longer_than_10_characters() throws Exception {
        NewCourseRequest newCourseRequest = new NewCourseRequest("java-212345", "Java Collection", "Java Collections: Lists, Sets, Maps and more.");

        mockMvc.perform(post("/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(newCourseRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.[0].fieldName", is("code")))
                .andExpect(jsonPath("$.errors.[0].message", is("size must be between 0 and 10")));
    }
    @Test
    void should_return_bad_request_if_course_name_is_longer_than_20_characters() throws Exception {
        NewCourseRequest newCourseRequest = new NewCourseRequest("java-2", "Java Collection:list, sets", "Java Collections: Lists, Sets, Maps and more.");

        mockMvc.perform(post("/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(newCourseRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.[0].fieldName", is("name")))
                .andExpect(jsonPath("$.errors.[0].message", is("size must be between 0 and 20")));
    }


}