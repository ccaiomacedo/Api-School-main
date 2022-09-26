package br.com.alura.school.enrollment;

import br.com.alura.school.course.Course;
import br.com.alura.school.course.CourseRepository;
import br.com.alura.school.exceptions.ResourceNotFoundException;
import br.com.alura.school.exceptions.UserAlreadyEnrolledInTheCourseException;
import br.com.alura.school.user.User;
import br.com.alura.school.user.UserRepository;
import br.com.alura.school.utils.EnrollmentValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
public class EnrollmentController {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    public EnrollmentController(UserRepository userRepository, CourseRepository courseRepository, EnrollmentRepository enrollmentRepository) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    @PostMapping(value = "/courses/{courseCode}/enroll")
    ResponseEntity<NewEnrollmentRequest> newEnrollment(@RequestBody @Valid NewEnrollmentRequest newEnrollmentRequest, @PathVariable String courseCode) {
        User user = userRepository.findByUsername(newEnrollmentRequest.getUsername()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Course course = courseRepository.findByCode(courseCode).orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        Enrollment enrollment = new Enrollment(course, user);

        if (EnrollmentValidator.isAlreadyEnrolled(user, course)) {
            throw new UserAlreadyEnrolledInTheCourseException("User " + user.getUsername() + " is already enrolled in course with code " + courseCode);
        }
        enrollment = enrollmentRepository.save(enrollment);
        newEnrollmentRequest = new NewEnrollmentRequest(enrollment);
        return ResponseEntity.created(null).build();
    }


}
